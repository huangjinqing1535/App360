package com.huang.app360.module.home.necessary;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;
import com.huang.app360.module.home.entity.NecessaryBean;
import com.huang.app360.network.RetrofitHelper;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huang on 2018/3/8.
 */

public class HomeNecessaryFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private NecessaryAdapter necessaryAdapter;
    private NecessaryBean mNecessaryBean;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_necessary;
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
        necessaryAdapter = new NecessaryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.gray_decoration));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(necessaryAdapter);
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getInstance().getNecessaryService().getNecessary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NecessaryBean>() {
                    @Override
                    public void accept(@NonNull NecessaryBean necessaryBean) throws Exception {
                        mNecessaryBean = necessaryBean;
                        finishTask();
                    }
                });
    }

    @Override
    protected void finishTask() {
        swipeRefreshLayout.setRefreshing(false);
        if (mNecessaryBean!=null){
            necessaryAdapter.setNewData(mNecessaryBean.getData());
        }
    }
}
