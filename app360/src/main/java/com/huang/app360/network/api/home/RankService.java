package com.huang.app360.network.api.home;

import com.huang.app360.module.home.entity.RankBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by huang on 2018/3/22.
 */

public interface RankService {

    @GET("rank/home?&prepage=categorysoft_recommend&curpage=recommend_ranking&page=1&os=24&os_version=7.0&vc=300070177&v=7.1.77&md=PRA-AL00X&sn=4.394670136694171&cpu=&ca1=armeabi-v7a&ca2=armeabi&m=af72271bdf5dde55ee8cec2752114388&m2=60fb190678e7891fdfe9ee336f866e89&ch=8967314&ppi=1080_1812&startCount=1&pvc=260&pvn=2.6.0&re=1&tid=0&cpc=1&snt=-1&nt=1&gender=0&age=24&newuser=0&theme=2&br=HONOR&carrier_id=70120&s_3pk=1&webp=1")
    Observable<RankBean> getRankList();

}
