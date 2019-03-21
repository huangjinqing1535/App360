package com.huang.app360.module.home.welfare;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.huang.app360.App360;
import com.huang.app360.R;
import com.huang.app360.base.RxLazyFragment;
import com.huang.app360.network.ApiConstants;
import com.huang.app360.util.NetStatusUtil;

import butterknife.BindView;

/**
 * Created by huang on 2018/3/8.
 */

public class HomeWelfareFragment extends RxLazyFragment {
    @BindView(R.id.fragment_content)
    FrameLayout mFrameLayout;
    private WebView mWebView;
    private WebSettings mWebSettings;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_welfare;
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
        initWebview();
        isPrepared = false;
    }


    private void initWebview() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(App360.getContext());
        mWebView.setLayoutParams(params);
        mFrameLayout.addView(mWebView);

        mWebSettings = mWebView.getSettings();
        initWebSetting();


        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals(ApiConstants.Welfare_Url)){
                    view.loadUrl(url);
                }else {
                    Intent mIntent = new Intent(getActivity(),WelfareActivity.class);
                    mIntent.putExtra(WelfareActivity.LOAD_URL,url);
                    startActivity(mIntent);
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                switch(errorCode)
                {
                    case WebViewClient.ERROR_FILE_NOT_FOUND:

                        break;
                }
            }
        });
        mWebView.loadUrl(ApiConstants.Welfare_Url);

        initWebSetting();
    }

    private void initWebSetting() {
        if (NetStatusUtil.isNetworkConnected(getContext())) {
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }

//        mWebSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
//        mWebSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
//        mWebSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
//
//        mWebSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口
//        mWebSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
//        mWebSettings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        mWebSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//        mWebSettings.setAllowFileAccess(true); // 允许访问文件
//        mWebSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
//        mWebSettings.setSupportZoom(true); // 支持缩放
        //用WebView显示图片，可使用这个参数 设置网页布局类型：
         //1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         //2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放

        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

    }


    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
