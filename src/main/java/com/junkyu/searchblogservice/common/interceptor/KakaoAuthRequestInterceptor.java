package com.junkyu.searchblogservice.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public record KakaoAuthRequestInterceptor(String token) implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {
    template.header("Authorization", "KakaoAK " + token);
  }
}
