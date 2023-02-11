package com.junkyu.searchblogservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.junkyu.searchblogservice.config.JpaAuditingConfig;
import com.junkyu.searchblogservice.config.QuerydslConfig;
import com.junkyu.searchblogservice.config.RedisConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import({JpaAuditingConfig.class, QuerydslConfig.class, RedisConfig.class})
@DataJpaTest
@ActiveProfiles("test")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceTest {
}
