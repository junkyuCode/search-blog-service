package com.junkyu.searchblogservice.service;

import java.util.List;

import com.junkyu.searchblogservice.common.error.BusinessException;
import com.junkyu.searchblogservice.common.error.ErrorCode;
import com.junkyu.searchblogservice.config.RedisConfig;
import com.junkyu.searchblogservice.feign.kakao.blog.KakaoBlogClient;
import com.junkyu.searchblogservice.feign.kakao.blog.request.KakaoBlogRequest;
import com.junkyu.searchblogservice.feign.kakao.blog.response.KakaoBlogResponse;
import com.junkyu.searchblogservice.feign.naver.blog.NaverBlogClient;
import com.junkyu.searchblogservice.feign.naver.blog.request.NaverBlogRequest;
import com.junkyu.searchblogservice.feign.naver.blog.response.NaverBlogResponse;
import com.junkyu.searchblogservice.port.in.CreateBlogDataUseCase;
import com.junkyu.searchblogservice.port.in.FindBlogUseCase;
import com.junkyu.searchblogservice.web.blog.request.FindBlogRequest;
import com.junkyu.searchblogservice.web.blog.response.FindBlogResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindBlogService implements FindBlogUseCase {

  private final KakaoBlogClient kakaoBlogClient;
  private final NaverBlogClient naverBlogClient;
  private final RedisTemplate<String, String> redisTemplate;

  private final CreateBlogDataUseCase createBlogDataUseCase;

  /**
   * 블로그 데이터 페이징 조회.
   */
  @Override
  public Page<FindBlogResponse> find(FindBlogRequest request) {
    redisTemplate.opsForZSet().incrementScore(RedisConfig.KEYWORD_RANK_KEY, request.getQuery(), 1);

    int totalCount;
    List<FindBlogResponse> findBlogResponses;

    try {
      KakaoBlogResponse response = kakaoBlogClient.getBlogData(KakaoBlogRequest.from(request));

      findBlogResponses = response.getDocuments().stream()
          .map(FindBlogResponse::from)
          .toList();

      totalCount = response.getMeta().totalCount();
    } catch (FeignException e) {
      log.error(String.format("네이버 검색으로 전환합니다. \n Cause : %s", e.getCause().getMessage()));

      NaverBlogResponse response;
      try {
        response = naverBlogClient.getBlogData(NaverBlogRequest.from(request));
      } catch (Exception ex) {
        throw new BusinessException(ErrorCode.EXTERNAL_API_ERROR);
      }

      findBlogResponses = response.getItems().stream()
          .map(FindBlogResponse::from)
          .toList();

      totalCount = response.getTotal();
    }

    createBlogDataUseCase.save(request.getQuery(), findBlogResponses);

    return new PageImpl<>(findBlogResponses, request.getPageRequest(), totalCount);
  }
}
