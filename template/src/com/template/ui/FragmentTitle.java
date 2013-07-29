package com.template.ui;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 07.07.2013
 * Time: 01:28
 * To change this template use File | Settings | File Templates.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FragmentTitle {
    int value();
}
