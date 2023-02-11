package com.junkyu.searchblogservice.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL Configuration.
 */
@Configuration
public class QuerydslConfig {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  /**
   * querydslConfiguration.
   */
  @Bean
  public com.querydsl.sql.Configuration querydslConfiguration() {
    SQLTemplates templates = MySQLTemplates.builder().build(); //change to your Templates
    com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
    SpringExceptionTranslator springExceptionTranslator = new SpringExceptionTranslator();
    configuration.setExceptionTranslator(springExceptionTranslator);
    return configuration;
  }

}
