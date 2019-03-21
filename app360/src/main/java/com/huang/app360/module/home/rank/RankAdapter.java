package com.huang.app360.module.home.rank;

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
import com.huang.app360.module.home.entity.RankBean;
import com.huang.app360.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2018/3/22.
 */

public class RankAdapter extends BaseQuickAdapter<RankBean.DataBean,BaseViewHolder> {

    public RankAdapter() {
        super(R.layout.ranking_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, RankBean.DataBean item) {
        ImageView topImage = helper.getView(R.id.top_image);
        GlideManager.loadUrl(mContext,item.getIcon_url(),topImage);
        TextView topTitle = helper.getView(R.id.top_title);
        topTitle.setText(item.getCat_name());
        RecyclerView recyclerView = helper.getView(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ItemRecyclerAdapter(item));
    }

    class ItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private RankBean.DataBean dataBean;
        public ItemRecyclerAdapter(RankBean.DataBean item) {
            dataBean = item;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(mContext).inflate(R.layout.rank_item_layout,parent,false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(mView);
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            LastItemAdapter lastItemAdapter = new LastItemAdapter();
            itemViewHolder.recyclerView.setAdapter(lastItemAdapter);
            List<RankBean.DataBean.AppsBean> appsBeanList = new ArrayList<>();
            for (int i=position*3;i<(position+1)*3;i++){
                appsBeanList.add(dataBean.getApps().get(i));
            }
            lastItemAdapter.setNewData(appsBeanList);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        }
    }

    class LastItemAdapter extends BaseQuickAdapter<RankBean.DataBean.AppsBean,BaseViewHolder>{

        public LastItemAdapter() {
            super(R.layout.rank_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, RankBean.DataBean.AppsBean item) {
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
