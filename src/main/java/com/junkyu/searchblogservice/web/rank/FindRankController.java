package com.junkyu.searchblogservice.web.rank;

import com.junkyu.searchblogservice.port.in.FindRankUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 검색어 순위 Controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rank")
public class FindRankController {

  private final FindRankUseCase findRankUseCase;

  @GetMapping("/query/top10")
  public ResponseEntity<?> findTop10Query() {
    return ResponseEntity.ok(findRankUseCase.findTop10Query());
  }
}
