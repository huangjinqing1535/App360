package com.huang.app360.module.game;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.huang.app360.R;
import com.huang.app360.adapter.GamePagerAdapter;
import com.huang.app360.adapter.HomePagerAdapter;
import com.huang.app360.base.RxLazyFragment;

import butterknife.BindView;

/**
 * Created by huang on 2018/3/8.
 */

public class GamePagerFragment extends RxLazyFragment {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_pager;
    }

    @Override
    protected void finishCreateView(Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new GamePagerAdapter(getActivity(),getChildFragmentManager()));
        //与viewpager绑定
        mTabLayout.setupWithViewPager(mViewPager);
        // MODE_SCROLLABLE：Scrollview格式
        // MODE_FIXED：所有的tab全部显示
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

}
