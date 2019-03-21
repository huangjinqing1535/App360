package com.huang.app360.network.api.home;

import com.huang.app360.module.home.entity.EntertainmentBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by huang on 2018/3/25.
 */

public interface EntertainmentService {
    @GET("Iservice/Yule?&prepage=app_num_app&curpage=recommend_entertainment&os=19&os_version=4.4.2&vc=300070177&v=7.1.77&md=SM-G9350&sn=6.884084906507182&cpu=qualcomm+msm+8974+hammerhead+%28flattened+device+tree%29&ca1=armeabi-v7a&ca2=armeabi&m=26e7063f15f5fbaca090e4de3ae5a095&m2=abfa6a0946099d927126adbf16c476c2&ch=710940&ppi=1080_1920&startCount=1&pvc=270&pvn=2.7.0&re=1&tid=0&cpc=1&snt=-1&nt=1&gender=-1&age=0&newuser=0&theme=2&br=samsung&carrier_id=70120&s_3pk=1&webp=1")
    Observable<EntertainmentBean> getEntertainmentBean();

}
