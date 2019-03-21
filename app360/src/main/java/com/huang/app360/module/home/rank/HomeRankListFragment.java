package com.huang.app360.module.home.rank;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;
import com.huang.app360.module.home.classify.adapter.CategoryAdapter;
import com.huang.app360.module.home.entity.RankBean;
import com.huang.app360.network.RetrofitHelper;

import butterknife.BindView;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huang on 2018/3/8.
 */

public class HomeRankListFragment extends RxLazyFragment {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    RankAdapter rankAdapter;
    private RankBean rankBean;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_rank_list;
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
        rankAdapter = new RankAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.gray_decoration));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(rankAdapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float startX= 0,startY = 0,endX,endY;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        endX = event.getX();
                        endY = event.getX();
                        if (Math.abs(endX -startX)<Math.abs(endY-startY)){
                            return true;
                        }else{
                            return false;
                        }
                    case MotionEvent.ACTION_UP:

                        break;// 处理完
                    case MotionEvent.ACTION_DOWN:
                        startX =  event.getX();
                        startY = event.getY();
                        break;
                }
                return false;

            }
        });
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getInstance().getRankService().getRankList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RankBean>() {
                    @Override
                    public void accept(@NonNull RankBean bean) throws Exception {
                        rankBean = bean;
                        finishTask();
                    }
                });
    }

    @Override
    protected void finishTask() {
        swipeRefreshLayout.setRefreshing(false);
        if (rankBean!=null){
            rankAdapter.setNewData(rankBean.getData());
        }
    }
}
