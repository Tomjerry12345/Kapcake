package com.exomatik.kapcake;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bumptech.glide.load.model.ModelLoader;
import com.exomatik.kapcake.Authentication.ActSignIn;
import com.exomatik.kapcake.Authentication.WebViewJavaScriptInterface;
import com.exomatik.kapcake.Featured.UserSave;
import com.exomatik.kapcake.Model.ModelUser;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView web;
    private UserSave userSave;
    private boolean jalan = true;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);

        // Tiga baris di bawah ini agar laman yang dimuat dapat
        // melakukan zoom.
        web.getSettings().setSupportZoom(false);
        web.getSettings().setBuiltInZoomControls(false);
        web.getSettings().setDisplayZoomControls(false);

        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.setWebViewClient(new WebViewClient());
        //webView.loadUrl("file:///android_asset/index.html");
        //web.loadUrl("http://192.168.43.196/kapcake/pos1/m.php");

        web.loadUrl("https://kasir.kapcake.com/");

        web.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String weburl){
                if (jalan){
                    web.loadUrl("javascript:loginUser(" + new Gson().toJson(userSave.getKEY_USER()) + ")");
                    jalan = false;
                }
            }
        });
        WebViewJavaScriptInterface webViewJS = new WebViewJavaScriptInterface(this, MainActivity.this, progressDialog);
        web.addJavascriptInterface(webViewJS, "android");
    }

    private void init() {
        web = (WebView) findViewById(R.id.web);
        userSave = new UserSave(this);

    }

}
