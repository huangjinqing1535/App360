package com.huang.app360.module.home.recommend.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huang.app360.R;
import com.huang.app360.manager.glide.GlideManager;
import com.huang.app360.module.home.entity.RecommendBean;
import com.huang.app360.util.MathUtil;

import java.util.List;

/**
 * Created by huang on 2018/3/20.
 */

public class TopHomeAdapter extends BaseQuickAdapter<RecommendBean.DataBean.BlockListBean.AppsBean,BaseViewHolder> {

    public TopHomeAdapter(@LayoutRes int layoutResId, @Nullable List<RecommendBean.DataBean.BlockListBean.AppsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendBean.DataBean.BlockListBean.AppsBean item) {
        ImageView itemView = helper.getView(R.id.item_image);
        GlideManager.loadUrl(mContext,item.getLogo_url(),itemView);
        TextView app_name = helper.getView(R.id.app_name);
        app_name.setText(item.getName());
        TextView appDesc = helper.getView(R.id.app_desc);
        appDesc.setText(MathUtil.getDownloadTimes(item.getDownload_times()));

    }
}
