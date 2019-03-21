package com.huang.app360.module.home.entertainment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;
import com.huang.app360.module.home.entity.EntertainmentBean;
import com.huang.app360.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huang on 2018/3/8.
 */

public class HomeEntertainmentFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private EntertainmentAdapter entertainmentAdapter;
    private EntertainmentBean entertainmentBean;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_entertainment;
    }

    @Override
    protected void finishCreateView(Bundle savedInstanceState) {
         isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared||!isVisible){
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initRefreshLayout() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    protected void initRecyclerView() {
        entertainmentAdapter = new EntertainmentAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.gray_decoration));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(entertainmentAdapter);
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getInstance().getEntertainmentService().getEntertainmentBean()
                .compose(this.<EntertainmentBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EntertainmentBean>() {
                    @Override
                    public void accept(@NonNull EntertainmentBean bean) throws Exception {
                        entertainmentBean = bean;
                        finishTask();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    protected void finishTask() {
        swipeRefreshLayout.setRefreshing(false);
        List<MultiItemEntity> dataList = new ArrayList<>();
        if (entertainmentBean!=null){
            EntertainmentBean mBean = new EntertainmentBean();
            mBean.setTags(entertainmentBean.getTags());
            dataList.add(mBean);

            EntertainmentBean.DataBeanXXXX dataBeanXXXX3 = new EntertainmentBean.DataBeanXXXX();
            dataBeanXXXX3.setTVplayData(entertainmentBean.getData().getTVplayData());
            dataList.add(dataBeanXXXX3);


            EntertainmentBean.DataBeanXXXX.EbookDataBean ebookDataBean = new EntertainmentBean.DataBeanXXXX.EbookDataBean();
            ebookDataBean.setData(entertainmentBean.getData().getEbookData().getData());
            dataList.add(ebookDataBean);

            EntertainmentBean.DataBeanXXXX.NewsDataBean newsDataBean = new EntertainmentBean.DataBeanXXXX.NewsDataBean();
            newsDataBean.setData(entertainmentBean.getData().getNewsData().getData());
            dataList.add(newsDataBean);




        }
        entertainmentAdapter.setNewData(dataList);
    }
}
