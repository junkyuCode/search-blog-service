package com.junkyu.searchblogservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureRestDocs
@ActiveProfiles("test")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerTest {
}
