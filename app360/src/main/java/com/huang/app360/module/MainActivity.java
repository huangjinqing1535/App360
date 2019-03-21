package com.huang.app360.module;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.huang.app360.R;
import com.huang.app360.base.RxBaseActivity;
import com.huang.app360.module.appgroup.AppgroupFragment;
import com.huang.app360.module.game.GamePagerFragment;
import com.huang.app360.module.home.HomePagerFragment;
import com.huang.app360.module.manager.ManagerFragment;
import com.huang.app360.module.software.SoftwarePagerFragment;

import butterknife.BindView;

/**
 * Created by huang on 2018/3/6.
 */

public class MainActivity extends RxBaseActivity implements RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.radio_group_menu)
    RadioGroup mMenuGroup;
    private Fragment mHomePagerFragment,mAppgroupFragment,mGamePagerFragment,
                mManagerFragment,mSoftwareFragment;
    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mMenuGroup.setOnCheckedChangeListener(this);
        initFragment();
    }

    @Override
    protected void initToolBar() {

    }

    private void initFragment() {
        mHomePagerFragment  = new HomePagerFragment();
        mAppgroupFragment = new AppgroupFragment();
        mGamePagerFragment = new GamePagerFragment();
        mManagerFragment = new ManagerFragment();
        mSoftwareFragment = new SoftwarePagerFragment();

        currentTabIndex = 0;
        fragments =new Fragment[]{mHomePagerFragment,mGamePagerFragment,mSoftwareFragment,mAppgroupFragment,mManagerFragment};

        ((RadioButton)mMenuGroup.getChildAt(0)).setChecked(true);
        //getSupportFragmentManager().beginTransaction().add(R.id.container_layout,mHomePagerFragment).commit();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.radio_home:
                changeFragmentIndex(0);
                break;
            case R.id.radio_game:
                changeFragmentIndex(1);
                break;
            case R.id.radio_software:
                changeFragmentIndex(2);
                break;
            case R.id.radio_appgroup:
                changeFragmentIndex(3);
                break;
            case R.id.radio_manager:
                changeFragmentIndex(4);
                break;
        }
    }

    /**
     * 切换Fragment的下标
     */
    private void changeFragmentIndex(int currentIndex) {
        index = currentIndex;
        switchFragment();
    }

    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container_layout, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }


}
