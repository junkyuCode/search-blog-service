package com.junkyu.searchblogservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * FeignClient Configuration.
 */
@Configuration
@EnableFeignClients(basePackages = {"com.junkyu"})
public class FeignClientConfig {
}
