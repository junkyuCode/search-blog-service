package com.junkyu.searchblogservice.port.in;

import com.junkyu.searchblogservice.web.blog.request.FindBlogRequest;
import com.junkyu.searchblogservice.web.blog.response.FindBlogResponse;
import org.springframework.data.domain.Page;

public interface FindBlogUseCase {

  Page<FindBlogResponse> find(FindBlogRequest request);
}
