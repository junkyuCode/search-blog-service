package com.junkyu.searchblogservice.web.blog.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;

/**
 * 블로그 정보 요청.
 */
@Getter
@Setter
@NoArgsConstructor
public class FindBlogRequest {

  @NotBlank(message = "query는 필수값입니다.")
  private String query;

  private String sort;

  @Range(min = 1, max = 50, message = "page는 1 이상 50 이하여야 합니다.")
  private Integer page = 1;

  @Range(min = 1, max = 50, message = "size는 1 이상 50 이하여야 합니다.")
  private Integer size = 10;

  @JsonIgnore
  public PageRequest getPageRequest() {
    return PageRequest.of(page - 1, size);
  }

  @Builder
  public FindBlogRequest(String query, String sort, Integer page, Integer size) {
    this.query = query;
    this.sort = sort;
    this.page = page;
    this.size = size;
  }
}
