package com.huang.app360.module.game.classify;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huang.app360.R;
import com.huang.app360.manager.glide.GlideManager;
import com.huang.app360.module.game.entity.GameClass;

import java.util.List;

/**
 * Created by huang on 2018/4/16.
 */

public class GameClassifyAdapter extends BaseQuickAdapter<GameClass.DataBean,BaseViewHolder> {

    public GameClassifyAdapter() {
        super( R.layout.game_classify_item,null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GameClass.DataBean item) {

        helper.setText(R.id.item_title,item.getCase_name()).setText(R.id.sub_title,item.getGame_tag_name());
        GlideManager.loadUrl(mContext,item.getIcon(), (ImageView) helper.getView(R.id.item_image));
    }
}
