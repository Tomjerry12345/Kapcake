package com.exomatik.kapcake.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.exomatik.kapcake.Featured.Bluetooth;
import com.exomatik.kapcake.Featured.CustomWebChromeClient;
import com.exomatik.kapcake.Featured.Print;
import com.exomatik.kapcake.Featured.WebViewJavaScriptInterface;
import com.exomatik.kapcake.Featured.UserSave;
import com.exomatik.kapcake.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView web;
    private UserSave userSave;
    private boolean jalan = true;
    public ProgressDialog progressDialog;
    private boolean back = true;
    private boolean tampil = true;
    private Bluetooth bluetoothClass;
    private Print printClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideStatusBar();
        setContentView(R.layout.activity_main);

        init();
        runnabelCekKoneksi();

        web.clearCache(true);
        web.clearHistory();

        web.setWebChromeClient(new CustomWebChromeClient(this));
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.getSettings().setDomStorageEnabled(true);

        web.getSettings().setSupportZoom(false);
        web.getSettings().setBuiltInZoomControls(false);
        web.getSettings().setDisplayZoomControls(false);

        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        web.loadUrl("https://kasir.kapcake.com");

        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String weburl) {
                if (jalan) {
                    web.loadUrl("javascript:loginUser(" + new Gson().toJson(userSave.getKEY_USER()) + ")");
                    jalan = false;
                }
            }
        });
        WebViewJavaScriptInterface webViewJS = new WebViewJavaScriptInterface(this, MainActivity.this
                , progressDialog, bluetoothClass, printClass);
        web.addJavascriptInterface(webViewJS, "android");
    }

    private void runnabelCekKoneksi() {
        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (back) {
                    cekKoneksi();
                }
            }

            public void onFinish() {
            }

        }.start();
    }

    private void cekKoneksi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                tampil = true;
            } else {
                if (tampil) {
                    Toast.makeText(this, "Mohon periksa koneksi internet anda", Toast.LENGTH_LONG).show();
                    tampil = false;
                }
            }
        }
    }

    private void init() {
        web = (WebView) findViewById(R.id.web);
        userSave = new UserSave(this);
        bluetoothClass = new Bluetooth(this, web);
    }

    @Override
    public void onBackPressed() {
        back = false;
        finish();
    }

    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth aktif", Toast.LENGTH_SHORT).show();

                    bluetoothClass.btnDiscover(1);
                } else {
                    Toast.makeText(this, "Bluetooth tidak aktif", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(bluetoothClass.receiverDevice);
        try {
            printClass.closeBT();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
