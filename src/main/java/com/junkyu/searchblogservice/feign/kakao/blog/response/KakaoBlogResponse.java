package com.junkyu.searchblogservice.feign.kakao.blog.response;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.junkyu.searchblogservice.common.datetime.KakaoDatePattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoBlogResponse {

  private Meta meta;
  private List<Document> documents;

  @Builder
  public KakaoBlogResponse(Meta meta, List<Document> documents) {
    this.meta = meta;
    this.documents = documents;
  }

  public record Meta(
      @JsonProperty("total_count")
      Integer totalCount,
      @JsonProperty("pageable_count")
      Integer pageableCount,
      @JsonProperty("is_end")
      Boolean isEnd
  ) {}

  public record Document(
      String title,
      String contents,
      String url,
      String blogname,
      String thumbnail,
      @JsonFormat(pattern = KakaoDatePattern.ISO_8601)
      OffsetDateTime datetime
  ) {}
}
