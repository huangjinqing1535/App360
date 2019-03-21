package com.huang.app360.module.game.classify;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioGroup;

import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;
import com.huang.app360.module.game.entity.GameClass;
import com.huang.app360.network.RetrofitHelper;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huang on 2018/3/28.
 */

public class GameClassifyFragment extends RxLazyFragment {
    @BindView(R.id.radio_menu)
    RadioGroup radioMenu;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private int checkItem = 1;
    private GameClassifyAdapter gameClassifyAdapter;
    private GameClass mGameClass;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_classify;
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
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gameClassifyAdapter = new GameClassifyAdapter();
        mRecyclerView.setAdapter(gameClassifyAdapter);
        radioMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_play_method:
                        checkItem = 1;
                        loadData();
                        break;
                    case R.id.radio_game_theme:
                        checkItem = 2;
                        loadData();
                        break;
                    case R.id.radio_paint_style:
                        checkItem = 3;
                        loadData();
                        break;
                }
            }
        });
        loadData();

    }

    @Override
    protected void loadData() {
        switch (checkItem) {
            case 1:
                RetrofitHelper.getInstance().getGameService().getGameClassPlayMethod()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<GameClass>() {
                            @Override
                            public void accept(@NonNull GameClass gameClass) throws Exception {
                                mGameClass = gameClass;
                                finishTask();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {

                            }
                        });
                break;
            case 2:
                RetrofitHelper.getInstance().getGameService().getGameClassTheme()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<GameClass>() {
                            @Override
                            public void accept(@NonNull GameClass gameClass) throws Exception {
                                mGameClass = gameClass;
                                finishTask();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {

                            }
                        });
                break;
            case 3:
                RetrofitHelper.getInstance().getGameService().getGameClassPaintStyle()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<GameClass>() {
                            @Override
                            public void accept(@NonNull GameClass gameClass) throws Exception {
                                mGameClass = gameClass;
                                finishTask();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {

                            }
                        });
                break;
        }
    }

    @Override
    protected void finishTask() {
        if (mGameClass!=null){
            gameClassifyAdapter.setNewData(mGameClass.getData());
        }
    }
}
