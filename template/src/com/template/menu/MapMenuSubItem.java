package com.template.menu;

import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.MapsInitializer;
import com.template.TemplateApp;
import com.template.activity.BaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 09.07.2013
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public class MapMenuSubItem extends MenuSubItem {


    public MapMenuSubItem(int title, Class<? extends Fragment> fragment) {
        super(title, fragment);
    }

    @Override
    public void onClick(BaseActivity baseActivity) {
        try {
            MapsInitializer.initialize(TemplateApp.instance);
            super.onClick(baseActivity);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(TemplateApp.instance, "No Google Play Services found.", Toast.LENGTH_SHORT).show();
        }


    }

}
