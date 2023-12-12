package org.company.project.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MicroServiceRunner {
    String basePackage();
    String ip() default "localhost";
    int port() default 8080;
}
