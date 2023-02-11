package com.junkyu.searchblogservice.web.blog.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.junkyu.searchblogservice.feign.kakao.blog.response.KakaoBlogResponse;
import com.junkyu.searchblogservice.feign.naver.blog.response.NaverBlogResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

public record FindBlogResponse(String title, String contents, String url,
                               String blogName, LocalDateTime dateTime) {

  @Builder
  @QueryProjection
  public FindBlogResponse {
  }

  public static FindBlogResponse from(KakaoBlogResponse.Document document) {
    return FindBlogResponse.builder()
        .title(document.title())
        .contents(document.contents())
        .url(document.url())
        .blogName(document.blogname())
        .dateTime(document.datetime().toLocalDateTime())
        .build();
  }

  public static FindBlogResponse from(NaverBlogResponse.Item item) {
    return FindBlogResponse.builder()
        .title(item.title())
        .contents(item.description())
        .url(item.link())
        .blogName(item.bloggername())
        .dateTime(LocalDateTime.of(
            LocalDate.parse(item.postdate(), DateTimeFormatter.ofPattern("yyyyMMdd")),
            LocalDateTime.MIN.toLocalTime()))
        .build();
  }
}
