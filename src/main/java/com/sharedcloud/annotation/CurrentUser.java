package com.sharedcloud.annotation;

import java.lang.annotation.*;

/**
 * @Author: kxr
 * @Date: 2019/9/6
 * @Description
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
