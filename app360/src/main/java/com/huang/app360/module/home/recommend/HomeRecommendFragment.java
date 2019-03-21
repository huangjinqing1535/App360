package com.huang.app360.module.home.recommend;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;
import com.huang.app360.module.home.entity.RecommendBean;
import com.huang.app360.module.home.recommend.adapter.RecommendAdapter;
import com.huang.app360.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huang on 2018/3/8.
 */

public class HomeRecommendFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private RecommendAdapter recommendAdapter;
    @BindView(R.id.recycle)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void finishCreateView(Bundle savedInstanceState) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
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
        recommendAdapter = new RecommendAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recommendAdapter);
    }

    private RecommendBean recommendBean;
    @Override
    protected void loadData() {
        RetrofitHelper.getInstance().getRecommendService()
                .getHomeJson().
                compose(this.<RecommendBean>bindToLifecycle()).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RecommendBean>() {
                    @Override
                    public void accept(@NonNull RecommendBean bean) throws Exception {
                        recommendBean = bean;
                        finishTask();

                    }
                });
    }
    private List<MultiItemEntity> multiItemEntityList = new ArrayList<>();
    @Override
    protected void finishTask() {
        swipeRefreshLayout.setRefreshing(false);
        Log.i("qing", recommendBean.toString());
        if (recommendBean!=null){

            RecommendBean.DataBean bannerBean = new RecommendBean.DataBean();
            bannerBean.setBanners(recommendBean.getData().getBanners());
            multiItemEntityList.add(bannerBean);

            RecommendBean.DataBean topBean = new RecommendBean.DataBean();
            topBean.setBlock_list(recommendBean.getData().getBlock_list());
            multiItemEntityList.add(topBean);

            recommendAdapter.setNewData(multiItemEntityList);

        }
    }
}
