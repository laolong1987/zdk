package com.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gaoyang on 2016/6/14.
 */
@Target(ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface Token {

    boolean save() default false ;

    boolean remove() default false ;
}