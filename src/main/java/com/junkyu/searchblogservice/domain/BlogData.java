package com.junkyu.searchblogservice.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.junkyu.searchblogservice.common.datetime.KakaoDatePattern;
import com.junkyu.searchblogservice.domain.common.BaseDateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "blog_data",
    indexes = {
        @Index(name = "idx__datetime", columnList = "datetime"),
        @Index(name = "idx__query", columnList = "query"),
        @Index(name = "idx__url", columnList = "url")
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogData extends BaseDateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "query")
  private String query;

  @Column(name = "title")
  private String title;

  @Column(name = "contents", columnDefinition = "mediumtext")
  private String contents;

  @Column(name = "url", columnDefinition = "varchar(1000)")
  private String url;

  @Column(name = "blog_name")
  private String blogName;

  @JsonFormat(pattern = KakaoDatePattern.ISO_8601)
  private LocalDateTime datetime;

  @Builder
  public BlogData(String query,
                  String title,
                  String contents,
                  String url,
                  String blogName,
                  LocalDateTime datetime) {
    this.query = query;
    this.title = title;
    this.contents = contents;
    this.url = url;
    this.blogName = blogName;
    this.datetime = datetime;
  }
}
