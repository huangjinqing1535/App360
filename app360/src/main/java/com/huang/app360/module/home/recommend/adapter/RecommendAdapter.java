package com.huang.app360.module.home.recommend.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huang.app360.R;
import com.huang.app360.constant.BaseConstant;
import com.huang.app360.module.home.entity.RecommendBean;
import com.huang.app360.widget.banner.BannerView;

import java.util.List;

/**
 * Created by huang on 2018/3/15.
 */

public class RecommendAdapter  extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public RecommendAdapter() {
        super(null);
        addItemType(BaseConstant.RECOMMEND_BANNER, R.layout.recommend_banner);
        addItemType(BaseConstant.RECOMMEND_TOP_HOT, R.layout.recommend_hot);
//        addItemType(BaseConstant.RECOMMEND_LIST, R.layout.recommend_list);
//        addItemType(BaseConstant.RECOMMEND_NEW, R.layout.recommend_new);
//        addItemType(BaseConstant.RECOMMEND_HOR, R.layout.recommend_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        Log.e("qing","type=="+helper.getItemViewType());
        switch (helper.getItemViewType()){
            case BaseConstant.RECOMMEND_BANNER:

                BannerView bannerView = helper.getView(R.id.banner);
                if (item instanceof RecommendBean.DataBean){
                    Log.i("qing","instanceof==");
                    RecommendBean.DataBean dataBean = (RecommendBean.DataBean) item;
                    bannerView.setBannnerData(dataBean.getBanners());
                }
                break;
            case BaseConstant.RECOMMEND_TOP_HOT:
                RecyclerView mHotView = helper.getView(R.id.recycler_view);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
                mHotView.setLayoutManager(linearLayoutManager);
                if (item instanceof RecommendBean.DataBean){
                    RecommendBean.DataBean dataBean2 = (RecommendBean.DataBean) item;
                    TopHomeAdapter topHomeAdapter = new TopHomeAdapter(R.layout.recommend_hot_item,dataBean2.getBlock_list().get(0).getApps());
                    mHotView.setAdapter(topHomeAdapter);
                }
                break;

        }
    }
}
