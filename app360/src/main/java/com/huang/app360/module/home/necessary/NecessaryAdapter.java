package com.huang.app360.module.home.necessary;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huang.app360.R;
import com.huang.app360.manager.glide.GlideManager;
import com.huang.app360.module.home.entity.NecessaryBean;
import com.huang.app360.util.MathUtil;

import java.util.List;

/**
 * Created by huang on 2018/3/23.
 */

public class NecessaryAdapter extends BaseQuickAdapter<NecessaryBean.DataBean,BaseViewHolder> {

    public NecessaryAdapter() {
        super(R.layout.recommend_new);
    }

    @Override
    protected void convert(BaseViewHolder helper, NecessaryBean.DataBean item) {
        RecyclerView recyclerView = helper.getView(R.id.list_recycler_view);
        helper.getView(R.id.app_group_all).setVisibility(View.GONE);
        helper.setText(R.id.app_group_title,item.getCategory_name());
        switch (item.getCategory_type()){
            case 1:
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
               List<NecessaryBean.DataBean.ApplistBean> beanList =  item.getApplist();
               if (beanList.size()>4)recyclerView.setAdapter(new ListAdapter(beanList.subList(0,4)));
                else recyclerView.setAdapter(new ListAdapter(beanList));
                break;
            case 2:
                recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
                List<NecessaryBean.DataBean.ApplistBean> dataList =  item.getApplist();
                if (dataList.size()>9)recyclerView.setAdapter(new GridAdapter(dataList.subList(0,8)));
                else recyclerView.setAdapter(new GridAdapter(dataList));
                break;
        }
    }


    class GridAdapter extends BaseQuickAdapter<NecessaryBean.DataBean.ApplistBean,BaseViewHolder>{

        public GridAdapter(@Nullable List<NecessaryBean.DataBean.ApplistBean> data) {
            super(R.layout.necessary_grid_item,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, NecessaryBean.DataBean.ApplistBean item) {
            ImageView imageView = helper.getView(R.id.item_image);
            GlideManager.loadUrl(mContext,item.getLogo_url(),imageView);
            helper.setText(R.id.item_name,item.getName());
        }
    }


    class ListAdapter extends BaseQuickAdapter<NecessaryBean.DataBean.ApplistBean,BaseViewHolder>{

        public ListAdapter(@Nullable List<NecessaryBean.DataBean.ApplistBean> data) {
            super(R.layout.necessary_list_item,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, NecessaryBean.DataBean.ApplistBean item) {
            ImageView imageView = helper.getView(R.id.item_image);
            GlideManager.loadUrl(mContext,item.getLogo_url(),imageView);
            helper.setText(R.id.app_name,item.getName());
            helper.setText(R.id.item_info, MathUtil.getDownloadTimes(item.getDownload_times())+"  "+MathUtil.formatFileSize(item.getSize()));
            helper.setText(R.id.item_desc,item.getSingle_word());
        }
    }
}
