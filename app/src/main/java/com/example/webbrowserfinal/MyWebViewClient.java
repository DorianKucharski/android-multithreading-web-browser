package com.example.webbrowserfinal;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Klasa klienta przeglądarki. odpowiada ona za logikę przeglądarki.
 * Klasa rozszerza klasę WebViewClient, przesłania wszystkie jej metody i wykonując je w wewnętrznych
 * klasach runnable za pomocą ExecutorService klasy klienta.
 */
public class MyWebViewClient extends WebViewClient {

    /**
     * LogTag
     */
    private static final String TAG = "MyWebViewClient";
    /**
     * Obiekt standardowego klienta przeglądarki
     */
    private WebViewClient webViewClient;
    /**
     * ExecutorService wykonująćy klasy runnable.
     */
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    /**
     * Nazwa wątku egzekutora
     */
    private String threadName;

    /**
     * Konstuktor. Przesyła do egzekutora runnable tworzące wenwętrzny obiekt klasy WebViewClient
     */
    MyWebViewClient() {
        Constructor runnable = new Constructor();
        executorService.submit(runnable);
    }

    /**
     * Getter nazwy wątku
     *
     * @return nazwa waku
     */
    String getThreadName() {
        return threadName;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        ShouldOverrideUrlLoadingRunnable runnable = new ShouldOverrideUrlLoadingRunnable(view, request);
        executorService.submit(runnable);
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        OnPageFinishedRunnable runnable = new OnPageFinishedRunnable(view, url);
        executorService.submit(runnable);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        OnLoadResourceRunnable runnable = new OnLoadResourceRunnable(view, url);
        executorService.submit(runnable);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        OnPageCommitVisibleRunnable runnable = new OnPageCommitVisibleRunnable(view, url);
        executorService.submit(runnable);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (SettingsActivity.profiles.get(SettingsActivity.currentProfile).adblock == 1 && isUrlOnBlacklist(request)) {
            Log.d(TAG, "shouldInterceptRequest: Zablokowano request z treścią reklamową: " + request.getUrl());
            return new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream("".getBytes()));
        } else {
            ShouldInterceptRequestRunnable runnable = new ShouldInterceptRequestRunnable(view, request);
            executorService.submit(runnable);
        }
        return null;
    }

    private boolean isUrlOnBlacklist(WebResourceRequest request) {
        if (MainActivity.downloadBlackListThread.isAlive()) {
            return false;
        }
        String url = request.getUrl().toString();
        for (String blacklistUrl : MainActivity.blacklist) {
            if (url.contains(blacklistUrl)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        OnReceivedErrorRunnable runnable = new OnReceivedErrorRunnable(view, request, error);
        executorService.submit(runnable);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        OnReceivedHttpErrorRunnable runnable = new OnReceivedHttpErrorRunnable(view, request, errorResponse);
        executorService.submit(runnable);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        OnFormResubmissionRunnable runnable = new OnFormResubmissionRunnable(view, dontResend, resend);
        executorService.submit(runnable);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        DoUpdateVisitedHistoryRunnable runnable = new DoUpdateVisitedHistoryRunnable(view, url, isReload);
        executorService.submit(runnable);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        OnReceivedSslErrorRunnable runnable = new OnReceivedSslErrorRunnable(view, handler, error);
        executorService.submit(runnable);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        OnReceivedClientCertRequestRunnable runnable = new OnReceivedClientCertRequestRunnable(view, request);
        executorService.submit(runnable);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        OnReceivedHttpAuthRequestRunnable runnable = new OnReceivedHttpAuthRequestRunnable(view, handler, host, realm);
        executorService.submit(runnable);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        ShouldOverrideKeyEventRunnable runnable = new ShouldOverrideKeyEventRunnable(view, event);
        executorService.submit(runnable);
        return true;
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        OnUnhandledKeyEventRunnable runnable = new OnUnhandledKeyEventRunnable(view, event);
        executorService.submit(runnable);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        OnScaleChangedRunnable runnable = new OnScaleChangedRunnable(view, oldScale, newScale);
        executorService.submit(runnable);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
        OnReceivedLoginRequestRunnable runnable = new OnReceivedLoginRequestRunnable(view, realm, account, args);
        executorService.submit(runnable);
    }

    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        OnRenderProcessGoneRunnable runnable = new OnRenderProcessGoneRunnable(view, detail);
        executorService.submit(runnable);
        return true;
    }

    @Override
    public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
        OnSafeBrowsingHitRunnable runnable = new OnSafeBrowsingHitRunnable(view, request, threatType, callback);
        executorService.submit(runnable);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        OnPageStartedRunnable onPageStartedRunnable = new OnPageStartedRunnable(view, url, favicon);
        executorService.submit(onPageStartedRunnable);
    }


    class ShouldOverrideUrlLoadingRunnable implements Runnable {
        private static final String TAG = "ShouldOverrideUrlLoadingRunnable";
        WebView view;
        WebResourceRequest request;

        ShouldOverrideUrlLoadingRunnable(WebView view, WebResourceRequest request) {
            this.view = view;
            this.request = request;
        }

        public void run() {
            webViewClient.shouldOverrideUrlLoading(view, request);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnPageFinishedRunnable implements Runnable {
        private static final String TAG = "OnPageFinishedRunnable";
        WebView view;
        String url;

        OnPageFinishedRunnable(WebView view, String url) {
            this.view = view;
            this.url = url;
        }

        public void run() {
            webViewClient.onPageFinished(view, url);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnLoadResourceRunnable implements Runnable {
        private static final String TAG = "OnLoadResourceRunnable";
        WebView view;
        String url;

        OnLoadResourceRunnable(WebView view, String url) {
            this.view = view;
            this.url = url;
        }

        public void run() {
            webViewClient.onLoadResource(view, url);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnPageCommitVisibleRunnable implements Runnable {
        private static final String TAG = "OnPageCommitVisibleRunnable";
        WebView view;
        String url;

        OnPageCommitVisibleRunnable(WebView view, String url) {
            this.view = view;
            this.url = url;
        }

        public void run() {
            webViewClient.onPageCommitVisible(view, url);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class ShouldInterceptRequestRunnable implements Runnable {
        private static final String TAG = "ShouldInterceptRequestRunnable";
        WebView view;
        WebResourceRequest request;

        ShouldInterceptRequestRunnable(WebView view, WebResourceRequest request) {
            this.view = view;
            this.request = request;
        }

        public void run() {
            webViewClient.shouldInterceptRequest(view, request);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnReceivedErrorRunnable implements Runnable {
        private static final String TAG = "OnReceivedErrorRunnable";
        WebView view;
        WebResourceRequest request;
        WebResourceError error;

        OnReceivedErrorRunnable(WebView view, WebResourceRequest request, WebResourceError error) {
            this.view = view;
            this.request = request;
            this.error = error;
        }

        public void run() {
            webViewClient.onReceivedError(view, request, error);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnReceivedHttpErrorRunnable implements Runnable {
        private static final String TAG = "OnReceivedHttpErrorRunnable";
        WebView view;
        WebResourceRequest request;
        WebResourceResponse error;

        OnReceivedHttpErrorRunnable(WebView view, WebResourceRequest request, WebResourceResponse error) {
            this.view = view;
            this.request = request;
            this.error = error;
        }

        public void run() {
            webViewClient.onReceivedHttpError(view, request, error);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }


    class OnFormResubmissionRunnable implements Runnable {
        private static final String TAG = "OnFormResubmissionRunnable";
        WebView view;
        Message dontResend;
        Message resend;

        OnFormResubmissionRunnable(WebView view, Message dontResend, Message resend) {
            this.view = view;
            this.dontResend = dontResend;
            this.resend = resend;
        }

        public void run() {
            webViewClient.onFormResubmission(view, dontResend, resend);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class DoUpdateVisitedHistoryRunnable implements Runnable {
        private static final String TAG = "DoUpdateVisitedHistoryRunnable";
        WebView view;
        String url;
        boolean isReload;

        DoUpdateVisitedHistoryRunnable(WebView view, String url, boolean isReload) {
            this.view = view;
            this.url = url;
            this.isReload = isReload;
        }

        public void run() {
            webViewClient.doUpdateVisitedHistory(view, url, isReload);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnReceivedSslErrorRunnable implements Runnable {
        private static final String TAG = "OnReceivedSslErrorRunnable";
        WebView view;
        SslErrorHandler handler;
        SslError error;

        OnReceivedSslErrorRunnable(WebView view, SslErrorHandler handler, SslError error) {
            this.view = view;
            this.handler = handler;
            this.error = error;
        }

        public void run() {
            webViewClient.onReceivedSslError(view, handler, error);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnReceivedClientCertRequestRunnable implements Runnable {
        private static final String TAG = "OnReceivedClientCertRequestRunnable";
        WebView view;
        ClientCertRequest request;

        OnReceivedClientCertRequestRunnable(WebView view, ClientCertRequest request) {
            this.view = view;
            this.request = request;
        }

        public void run() {
            webViewClient.onReceivedClientCertRequest(view, request);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnReceivedHttpAuthRequestRunnable implements Runnable {
        private static final String TAG = "OnReceivedHttpAuthRequestRunnable";
        WebView view;
        HttpAuthHandler handler;
        String host;
        String realm;

        OnReceivedHttpAuthRequestRunnable(WebView view, HttpAuthHandler handler, String host, String realm) {
            this.view = view;
            this.handler = handler;
            this.host = host;
            this.realm = realm;
        }

        public void run() {
            webViewClient.onReceivedHttpAuthRequest(view, handler, host, realm);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class ShouldOverrideKeyEventRunnable implements Runnable {
        private static final String TAG = "ShouldOverrideKeyEventRunnable";
        WebView view;
        KeyEvent event;

        ShouldOverrideKeyEventRunnable(WebView view, KeyEvent event) {
            this.view = view;
            this.event = event;
        }

        public void run() {
            webViewClient.shouldOverrideKeyEvent(view, event);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnUnhandledKeyEventRunnable implements Runnable {
        private static final String TAG = "OnUnhandledKeyEventRunnable";
        WebView view;
        KeyEvent event;

        OnUnhandledKeyEventRunnable(WebView view, KeyEvent event) {
            this.view = view;
            this.event = event;
        }

        public void run() {
            webViewClient.onUnhandledKeyEvent(view, event);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnScaleChangedRunnable implements Runnable {
        private static final String TAG = "OnScaleChangedRunnable";
        WebView view;
        float oldScale;
        float newScale;

        OnScaleChangedRunnable(WebView view, float oldScale, float newScale) {
            this.view = view;
            this.oldScale = oldScale;
            this.newScale = newScale;
        }

        public void run() {
            webViewClient.onScaleChanged(view, oldScale, newScale);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnReceivedLoginRequestRunnable implements Runnable {
        private static final String TAG = "OnReceivedLoginRequestRunnable";
        WebView view;
        String realm;
        String account;
        String args;

        OnReceivedLoginRequestRunnable(WebView view, String realm, @Nullable String account, String args) {
            this.view = view;
            this.realm = realm;
            this.account = account;
            this.args = args;
        }

        public void run() {
            webViewClient.onReceivedLoginRequest(view, realm, account, args);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnRenderProcessGoneRunnable implements Runnable {
        private static final String TAG = "OnRenderProcessGoneRunnable";
        WebView view;
        RenderProcessGoneDetail detail;

        OnRenderProcessGoneRunnable(WebView view, RenderProcessGoneDetail detail) {
            this.view = view;
            this.detail = detail;
        }

        public void run() {
            webViewClient.onRenderProcessGone(view, detail);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }

    class OnSafeBrowsingHitRunnable implements Runnable {
        private static final String TAG = "OnSafeBrowsingHitRunnable";
        WebView view;
        WebResourceRequest request;
        int threatType;
        SafeBrowsingResponse callback;

        OnSafeBrowsingHitRunnable(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
            this.view = view;
            this.request = request;
            this.threatType = threatType;
            this.callback = callback;
        }

        public void run() {
//            webViewClient.onSafeBrowsingHit(view, request, threatType, callback);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }


    class OnPageStartedRunnable implements Runnable {
        private static final String TAG = "OnPageStartedRunnable";
        WebView webView;
        String url;
        Bitmap favicon;

        OnPageStartedRunnable(WebView view, String url, Bitmap favicon) {
            this.webView = view;
            this.url = url;
            this.favicon = favicon;
        }

        public void run() {
            webViewClient.onPageStarted(webView, url, favicon);
            Log.d(TAG, "run:  " + Thread.currentThread());
        }
    }


    class Constructor implements Runnable {
        private static final String TAG = "Constructor";

        public void run() {
            webViewClient = new WebViewClient();
            Log.d(TAG, "run:  " + Thread.currentThread());
            threadName = Thread.currentThread().toString();
        }
    }

}
