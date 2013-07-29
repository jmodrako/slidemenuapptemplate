package com.template;

import android.app.Application;
import com.googlecode.androidannotations.annotations.EApplication;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 26.07.2013
 * Time: 09:13
 * To change this template use File | Settings | File Templates.
 */
@EApplication
public class TemplateApp extends Application {
    public static TemplateApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
