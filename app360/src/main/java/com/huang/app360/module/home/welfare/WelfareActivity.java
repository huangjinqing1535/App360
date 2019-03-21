package com.huang.app360.module.home.welfare;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huang.app360.R;
import com.huang.app360.base.RxBaseActivity;
import com.huang.app360.network.ApiConstants;

import butterknife.BindView;

/**
 * Created by huang on 2018/3/26.
 */

public class WelfareActivity extends RxBaseActivity {
    @BindView(R.id.web_view)
    WebView webView;
    private WebSettings webSettings;
    private String loadUrl;
    public  static final String LOAD_URL = "LOAD_URL";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_welfare;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        loadUrl = getIntent().getStringExtra(LOAD_URL);
        if (TextUtils.isEmpty(loadUrl)) {
            finish();
            return;
        }
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(loadUrl);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initToolBar() {

    }
}
