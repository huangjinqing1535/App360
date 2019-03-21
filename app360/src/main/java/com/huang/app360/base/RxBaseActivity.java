package com.huang.app360.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;


import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huang on 2018/3/6.
 */

public abstract class RxBaseActivity extends RxAppCompatActivity {

    private Unbinder bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
    }

    /**
     * 返回布局id
     */
    protected abstract int getLayoutId();
    /**
     * 初始化View
     */
    protected abstract void initViews(Bundle savedInstanceState);
    /**
     * 初始化Toolbar
     */
    protected abstract void initToolBar();

    /**
     * 初始化recyclerView
     */
    public void initRecyclerView() {
    }

    /**
     * 初始化refreshLayout
     */
    public void initRefreshLayout() {
    }

    /**
     * 设置数据显示
     */
    public void finishTask() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
