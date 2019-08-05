package com.exomatik.kapcake;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.exomatik.kapcake.Authentication.WebViewJavaScriptInterface;
import com.exomatik.kapcake.Featured.UserSave;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView web;
    private UserSave userSave;
    private boolean jalan = true;
    public ProgressDialog progressDialog;
    private boolean back = true;
    private boolean tampil = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        runnabelCekKoneksi();

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

        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String weburl) {
                if (jalan) {
                    web.loadUrl("javascript:loginUser(" + new Gson().toJson(userSave.getKEY_USER()) + ")");
                    jalan = false;
                }
            }
        });
        WebViewJavaScriptInterface webViewJS = new WebViewJavaScriptInterface(this, MainActivity.this, progressDialog);
        web.addJavascriptInterface(webViewJS, "android");
    }

    private void runnabelCekKoneksi() {
        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (back){
                    cekKoneksi();
                }
            }

            public void onFinish() {
            }

        }.start();
    }

    private void cekKoneksi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            tampil = true;
        } else {
            if (tampil){
                Toast.makeText(this, "Mohon periksa koneksi internet anda", Toast.LENGTH_LONG).show();
                tampil = false;
            }
        }
    }

    private void init() {
        web = (WebView) findViewById(R.id.web);
        userSave = new UserSave(this);
    }

    @Override
    public void onBackPressed() {
        back = false;
        finish();
    }
}
