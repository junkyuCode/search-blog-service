package com.junkyu.searchblogservice.service;

import java.util.List;

import com.junkyu.searchblogservice.domain.BlogData;
import com.junkyu.searchblogservice.domain.repository.BlogDataRepository;
import com.junkyu.searchblogservice.port.in.CreateBlogDataUseCase;
import com.junkyu.searchblogservice.web.blog.response.FindBlogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBlogDataService implements CreateBlogDataUseCase {

  private final BlogDataRepository blogDataRepository;

  /**
   * 관리할 데이터 적재.
   */
  @Transactional
  public void save(String query, List<FindBlogResponse> responses) {
    blogDataRepository.saveAll(
        responses.stream()
            .filter(res -> blogDataRepository.existsByUrl(res.url()))
            .map(res ->
                BlogData.builder()
                    .query(query)
                    .title(res.title())
                    .contents(res.contents())
                    .url(res.url())
                    .blogName(res.blogName())
                    .datetime(res.dateTime())
                .build()).toList());
  }
}
