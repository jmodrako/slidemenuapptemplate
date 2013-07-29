package com.template.menu;

import android.support.v4.app.Fragment;
import com.template.TemplateApp;
import com.template.activity.BaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 09.07.2013
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public class MenuItemImpl implements MenuItem {


    protected int titleRes;

    public MenuItemImpl(int title) {
        this.titleRes = title;
    }

    public String getTitle() {
        return TemplateApp.instance.getString(titleRes);
    }

    @Override
    public String getLogoUrl() {
        return null;
    }

    @Override
    public void onClick(BaseActivity baseActivity) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public boolean isCheckable() {
        return false;
    }

    @Override
    public Fragment getFragment() {
        return null;
    }
}
