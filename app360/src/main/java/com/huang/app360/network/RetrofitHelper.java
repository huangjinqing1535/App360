package com.huang.app360.network;

import com.huang.app360.App360;
import com.huang.app360.network.api.game.GameService;
import com.huang.app360.network.api.home.AppService;
import com.huang.app360.network.api.home.CategoryService;
import com.huang.app360.network.api.home.EntertainmentService;
import com.huang.app360.network.api.home.NecessaryService;
import com.huang.app360.network.api.home.RankService;
import com.huang.app360.network.api.home.RecommendService;
import com.huang.app360.util.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huang on 2018/3/6.
 */

public class RetrofitHelper {

    private static OkHttpClient mOkHttpClient;
    private static RetrofitHelper mRetrofitHelper;

    static {
        initOkHttpClient();
    }

    public static RetrofitHelper getInstance(){
        if (mRetrofitHelper==null){
            synchronized (RetrofitHelper.class){
                if (mRetrofitHelper==null){
                    mRetrofitHelper = new RetrofitHelper();
                }
            }
        }
        return mRetrofitHelper;
    }


    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(App360.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }

    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    private static class UserAgentInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 7.0; zh-cn; PRA-AL00X Build/HONORPRA-AL00X) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30;360appstore")
                    .removeHeader("Content-Type")
                    .addHeader("Content-Type"," text/html;charset=utf-8")
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(App360.getContext())) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(App360.getContext())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    public RecommendService getRecommendService(){
        return createService(RecommendService.class,ApiConstants.Recommend_Base_Url);
    }
    public CategoryService getCategoryService(){
        return createService(CategoryService.class,ApiConstants.Category_Base_Url);
    }

    public RankService getRankService(){
        return createService(RankService.class,ApiConstants.Category_Base_Url);
    }

    public NecessaryService getNecessaryService(){
        return createService(NecessaryService.class,ApiConstants.Category_Base_Url);
    }
    public EntertainmentService getEntertainmentService(){
        return createService(EntertainmentService.class,ApiConstants.Category_Base_Url);
    }

    public AppService getAppService(){
        return createService(AppService.class,ApiConstants.App_Base_Url);
    }
    public GameService getGameService(){
        return createService(GameService.class,ApiConstants.Category_Base_Url);
    }


    private  <T> T createService(Class<T> ServiceClass, String recommend_base_url) {
        Retrofit mRetrofit = new Retrofit.Builder().
                baseUrl(recommend_base_url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit.create(ServiceClass);
    }

}
