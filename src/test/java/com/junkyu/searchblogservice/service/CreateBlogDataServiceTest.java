package com.junkyu.searchblogservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;

import com.junkyu.searchblogservice.annotation.ServiceTest;
import com.junkyu.searchblogservice.domain.repository.BlogDataRepository;
import com.junkyu.searchblogservice.web.blog.response.FindBlogResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class CreateBlogDataServiceTest {

  @Mock
  BlogDataRepository blogDataRepository;

  @InjectMocks
  CreateBlogDataService createBlogDataService;

  @Nested
  @DisplayName("save 메소드는")
  class Describe_of_save {

    final String query = "쿼리";
    final List<FindBlogResponse> responses =
        List.of(
            FindBlogResponse.builder()
                .title("제목")
                .contents("콘텐츠")
                .url("url")
                .blogName("블로그명")
                .dateTime(LocalDateTime.now())
                .build()
        );

    @DisplayName("JPA Repository의 save 메소드를 1회 호출한다.")
    @Test
    void it_calls_save() {
      createBlogDataService.save(query, responses);

      verify(blogDataRepository, times(1)).saveAll(any());
    }
  }
}