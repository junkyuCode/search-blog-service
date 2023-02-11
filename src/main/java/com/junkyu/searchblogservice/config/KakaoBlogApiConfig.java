package com.junkyu.searchblogservice.config;

import com.junkyu.searchblogservice.common.interceptor.KakaoAuthRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Kakao OpenAPI 사용을 위한 Configuration.
 *
 * Request Header에 포함될 인증 정보 관리
 */
@Configuration
public class KakaoBlogApiConfig {

  @Bean
  public RequestInterceptor kakaoApiRequestHeader(
      @Value("${kakao.api.token}") String token) {

    return new KakaoAuthRequestInterceptor(token);
  }
}
