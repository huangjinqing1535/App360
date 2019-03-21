package com.huang.app360.module.home.classify.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huang.app360.R;
import com.huang.app360.manager.glide.GlideManager;
import com.huang.app360.module.home.entity.CategoryBean;

import java.util.List;

/**
 * Created by huang on 2018/3/21.
 */

public class CategoryAdapter extends BaseQuickAdapter<CategoryBean.DataBean,BaseViewHolder> {
    public CategoryAdapter() {
        super(R.layout.item_category_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean.DataBean item) {
        ImageView imageView = helper.getView(R.id.category_image);
        GlideManager.loadUrl(mContext,item.getLogo(),imageView);

        TextView category_title = helper.getView(R.id.category_title);
        category_title.setText(item.getTitle()+">");

        RecyclerView recyclerView = helper.getView(R.id.recycler_grid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        List<String> titles = item.getTitle2();
        if (titles.size()>6){
            List<String> titleList =  titles.subList(0, 6);
            recyclerView.setAdapter(new CategoryItemAdapter(titleList));
        }else{
            recyclerView.setAdapter(new CategoryItemAdapter(titles));
        }

    }

    class CategoryItemAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
        private ItemClickListener onItemClickListener;
        public CategoryItemAdapter(List<String> tags) {
            super(R.layout.item_text_view,tags);
        }

        public void setOnItemListener(ItemClickListener listener){
            onItemClickListener = listener;
        }

        @Override
        protected void convert(BaseViewHolder helper,final String item) {
            TextView tv = helper.getView(R.id.text);
            tv.setText(item);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null)onItemClickListener.onItemClick(item);
                }
            });
        }
    }

    interface ItemClickListener{
        void onItemClick(String tag);
    }

}
