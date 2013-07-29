package com.template.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.template.R;
import com.template.data.DataContainer;
import com.template.fragments.BaseFragment;
import com.template.fragments.MainFragment_;
import com.template.ui.SlidingPaneLayout;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 11.07.2013
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
@EActivity(R.layout.main_activity)
public abstract class BaseActivity extends ActionBarActivity {


    protected FragmentManager fragmentManager;
    protected boolean isActive = false;
    protected InputMethodManager imm;


    @Bean
    protected DataContainer dataContainer;
    @ViewById(R.id.drawer_layout)
    protected SlidingPaneLayout drawerLayout;

    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getSupportActionBar().setTitle("");
        fragmentManager = getSupportFragmentManager();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        isActive = true;
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                onFragmentChange();
            }
        });
        getWindow().setBackgroundDrawable(null);
        this.savedInstanceState = state;
    }

    public void hideMenu() {
        drawerLayout.closePane();
    }


    @AfterViews
    protected void start() {

        if (!getResources().getBoolean(R.bool.tablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        ActionBar actionBar = getSupportActionBar();

        if (savedInstanceState == null) {
            openFragment(isTablet() ? MainFragment_.builder().build() : MainFragment_.builder().build());
            dataContainer.getMenuAdapter().resetItem();
        }

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        drawerLayout.setSliderFadeColor(Color.TRANSPARENT);
        drawerLayout.setShadowResource(R.drawable.menu_shadow);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (drawerLayout.isOpen()) {
                    drawerLayout.closePane();
                } else {
                    drawerLayout.openPane();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BaseFragment fragment = (BaseFragment) getCurrentFragment();
        if (fragment.reloadOnOrientationChange()) {
            refreshFragment();
        }
    }


    public abstract void onFragmentChange();

    public void openFragment(Fragment fragment) {
        openFragment(fragment, false);
    }

    public void openFragment(Fragment fragment, boolean addToBackStack) {
        if (isActive) {

            if (imm != null && getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            if (addToBackStack) {
                transaction.addToBackStack(null);
            } else {
                fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            transaction.replace(R.id.main_content, fragment);
            transaction.commit();
            if (!addToBackStack) {
                getSupportFragmentManager().executePendingTransactions();
                onFragmentChange();
            }

        }
    }

    public void refreshFragment() {
        if (isActive) {
            Fragment currFragment = getCurrentFragment();

            if (currFragment != null) {


                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.detach(currFragment);
                transaction.commit();
                transaction = fragmentManager.beginTransaction();
                transaction.attach(currFragment);
                transaction.commit();
            }
        }
    }

    public boolean back() {
        BaseFragment fragment = (BaseFragment) getCurrentFragment();
        if (fragment != null) {
            if (fragment.onBack()) {
                return true;
            }
        }
        if (isActive && fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    public Fragment getCurrentFragment() {
        return fragmentManager.findFragmentById(R.id.main_content);
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.tablet);
    }

}
