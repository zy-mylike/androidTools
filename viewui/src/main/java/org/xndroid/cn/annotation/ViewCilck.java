package org.xndroid.cn.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewCilck {
    int value();

    Class listenerType() default View.OnClickListener.class;

    String listenerSetter() default "setOnClickListener";
}
