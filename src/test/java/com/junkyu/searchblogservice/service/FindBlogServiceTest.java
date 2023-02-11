package com.junkyu.searchblogservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;
import java.util.List;

import com.junkyu.searchblogservice.annotation.ServiceTest;
import com.junkyu.searchblogservice.common.error.BusinessException;
import com.junkyu.searchblogservice.feign.kakao.blog.KakaoBlogClient;
import com.junkyu.searchblogservice.feign.kakao.blog.response.KakaoBlogResponse;
import com.junkyu.searchblogservice.feign.naver.blog.NaverBlogClient;
import com.junkyu.searchblogservice.feign.naver.blog.response.NaverBlogResponse;
import com.junkyu.searchblogservice.port.in.CreateBlogDataUseCase;
import com.junkyu.searchblogservice.web.blog.request.FindBlogRequest;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

@ServiceTest
class FindBlogServiceTest {

  @Mock
  KakaoBlogClient kakaoBlogClient;

  @Mock
  NaverBlogClient naverBlogClient;

  @Mock
  CreateBlogDataUseCase createBlogDataUseCase;

  @InjectMocks
  FindBlogService findBlogService;

  @Mock
  RedisTemplate<String, String> redisTemplate;

  @Mock
  ZSetOperations<String, String> zSetOperations;

  @BeforeEach
  public void setUp() {
    Mockito.when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
    Mockito.when(zSetOperations.incrementScore(any(), any(), anyDouble()))
        .thenReturn(null);
  }

  @Nested
  @DisplayName("find 메소드는")
  class Describe_of_find {

    @Nested
    @DisplayName("카카오 API가 사용 가능하고")
    class Context_with_Kakao_Available {

      final Integer givenTotalCount = 10;

      @BeforeEach
      void setup() {
        given(kakaoBlogClient.getBlogData(any()))
            .willReturn(
                KakaoBlogResponse.builder()
                    .meta(new KakaoBlogResponse.Meta(givenTotalCount, 10, true))
                    .documents(
                        List.of(
                            new KakaoBlogResponse.Document("카카오 제목", "콘텐츠", "url", "블로그명", "썸네일 링크", OffsetDateTime.now())
                        )
                    )
                    .build()
            );
      }

      @Nested
      @DisplayName("FindBlogRequest를 요청으로 받으면")
      class Context_with_FindBlogRequest {
        final FindBlogRequest request =
            FindBlogRequest.builder()
                .query("쿼리")
                .page(1)
                .size(10)
                .build();

        @DisplayName("카카오 API를 이용하여 응답한다.")
        @Test
        void test() {
          var result = findBlogService.find(request);

          verify(kakaoBlogClient, times(1)).getBlogData(any());
          verify(naverBlogClient, times(0)).getBlogData(any());
          assertEquals(givenTotalCount.longValue(), result.getTotalElements());
        }
      }
    }

    @Nested
    @DisplayName("카카오 API가 사용이 불가하고")
    class Context_with_Kakao_Unavailable {

      final Integer givenTotalCount = 10;

      @BeforeEach
      void setup() {
        given(kakaoBlogClient.getBlogData(any()))
            .willThrow(FeignException.class);

        given(naverBlogClient.getBlogData(any()))
            .willReturn(
                NaverBlogResponse.builder()
                    .total(10)
                    .start(1)
                    .display(10)
                    .items(
                        List.of(
                            new NaverBlogResponse.Item("네이버 제목", "콘텐츠", "url", "블로그명", "블로그 링크", "20230210")
                        )
                    )
                    .build()
            );
      }

      @Nested
      @DisplayName("FindBlogRequest를 요청으로 받으면")
      class Context_with_FindBlogRequest {
        final FindBlogRequest request =
            FindBlogRequest.builder()
                .query("쿼리")
                .page(1)
                .size(10)
                .build();

        @DisplayName("네이버 API를 이용하여 응답한다.")
        @Test
        void test() {
          var result = findBlogService.find(request);

          verify(kakaoBlogClient, times(1)).getBlogData(any());
          verify(naverBlogClient, times(1)).getBlogData(any());
          assertEquals(givenTotalCount.longValue(), result.getTotalElements());
        }
      }
    }

    @Nested
    @DisplayName("카카오 API와 네이버 API가 모두 사용이 불가하고")
    class Context_with_Kakao_Naver_Unavailable {

      @BeforeEach
      void setup() {
        given(kakaoBlogClient.getBlogData(any()))
            .willThrow(FeignException.class);

        given(naverBlogClient.getBlogData(any()))
            .willThrow(FeignException.class);
      }

      @Nested
      @DisplayName("FindBlogRequest를 요청으로 받으면")
      class Context_with_FindBlogRequest {
        final FindBlogRequest request =
            FindBlogRequest.builder()
                .query("쿼리")
                .page(1)
                .size(10)
                .build();

        @DisplayName("BusinessException을 발생시킨다.")
        @Test
        void test() {
          Assertions.assertThrows(
              BusinessException.class, () ->
                  findBlogService.find(request));
        }
      }
    }
  }
}