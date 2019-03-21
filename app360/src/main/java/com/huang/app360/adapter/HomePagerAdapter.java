package com.huang.app360.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.huang.app360.R;
import com.huang.app360.module.home.app.HomeAppFramgnet;
import com.huang.app360.module.home.classify.HomeClassifyFragment;
import com.huang.app360.module.home.entertainment.HomeEntertainmentFragment;
import com.huang.app360.module.home.necessary.HomeNecessaryFragment;
import com.huang.app360.module.home.rank.HomeRankListFragment;
import com.huang.app360.module.home.recommend.HomeRecommendFragment;
import com.huang.app360.module.home.welfare.HomeWelfareFragment;

/**
 * Created by huang on 2018/3/8.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] titles;
    public HomePagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
        titles = mContext.getResources().getStringArray(R.array.home_title);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new HomeRecommendFragment();
            case 1:return new HomeClassifyFragment();
            case 2:return new HomeRankListFragment();
            case 3:return new HomeNecessaryFragment();
            case 4:return new HomeWelfareFragment();
            case 5:return new HomeAppFramgnet();
            case 6:return new HomeEntertainmentFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
