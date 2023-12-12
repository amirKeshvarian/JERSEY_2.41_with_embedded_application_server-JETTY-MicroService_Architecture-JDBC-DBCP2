package org.company.project.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RestConfiguration {
    String prefix();
    int maxThread() default 20;
    int minThread() default 5;
    int idleTimeout() default 1000;
}
