package com.junkyu.searchblogservice.common.datetime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoDatePattern {

  /**
   * 기본 ISO 8601 패턴.
   */
  public static final String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
}
