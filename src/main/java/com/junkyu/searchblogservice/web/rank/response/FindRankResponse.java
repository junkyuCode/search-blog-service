package com.junkyu.searchblogservice.web.rank.response;

import lombok.Builder;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * 검색어 순위 응답.
 */
public record FindRankResponse(String query, Integer score) {

  @Builder
  public FindRankResponse {}

  public static FindRankResponse from(ZSetOperations.TypedTuple<String> tuple) {
    return FindRankResponse.builder()
        .query(tuple.getValue())
        .score(tuple.getScore().intValue())
        .build();
  }
}
