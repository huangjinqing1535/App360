package com.huang.app360.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huang.app360.R;
import com.huang.app360.module.home.app.HomeAppFramgnet;
import com.huang.app360.module.home.classify.HomeClassifyFragment;
import com.huang.app360.module.home.entertainment.HomeEntertainmentFragment;
import com.huang.app360.module.home.necessary.HomeNecessaryFragment;
import com.huang.app360.module.home.rank.HomeRankListFragment;
import com.huang.app360.module.home.recommend.HomeRecommendFragment;
import com.huang.app360.module.home.welfare.HomeWelfareFragment;
import com.huang.app360.module.software.classify.SoftwareClassifyFragment;
import com.huang.app360.module.software.from360.Software360Fragment;
import com.huang.app360.module.software.rank.SoftwareRankFragment;
import com.huang.app360.module.software.recommend.SoftwareRecommendFragment;

/**
 * Created by huang on 2018/3/8.
 */

public class SoftwarePagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] titles;
    public SoftwarePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        titles = mContext.getResources().getStringArray(R.array.software_title);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new SoftwareRecommendFragment();
            case 1:return new SoftwareClassifyFragment();
            case 2:return new SoftwareRankFragment();
            case 3:return new Software360Fragment();
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
