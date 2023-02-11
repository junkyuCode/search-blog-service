package com.junkyu.searchblogservice.port.in;

import java.util.List;

import com.junkyu.searchblogservice.web.rank.response.FindRankResponse;

public interface FindRankUseCase {

  List<FindRankResponse> findTop10Query();
}
