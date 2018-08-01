package com.nenggou.slsm.webview.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.widget.web.NoScrollWebView;
import com.nenggou.slsm.webview.unit.BridgeImpl;
import com.nenggou.slsm.webview.unit.JSBridge;
import com.nenggou.slsm.webview.unit.JSBridgeWebChromeClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/8/1.
 */

public class WebViewFragment extends BaseFragment {


    @BindView(R.id.webView)
    NoScrollWebView webView;
    private BridgeImpl bridge;
    private String url;

    public void addUrl(String url) {
        this.url = url;
        if (!isFirstLoad && getUserVisibleHint() && webView != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_webview, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    private void initView() {
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new JSBridgeWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });
        bridge = new BridgeImpl(getActivity());
        JSBridge.register("bridge", BridgeImpl.class);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case StaticData.CALLBACK_DATA:

                    break;
                default:
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                if (webView != null && !TextUtils.isEmpty(url)) {
                    webView.loadUrl(url);
                }
                isFirstLoad = false;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            return super.onBackPressed();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
