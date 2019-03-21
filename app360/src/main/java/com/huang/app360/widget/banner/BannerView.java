package com.huang.app360.widget.banner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huang.app360.R;
import com.huang.app360.manager.glide.GlideManager;
import com.huang.app360.module.home.entity.RecommendBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2018/3/20.
 */

public class BannerView extends LinearLayout {

    private ViewPager mViewPager;
    private List<ImageView> mImageViewList;
    private List<RecommendBean.DataBean.BannersBean> mbannersBeanList;
    private ViewPagerAdapetr viewPagerAdapetr;

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.banner_view,null);
        addView(mView,new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        viewPagerAdapetr = new ViewPagerAdapetr();
        mViewPager.setAdapter(viewPagerAdapetr);
    }


    public void setBannnerData(List<RecommendBean.DataBean.BannersBean> bannersBeanList){
        mImageViewList = new ArrayList<>();
        mbannersBeanList = bannersBeanList;
        for (RecommendBean.DataBean.BannersBean bannersBean:mbannersBeanList){
            ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.image_view,null).findViewById(R.id.image_view);
            GlideManager.loadUrl(getContext(),bannersBean.getImage_url_704_244(),imageView);
            mImageViewList.add(imageView);
        }
        viewPagerAdapetr.updateData(mImageViewList);
        viewPagerAdapetr.notifyDataSetChanged();
    }


    class ViewPagerAdapetr extends PagerAdapter{
        private List<ImageView> imageViews = new ArrayList<>();
        public void updateData(List<ImageView> imageViews){
            this.imageViews = imageViews;
        };
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= imageViews.size();
            if (position < 0) {
                position = imageViews.size() + position;
            }
            ImageView imageView = imageViews.get(position);

            ViewParent vp = imageView.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }

}
