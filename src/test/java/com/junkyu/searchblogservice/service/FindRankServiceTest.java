package com.junkyu.searchblogservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.Set;

import com.junkyu.searchblogservice.annotation.ServiceTest;
import com.junkyu.searchblogservice.config.RedisConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

@ServiceTest
class FindRankServiceTest {

  @Mock
  RedisTemplate<String, String> redisTemplate;

  @Mock
  ZSetOperations<String, String> zSetOperations;

  @InjectMocks
  FindRankService findRankService;

  @BeforeEach
  public void setUp() {
    Mockito.when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
    Mockito.when(zSetOperations.incrementScore(any(), any(), anyDouble()))
        .thenReturn(null);
  }

  @Nested
  @DisplayName("findTop10Query 메소드는")
  class Describe_of_findTop10Query {

    @Nested
    @DisplayName("Redis에 데이터가 있으면")
    class Context_with_result_exists {

      final String query = "쿼리";
      final Double score = 10d;

      @BeforeEach
      void setup() {
        given(zSetOperations.reverseRangeWithScores(RedisConfig.KEYWORD_RANK_KEY, 0, 9))
            .willReturn(
                Set.of(
                    ZSetOperations.TypedTuple.of(query, score)
                )
            );
      }

      @Test
      @DisplayName("인기검색어 배열을 반환한다.")
      void it_returns_empty_array() {
        var result = findRankService.findTop10Query();

        assertEquals(1, result.size());
      }
    }

    @Nested
    @DisplayName("Redis에 데이터가 없으면")
    class Context_with_result_not_exists {

      @BeforeEach
      void setup() {
        given(zSetOperations.reverseRangeWithScores(RedisConfig.KEYWORD_RANK_KEY, 0, 9))
            .willReturn(null);
      }

      @Test
      @DisplayName("빈 배열을 반환한다.")
      void it_returns_empty_array() {
        var result = findRankService.findTop10Query();

        assertEquals(0, result.size());
      }
    }
  }

}