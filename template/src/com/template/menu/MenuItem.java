package com.template.menu;

import android.support.v4.app.Fragment;
import com.template.activity.BaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 09.07.2013
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public interface MenuItem {


    public String getTitle();

    public String getLogoUrl();

    public void onClick(BaseActivity baseActivity);

    public boolean isChecked();

    public boolean isCheckable();

    public Fragment getFragment();
}
