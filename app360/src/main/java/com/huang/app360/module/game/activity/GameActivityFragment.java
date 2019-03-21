package com.huang.app360.module.game.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;

import butterknife.BindView;

/**
 * Created by huang on 2018/3/28.
 */

public class GameActivityFragment extends RxLazyFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @Override
    protected int getLayoutId() {
        return R.layout.recyclerview_layout;
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

    }

    @Override
    protected void initRecyclerView() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void finishTask() {

    }
}
