package com.template.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.SystemService;
import com.jsonrpclib.observers.ObserverSupportFragment;
import com.template.R;
import com.template.TemplateApp;
import com.template.activity.BaseActivity;
import com.template.ui.FragmentTitle;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 06.07.2013
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
@EFragment
public class BaseFragment extends ObserverSupportFragment {

    @SystemService
    protected InputMethodManager imm;
    private static Handler handler = new Handler(Looper.getMainLooper());


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        FragmentTitle title = getClass().getSuperclass().getAnnotation(FragmentTitle.class);
        if (title != null) {
            actionBar.setTitle(title.value());
        }

    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.tablet);
    }

    public boolean isBigTablet() {
        return getResources().getBoolean(R.bool.big_tablet);
    }

    public Fragment getFragment(String tag) {
        return getChildFragmentManager().findFragmentByTag(tag);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void showToastInUi(int msg) {
        showToastInUi(TemplateApp.instance.getResources().getString(msg));
    }

    public void showToastInUi(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TemplateApp.instance, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean onBack() {
        return false;
    }

    public boolean reloadOnOrientationChange() {
        return false;
    }

    public void back() {
        getFragmentManager().popBackStack();
    }
}
