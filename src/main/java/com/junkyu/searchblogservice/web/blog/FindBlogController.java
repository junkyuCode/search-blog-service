package com.junkyu.searchblogservice.web.blog;

import javax.validation.Valid;

import com.junkyu.searchblogservice.port.in.FindBlogUseCase;
import com.junkyu.searchblogservice.web.blog.request.FindBlogRequest;
import com.junkyu.searchblogservice.web.blog.response.FindBlogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 블로그 데이터 Controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/blog")
public class FindBlogController {

  private final FindBlogUseCase findBlogUseCase;

  /**
   * 카카오 API를 조회하여 블로그 데이터 조회 API.
   */
  @GetMapping("/list")
  public Page<FindBlogResponse> find(@Valid FindBlogRequest request) {
    return findBlogUseCase.find(request);
  }
}
