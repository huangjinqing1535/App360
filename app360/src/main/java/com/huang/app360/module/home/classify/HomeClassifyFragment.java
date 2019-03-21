package com.huang.app360.module.home.classify;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;
import com.huang.app360.module.home.classify.adapter.CategoryAdapter;
import com.huang.app360.module.home.entity.CategoryBean;
import com.huang.app360.module.home.recommend.adapter.RecommendAdapter;
import com.huang.app360.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huang on 2018/3/8.
 */

public class HomeClassifyFragment extends RxLazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CategoryAdapter categoryAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_classify;
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
        categoryAdapter = new CategoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.gray_decoration));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getInstance().getCategoryService().getCategoryGroup()
                .map(new Function<CategoryBean, List<CategoryBean.DataBean>>() {
                    @Override
                    public List<CategoryBean.DataBean> apply(@NonNull CategoryBean categoryBean) throws Exception {
                        List<CategoryBean.DataBean> data = new ArrayList<CategoryBean.DataBean>();
                        List<CategoryBean.DataBean> dataBeanList = categoryBean.getData();
                        if (dataBeanList!=null){
                            for (CategoryBean.DataBean bean:dataBeanList){
                                if (bean.getTag2()!=null&&bean.getTitle2()!=null){
                                    data.add(bean);
                                }
                            }
                        }
                        return data;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryBean.DataBean>>() {
                    @Override
                    public void accept(@NonNull List<CategoryBean.DataBean> dataBeenList) throws Exception {
                        finishTask();
                        categoryAdapter.setNewData(dataBeenList);
                    }
                });
    }

    @Override
    protected void finishTask() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
