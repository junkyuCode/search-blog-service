package com.junkyu.searchblogservice.domain.repository.custom;

import static com.junkyu.searchblogservice.domain.QBlogData.blogData;

import java.util.List;

import com.junkyu.searchblogservice.domain.BlogData;
import com.junkyu.searchblogservice.web.blog.response.FindBlogResponse;
import com.junkyu.searchblogservice.web.blog.response.QFindBlogResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BlogDataCustomRepositoryImpl extends QuerydslRepositorySupport
    implements BlogDataCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;

  public BlogDataCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(BlogData.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Page<FindBlogResponse> findAllByQuery(String query, PageRequest pageRequest) {
    List<FindBlogResponse> results =
        jpaQueryFactory.select(new QFindBlogResponse(
            blogData.title,
                blogData.contents,
                blogData.url,
                blogData.blogName,
                blogData.datetime))
            .from(blogData)
            .where(eqQuery(query))
            .orderBy(blogData.datetime.desc())
            .fetch();

    return new PageImpl<>(results, pageRequest, results.size());
  }

  @Override
  public boolean existsByUrl(String url) {
    return jpaQueryFactory.selectOne()
        .from(blogData)
        .where(eqUrl(url))
        .fetchFirst() != null;
  }

  private BooleanExpression eqQuery(String query) {
    if (query == null) {
      return null;
    }

    return blogData.query.eq(query);
  }

  private BooleanExpression eqUrl(String url) {
    if (url == null) {
      return null;
    }

    return blogData.url.eq(url);
  }
}
