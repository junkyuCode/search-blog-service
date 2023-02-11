package com.junkyu.searchblogservice.feign.naver.blog.request;

import com.junkyu.searchblogservice.feign.kakao.blog.request.KakaoBlogRequest;
import com.junkyu.searchblogservice.web.blog.request.FindBlogRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NaverBlogRequest {

  private final String query;
  private final String sort;
  private final Integer start;
  private final Integer display;

  @Builder
  public NaverBlogRequest(String query, String sort, Integer start, Integer display) {
    this.query = query;
    this.sort = sort;
    this.start = start;
    this.display = display;
  }

  public static NaverBlogRequest from(FindBlogRequest request) {
    return NaverBlogRequest.builder()
        .query(request.getQuery())
        .sort("date".equals(request.getSort()) ? "date" : "sim")
        .start(request.getPage())
        .display(request.getSize())
        .build();
  }
}
