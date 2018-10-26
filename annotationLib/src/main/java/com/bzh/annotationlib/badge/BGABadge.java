package com.bzh.annotationlib.badge;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import android.view.View;

/**
 * @author 毕泽浩
 * @Description: 气泡注解
 * @time 2018/10/24 10:05
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BGABadge {
    Class<? extends View>[] value() default {};
}
