package com.junkyu.searchblogservice.feign.kakao.blog;

import com.junkyu.searchblogservice.config.KakaoBlogApiConfig;
import com.junkyu.searchblogservice.feign.kakao.blog.request.KakaoBlogRequest;
import com.junkyu.searchblogservice.feign.kakao.blog.response.KakaoBlogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "KakaoBlogClient", url = "${kakao.api.url}", configuration = KakaoBlogApiConfig.class)
public interface KakaoBlogClient {

  @GetMapping(value = "/v2/search/blog", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  KakaoBlogResponse getBlogData(@SpringQueryMap KakaoBlogRequest request);
}
