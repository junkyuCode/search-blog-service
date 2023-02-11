package com.junkyu.searchblogservice.feign.naver.blog;

import com.junkyu.searchblogservice.config.NaverBlogApiConfig;
import com.junkyu.searchblogservice.feign.naver.blog.request.NaverBlogRequest;
import com.junkyu.searchblogservice.feign.naver.blog.response.NaverBlogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "NaverBlogClient", url = "${naver.api.url}", configuration = NaverBlogApiConfig.class)
public interface NaverBlogClient {

  @GetMapping(value = "/v1/search/blog.json", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  NaverBlogResponse getBlogData(@SpringQueryMap NaverBlogRequest request);
}
