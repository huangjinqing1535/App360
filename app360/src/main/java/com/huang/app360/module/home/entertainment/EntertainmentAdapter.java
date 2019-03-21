package com.huang.app360.module.home.entertainment;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huang.app360.R;
import com.huang.app360.constant.BaseConstant;
import com.huang.app360.manager.glide.GlideManager;
import com.huang.app360.module.home.entity.EntertainmentBean;

import java.util.List;

/**
 * Created by huang on 2018/3/25.
 */

public class EntertainmentAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {



    public EntertainmentAdapter() {
        super(null);
        addItemType(BaseConstant.Entertainment_Tags, R.layout.recyclerview_layout);
        addItemType(BaseConstant.Entertainment_Tv_Ebook, R.layout.base_recycler_with_title);
        addItemType(BaseConstant.Entertainment_News, R.layout.base_recycler_with_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        switch (helper.getItemViewType()){
            case BaseConstant.Entertainment_Tags:
                EntertainmentBean mBean = (EntertainmentBean) item;
                recyclerView.setLayoutManager(new GridLayoutManager(mContext,5));
                recyclerView.setAdapter(new TagAdapter(mBean.getTags()));
                break;
            case BaseConstant.Entertainment_Tv_Ebook:
                recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
                if (item instanceof EntertainmentBean.DataBeanXXXX.EbookDataBean){
                    helper.setText(R.id.app_group_title,"电子书");
                    EntertainmentBean.DataBeanXXXX.EbookDataBean ebookDataBean = (EntertainmentBean.DataBeanXXXX.EbookDataBean) item;
                    recyclerView.setAdapter(new TVAndEbookAdapter(ebookDataBean.getData()));
                }else if (item instanceof EntertainmentBean.DataBeanXXXX){
                    helper.setText(R.id.app_group_title,"影视");
                    EntertainmentBean.DataBeanXXXX dataBeanXXXX = (EntertainmentBean.DataBeanXXXX) item;
                    recyclerView.setAdapter(new TVAndEbookAdapter(dataBeanXXXX.getTVplayData()));
                }
                break;
            case BaseConstant.Entertainment_News:
                helper.setText(R.id.app_group_title,"新闻");
                EntertainmentBean.DataBeanXXXX.NewsDataBean newsDataBean = (EntertainmentBean.DataBeanXXXX.NewsDataBean) item;
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.setAdapter(new NewsAdapter(newsDataBean.getData()));
                break;
        }
    }

    class TagAdapter extends BaseQuickAdapter<EntertainmentBean.TagsBean,BaseViewHolder>{

        public TagAdapter(@Nullable List<EntertainmentBean.TagsBean> data) {
            super(R.layout.entertainment_tag,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, EntertainmentBean.TagsBean item) {
            helper.setText(R.id.tag_title,item.getTitle());
            ImageView imageView = helper.getView(R.id.tag_image);
            GlideManager.loadUrl(mContext,item.getLogo(),imageView);
        }
    }

    class TVAndEbookAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder>{

        public TVAndEbookAdapter(@Nullable List<T> data) {
            super(R.layout.entertainment_tv_ebook,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            ImageView imageView = helper.getView(R.id.tv_images);
            if (item instanceof EntertainmentBean.DataBeanXXXX.TVplayDataBean){
                EntertainmentBean.DataBeanXXXX.TVplayDataBean tVplayDataBean = (EntertainmentBean.DataBeanXXXX.TVplayDataBean) item;
                GlideManager.loadUrl(mContext,tVplayDataBean.getCover(),imageView);
                helper.setText(R.id.tv_name,tVplayDataBean.getTitle());
                helper.setText(R.id.tv_desc,tVplayDataBean.getWord());
            }else if (item instanceof EntertainmentBean.DataBeanXXXX.EbookDataBean.DataBean){
                EntertainmentBean.DataBeanXXXX.EbookDataBean.DataBean  dataBean = (EntertainmentBean.DataBeanXXXX.EbookDataBean.DataBean) item;
                GlideManager.loadUrl(mContext,dataBean.getSmallPic(),imageView);
                helper.setText(R.id.tv_name,dataBean.getName()).setText(R.id.tv_desc,dataBean.getAuthor());
            }
        }
    }


    class NewsAdapter extends BaseQuickAdapter<EntertainmentBean.DataBeanXXXX.NewsDataBean.DataBeanXX,BaseViewHolder>{

        public NewsAdapter(@Nullable List<EntertainmentBean.DataBeanXXXX.NewsDataBean.DataBeanXX> data) {
            super(R.layout.entertainment_news,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, EntertainmentBean.DataBeanXXXX.NewsDataBean.DataBeanXX item) {
            ImageView imageView = helper.getView(R.id.news_image);
            String imageUrl = item.getI().split("\\|")[0];
            GlideManager.loadUrl(mContext,imageUrl,imageView);
            helper.setText(R.id.news_name,item.getT()).setText(R.id.news_desc,item.getF());
        }
    }
}
