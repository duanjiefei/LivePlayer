package com.duanjiefei.github.liveplayer.fragment;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.duanjiefei.github.liveplayer.R;

public class BlogFragment extends BaseFragment {
    private WebView webView;
    private ProgressBar progressBar;
    private static final String URL = "https://github.com/duanjiefei";
    private static final int MAX = 100;
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initView() {
        progressBar = bindViewID(R.id.progressbar);
        webView = bindViewID(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setSupportZoom(true);  //支持缩放
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setBuiltInZoomControls(true); //设置支持缩放
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webView.loadUrl(URL);
        webView.setWebChromeClient(webChromeClient);
    }

    @Override
    protected void initData() {

    }

    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
            if (newProgress == MAX){
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    };
}
