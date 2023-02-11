package com.junkyu.searchblogservice.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public record NaverAuthRequestInterceptor(String clientId, String clientSecret) implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {
    template.header("X-Naver-Client-Id", clientId);
    template.header("X-Naver-Client-Secret", clientSecret);
  }
}
