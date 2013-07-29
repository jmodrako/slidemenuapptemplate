package com.template.fragments;

import android.widget.ListView;
import com.googlecode.androidannotations.annotations.*;
import com.template.R;
import com.template.data.DataContainer;
import com.template.menu.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 09.07.2013
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */

@EFragment(R.layout.menu_fragment)
public class MenuFragment extends BaseFragment {

    @ViewById(R.id.menu)
    ListView menu;

    @Bean
    DataContainer dataContainer;


    @AfterViews
    void start() {
        menu.setAdapter(dataContainer.getMenuAdapter());
    }


    @ItemClick(R.id.menu)
    void menuClick(MenuItem item) {
        item.onClick(getBaseActivity());
    }


}
