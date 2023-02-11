package com.junkyu.searchblogservice.domain.repository.custom;

import com.junkyu.searchblogservice.web.blog.response.FindBlogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 블로그 정보 커스텀 쿼리.
 */
public interface BlogDataCustomRepository {

  /**
   * query 기준으로 블로그 정보 목록 조회.
   */
  Page<FindBlogResponse> findAllByQuery(String query, PageRequest pageRequest);

  /**
   * url 기준으로 데이터 존재 유무 조회.
   */
  boolean existsByUrl(String url);
}
