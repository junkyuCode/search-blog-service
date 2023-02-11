package com.junkyu.searchblogservice.feign.naver.blog.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverBlogResponse {

  private Integer total;
  private Integer start;
  private Integer display;
  private List<Item> items;

  @Builder
  public NaverBlogResponse(Integer total, Integer start, Integer display, List<Item> items) {
    this.total = total;
    this.start = start;
    this.display = display;
    this.items = items;
  }

  public record Item(
      String title,
      String link,
      String description,
      String bloggername,
      String bloggerlink,
      String postdate
  ) {}
}
