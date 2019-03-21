package com.huang.app360.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huang.app360.R;
import com.huang.app360.module.game.activity.GameActivityFragment;
import com.huang.app360.module.game.classify.GameClassifyFragment;
import com.huang.app360.module.game.findgame.FindGameFragment;
import com.huang.app360.module.game.rank.GameRankFragment;
import com.huang.app360.module.game.recommend.GameRecommendFragment;
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

public class GamePagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] titles;
    public GamePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        titles = mContext.getResources().getStringArray(R.array.game_title);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new GameRecommendFragment();
            case 1:return new FindGameFragment();
            case 2:return new GameClassifyFragment();
            case 3:return new GameRankFragment();
            case 4:return new GameActivityFragment();
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
