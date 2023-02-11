package com.junkyu.searchblogservice.config;

import com.junkyu.searchblogservice.common.interceptor.NaverAuthRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Naver OpenAPI 사용을 위한 Configuration.
 *
 * Request Header에 포함될 인증 정보 관리
 */
@Configuration
public class NaverBlogApiConfig {

  @Bean
  public RequestInterceptor naverApiRequestHeader(
      @Value("${naver.api.client-id}") String clientId,
      @Value("${naver.api.client-secret}") String clientSecret) {

    return new NaverAuthRequestInterceptor(clientId, clientSecret);
  }
}
