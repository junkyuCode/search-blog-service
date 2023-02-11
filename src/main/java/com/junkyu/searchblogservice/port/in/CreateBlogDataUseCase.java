package com.junkyu.searchblogservice.port.in;

import java.util.List;

import com.junkyu.searchblogservice.web.blog.response.FindBlogResponse;

public interface CreateBlogDataUseCase {

  void save(String query, List<FindBlogResponse> responses);
}
