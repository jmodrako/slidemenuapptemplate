package com.template.data;

import android.content.Context;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;
import com.jsonrpclib.observers.JsonObserver;
import com.template.R;
import com.template.adapters.MenuAdapter;
import com.template.fragments.MainFragment_;
import com.template.fragments.SecondFragment_;
import com.template.menu.MenuParent;
import com.template.menu.MenuSubItem;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 06.07.2013
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
@EBean(scope = Scope.Singleton)
public class DataContainer {


    @RootContext
    Context context;
    private MenuAdapter menuAdapter;


    @AfterInject
    void start() {

        menuLoader();
    }


    void menuLoader() {
        MenuParent[] menuParents = new MenuParent[]{
                new MenuParent(
                        R.string.app_name,
                        new MenuSubItem[]{
                                new MenuSubItem(R.string.app_name, MainFragment_.class),
                                new MenuSubItem(R.string.app_name, SecondFragment_.class)
                        }
                ),
                new MenuParent(
                        R.string.app_name,
                        new MenuSubItem[]{
                                new MenuSubItem(R.string.app_name, MainFragment_.class),
                                new MenuSubItem(R.string.app_name, SecondFragment_.class)
                        }
                )
        };

        menuAdapter = new MenuAdapter(context, menuParents);
        menuAdapter.resetItem();
        JsonObserver.setDataObject(this);
    }


    public MenuAdapter getMenuAdapter() {
        return menuAdapter;
    }

}
