package com.duanjiefei.github.liveplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;

import com.duanjiefei.github.liveplayer.base.BaseActivity;
import com.duanjiefei.github.liveplayer.fragment.AboutFragment;
import com.duanjiefei.github.liveplayer.fragment.BlogFragment;
import com.duanjiefei.github.liveplayer.fragment.FragmentManagerWrapper;
import com.duanjiefei.github.liveplayer.fragment.HomeFragment;

public class HomeActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private  ActionBarDrawerToggle mActionBarDrawerToggle;

    private MenuItem mPreItem;
    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void initView() {
        setSupportActionBar();
        setActionBarIcon(R.drawable.ic_drawer_home);
        setTitle("主页");

        mDrawerLayout = bindViewID(R.id.drawLayout);
        mNavigationView = bindViewID(R.id.navigation_view);


        mActionBarDrawerToggle =  new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        mPreItem = mNavigationView.getMenu().getItem(0);
        mPreItem.setCheckable(true);  //默认选中第一项item
        initFragment();
        handelNavigationView();
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = FragmentManagerWrapper.getInstance().crateFragment(HomeFragment.class);
        mFragmentManager.beginTransaction().add(R.id.fl_home_content,mCurrentFragment).commit();
    }

    private void handelNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_item_video:
                        switchFragment(HomeFragment.class);
                        mToolbar.setTitle(R.string.fragment_home);
                        break;
                    case R.id.navigation_item_blog:
                        switchFragment(BlogFragment.class);
                        mToolbar.setTitle(R.string.fragment_blog);
                        break;
                    case R.id.navigation_item_about:
                        switchFragment(AboutFragment.class);
                        mToolbar.setTitle(R.string.fragment_about);
                        break;
                }
                item.setCheckable(true);
                mPreItem = item;
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }

    private void switchFragment(Class<?> clazz) {
        Fragment fragment = FragmentManagerWrapper.getInstance().crateFragment(clazz);
        if (fragment.isAdded()){
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        }else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.fl_home_content,fragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
    }
}
