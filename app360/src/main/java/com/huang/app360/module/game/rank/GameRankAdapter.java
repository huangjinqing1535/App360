package com.huang.app360.module.game.rank;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huang.app360.R;
import com.huang.app360.manager.glide.GlideManager;
import com.huang.app360.module.game.entity.GameRankBean;
import com.huang.app360.util.MathUtil;
import com.huang.app360.widget.viewpager.CardTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2018/3/28.
 */

public class GameRankAdapter extends BaseQuickAdapter<GameRankBean.DataBean,BaseViewHolder> {

    public GameRankAdapter() {
        super(R.layout.game_ranking_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, GameRankBean.DataBean item) {
        GlideManager.loadUrl(mContext,item.getIcon_url(), (ImageView) helper.getView(R.id.top_image));
        TextView topTitle = helper.getView(R.id.top_title);
        topTitle.setText(item.getCat_name());
        ViewPager mViewPager = helper.getView(R.id.view_pager);
        mViewPager.setAdapter(new ViewPagerAdapter(item));
        mViewPager.setPageMargin(30);
        mViewPager.setPageTransformer(true, new CardTransformer());
        mViewPager.setOffscreenPageLimit(3);

    }

    class ViewPagerAdapter extends PagerAdapter{
        private  GameRankBean.DataBean dataBean;
        public ViewPagerAdapter( GameRankBean.DataBean item) {
            this.dataBean = item;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            List<GameRankBean.DataBean.AppsBean> dataList = new ArrayList<>();
            View mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_layout,container,false);
            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            for (int i=position*3;i<(position+1)*3;i++){
                dataList.add(dataBean.getApps().get(i));
            }
            recyclerView.setAdapter(new LastItemAdapter(dataList));
            container.addView(mView);
            return mView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }

    class LastItemAdapter extends BaseQuickAdapter<GameRankBean.DataBean.AppsBean,BaseViewHolder>{

        public LastItemAdapter(List<GameRankBean.DataBean.AppsBean> data) {
            super(R.layout.rank_item,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GameRankBean.DataBean.AppsBean item) {
            ImageView itemImg = helper.getView(R.id.item_image);
            GlideManager.loadUrl(mContext,item.getLogo_url(),itemImg);

            TextView itemNum = helper.getView(R.id.item_num);

            TextView itemTitle = helper.getView(R.id.item_name);
            itemTitle.setText(item.getName());

            TextView itemDesc = helper.getView(R.id.item_desc);
            itemDesc.setText(item.getCategory_name()+"|"+ MathUtil.formatFileSize(item.getSize()));

        }
    }
}
