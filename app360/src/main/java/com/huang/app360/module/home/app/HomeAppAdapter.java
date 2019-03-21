package com.huang.app360.module.home.app;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huang.app360.R;
import com.huang.app360.constant.BaseConstant;
import com.huang.app360.manager.glide.GlideManager;
import com.huang.app360.module.home.entity.AppBean;
import com.huang.app360.util.MathUtil;

import java.util.List;

/**
 * Created by huang on 2018/3/27.
 */

public class HomeAppAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {

    public HomeAppAdapter() {
        super(null);
        addItemType(BaseConstant.App_Top, R.layout.app_top_layout);
        addItemType(BaseConstant.App_Item, R.layout.app_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case BaseConstant.App_Top:
                if (item instanceof AppBean){
                    AppBean appBean = (AppBean) item;
                    List<AppBean.TagsBean> tagsBeen = appBean.getTags();
                    helper.setText(R.id.desc1,tagsBeen.get(0).getSub_title());
                    GlideManager.loadUrl(mContext,tagsBeen.get(0).getSub_title_image(), (ImageView) helper.getView(R.id.small_img));
                    GlideManager.loadUrl(mContext,tagsBeen.get(0).getIcon(), (ImageView) helper.getView(R.id.big_img));

                    helper.setText(R.id.desc2,tagsBeen.get(1).getSub_title());
                    GlideManager.loadUrl(mContext,tagsBeen.get(1).getSub_title_image(), (ImageView) helper.getView(R.id.small_img2));
                    GlideManager.loadUrl(mContext,tagsBeen.get(1).getIcon(), (ImageView) helper.getView(R.id.big_img2));
                }
                break;
            case BaseConstant.App_Item:
                if (item instanceof AppBean.DataBean){
                    AppBean.DataBean dataBean = (AppBean.DataBean) item;
                    helper.setText(R.id.item_title,dataBean.getTitle())
                            .setText(R.id.item_subtitle,dataBean.getDigest())
                            .setText(R.id.app_name,dataBean.getApp_infos().get(0).getName())
                            .setText(R.id.app_size, MathUtil.formatFileSize(dataBean.getApp_infos().get(0).getSize()))
                            .setText(R.id.author_name,dataBean.getAccount_info().getName());

                    GlideManager.loadUrl(mContext,dataBean.getThumb().get(0), (ImageView) helper.getView(R.id.item_image));
                    GlideManager.loadUrl(mContext,dataBean.getApp_infos().get(0).getLogo_url(), (ImageView) helper.getView(R.id.app_image));
                    GlideManager.loadUrl(mContext,dataBean.getAccount_info().getAvatar(), (ImageView) helper.getView(R.id.author_img));
                }

                break;
        }
    }
}
