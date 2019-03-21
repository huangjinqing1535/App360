package com.huang.app360.module.home.app;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;
import com.huang.app360.module.home.entity.AppBean;
import com.huang.app360.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huang on 2018/3/8.
 */

public class HomeAppFramgnet extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private HomeAppAdapter homeAppAdapter;
    private String articleId = "";
    private AppBean appBean;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_app;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.gray_decoration));
        recyclerView.addItemDecoration(divider);

        homeAppAdapter = new HomeAppAdapter();
        recyclerView.setAdapter(homeAppAdapter);
    }


    @Override
    protected void loadData() {
        RetrofitHelper.getInstance().getAppService().getAppBeanById()
                .subscribeOn(Schedulers.io())
                .compose(this.<AppBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AppBean>() {
                    @Override
                    public void accept(@NonNull AppBean bean) throws Exception {
                        appBean = bean;
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
        if (appBean!=null){
            List<MultiItemEntity> dataList = new ArrayList<>();
            AppBean bean = new AppBean();
            bean.setTags(appBean.getTags());
            dataList.add(bean);

            dataList.addAll(appBean.getData());
            homeAppAdapter.setNewData(dataList);
        }
    }
}
