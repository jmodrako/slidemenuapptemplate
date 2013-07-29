package com.template.menu;

import android.support.v4.app.Fragment;
import com.template.R;
import com.template.TemplateApp;
import com.template.activity.BaseActivity;
import com.template.activity.MainActivity;
import com.template.data.DataContainer;
import com.template.data.DataContainer_;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 09.07.2013
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public class MenuSubItem extends MenuItemImpl {

    Class<? extends Fragment> smartphoneFragment;
    Class<? extends Fragment> tabletFragment;

    public MenuSubItem(int title, Class<? extends Fragment> fragment) {
        super(title);
        this.smartphoneFragment = fragment;
    }

    public MenuSubItem(int title, Class<? extends Fragment> smartphoneFragment, Class<? extends Fragment> tabletFragment) {
        super(title);
        this.smartphoneFragment = smartphoneFragment;
        this.tabletFragment = tabletFragment;
    }

    @Override
    public void onClick(BaseActivity baseActivity) {
        DataContainer dataContainer = DataContainer_.getInstance_(TemplateApp.instance);
        if ((getFragment() != null) && (!dataContainer.getMenuAdapter().getCurrent().equals(this))) {
            dataContainer.getMenuAdapter().setItem(this);
            baseActivity.openFragment(getFragment());


        }
        ((MainActivity) baseActivity).hideMenu();
    }

    public Fragment getFragment() {
        try {
            if (!TemplateApp.instance.getResources().getBoolean(R.bool.tablet) || tabletFragment == null) {
                return smartphoneFragment.newInstance();
            } else {
                return tabletFragment.newInstance();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
