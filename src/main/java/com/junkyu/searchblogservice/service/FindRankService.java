package com.junkyu.searchblogservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.junkyu.searchblogservice.config.RedisConfig;
import com.junkyu.searchblogservice.port.in.FindRankUseCase;
import com.junkyu.searchblogservice.web.rank.response.FindRankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindRankService implements FindRankUseCase {

  private final RedisTemplate<String, String> redisTemplate;

  /**
   * Top10 인기 검색어 조회.
   */
  @Override
  public List<FindRankResponse> findTop10Query() {
    ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

    Set<ZSetOperations.TypedTuple<String>> typedTuples =
        zSetOperations.reverseRangeWithScores(RedisConfig.KEYWORD_RANK_KEY, 0, 9);

    return typedTuples != null ?
        typedTuples.stream().map(FindRankResponse::from).toList()
        : new ArrayList<>();
  }
}
