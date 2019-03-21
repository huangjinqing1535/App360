package com.huang.app360.module.game.rank;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;
import com.huang.app360.module.game.entity.GameRankBean;
import com.huang.app360.module.home.rank.RankAdapter;
import com.huang.app360.network.RetrofitHelper;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huang on 2018/3/28.
 */

public class GameRankFragment extends RxLazyFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private GameRankAdapter gameRankAdapter;
    private GameRankBean gameRankBean;
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
        Log.i("huang","lazyLoad");
        if (!isPrepared||!isVisible){
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
        loadData();
    }

    @Override
    protected void initRecyclerView() {

        gameRankAdapter = new GameRankAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.gray_decoration));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(gameRankAdapter);
    }


    @Override
    protected void loadData() {
        RetrofitHelper.getInstance().getGameService().getGameRankBean()
                .compose(this.<GameRankBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GameRankBean>() {
                    @Override
                    public void accept(@NonNull GameRankBean bean) throws Exception {
                        gameRankBean = bean;
                        finishTask();
                    }
                });
    }

    @Override
    protected void finishTask() {
        if (gameRankBean!=null){
            gameRankAdapter.setNewData(gameRankBean.getData());
        }
    }

    @Override
    protected void loadError() {

    }
}
