package com.junkyu.searchblogservice.feign.kakao.blog.request;

import com.junkyu.searchblogservice.web.blog.request.FindBlogRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoBlogRequest {

  private final String query;
  private final String sort;
  private final Integer page;
  private final Integer size;

  @Builder
  public KakaoBlogRequest(String query, String sort, Integer page, Integer size) {
    this.query = query;
    this.sort = sort;
    this.page = page;
    this.size = size;
  }

  public static KakaoBlogRequest from(FindBlogRequest request) {
    return KakaoBlogRequest.builder()
        .query(request.getQuery())
        .sort("accuracy".equals(request.getSort()) ? "accuracy" : "recency")
        .page(request.getPage())
        .size(request.getSize())
        .build();
  }
}
