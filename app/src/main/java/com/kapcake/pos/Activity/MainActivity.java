package com.kapcake.pos.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.kapcake.pos.Featured.Bluetooth;
import com.kapcake.pos.Featured.BluetoothSocket;
import com.kapcake.pos.Featured.CustomWebChromeClient;
import com.kapcake.pos.Featured.Print;
import com.kapcake.pos.Featured.WebViewJavaScriptInterface;
import com.kapcake.pos.Featured.UserSave;
import com.kapcake.pos.Model.ModelBluetooth;
import com.kapcake.pos.Model.ModelSocket;
import com.kapcake.pos.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private WebView web;
    private boolean jalan = true;
    private UserSave userSave;
    public ProgressDialog progressDialog;
    private boolean back = true;
    private boolean tampil = true;
    private Bluetooth bluetoothClass;
    private Print printClass;
    private View view;
    private ArrayList<ModelBluetooth> listDevice = new ArrayList<ModelBluetooth>();
    public static ArrayList<ModelSocket> listSocket = new ArrayList<ModelSocket>();
    private BluetoothAdapter bluetoothAdapter;
    public static boolean searching = true;
    public static String macAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideStatusBar();
        setContentView(R.layout.activity_main);

        init();
        progressShow("Mohon tunggu");
        runnabelCekKoneksi();
        setWebView();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void setWebView() {
        web.clearFormData();
        web.clearMatches();
        web.clearSslPreferences();
        web.clearAnimation();
        web.clearFocus();
        web.clearDisappearingChildren();
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

        web.loadUrl("file:///android_asset/index.html");
//        web.loadUrl("http://192.168.43.196/kapcake/kasir/index.html");
//        web.loadUrl("http://192.168.137.84/kapcake/kasir/index.html");
//        web.loadUrl("https://kasir.kapcake.com/");
        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String weburl) {
                if (jalan) {
                    web.loadUrl("javascript:loginUser(" + new Gson().toJson(userSave.getKEY_USER()) + ", "
                            + userSave.getKEY_KODE() + ")");
                    progressDialog.dismiss();
                    jalan = false;
                }
            }
        });
        WebViewJavaScriptInterface webViewJS = new WebViewJavaScriptInterface(this, MainActivity.this
                , progressDialog, bluetoothClass, printClass, view, web, bluetoothAdapter);
        web.addJavascriptInterface(webViewJS, "android");
    }

    private void progressShow(String title) {
        progressDialog = new ProgressDialog(MainActivity.this, R.style.MyProgressDialogTheme);
        progressDialog.setMessage(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (toAuth == 1){
//            Log.e("Tag", "To Auth");
//            Intent intent = new Intent(getApplicationContext(), ActAuthPin.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(intent);
//            toAuth = 0;
//        }
//        else if (toAuth == 2){
//            Log.e("Tag", "To Sign IN");
//            finish();
//            userSave.setKEY_USER(null);
//            Intent intent = new Intent(getApplicationContext(), ActSignIn.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(intent);
//        }
//        else {
//            Log.e("Tag", "JS Running");
//            WebViewJavaScriptInterface webViewJS = new WebViewJavaScriptInterface(this, MainActivity.this
//                    , progressDialog, bluetoothClass, printClass, statusBluetooth, view);
//            web.addJavascriptInterface(webViewJS, "android");
//        }
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

    private void customSnackbar(String text, int background) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        // Get the Snackbar view
        View view = snackbar.getView();

        view.setBackground(ContextCompat.getDrawable(this, background));
        TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

        tv.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.setText(text);
        snackbar.show();
    }

    private void cekKoneksi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                tampil = true;
            } else {
                if (tampil) {
                    customSnackbar("Mohon periksa koneksi internet anda", R.drawable.snakbar_red);
                    tampil = false;
                }
            }
        }
    }

    private void init() {
        web = (WebView) findViewById(R.id.web);
        view = (View) findViewById(android.R.id.content);

        userSave = new UserSave(this);
        bluetoothClass = new Bluetooth(this, web, listDevice);

        overridePendingTransition(0, 0);
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

                    if (searching) {
                        bluetoothClass.btnDiscover();
                    } else {
                        ModelSocket dataSocket = null;
                        for (int a = 0; a < listSocket.size(); a++) {
                            if (listSocket.get(a).getMacAddress().equals(macAddress)) {
                                dataSocket = listSocket.get(a);
                            }
                        }
                        printClass = new Print(bluetoothAdapter.getRemoteDevice(macAddress), MainActivity.this, bluetoothAdapter, progressDialog, web, dataSocket);
                        printClass.hubungkanDevice(macAddress);
                    }
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

        searching = true;
        macAddress = null;
        if (listDevice.size() != 0) {
            listDevice.removeAll(listDevice);
        }
        if (listSocket.size() != 0){
            listSocket.removeAll(listSocket);
        }
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            if (bluetoothClass != null) {
                if (bluetoothClass.receiverDevice.isOrderedBroadcast()) {
                    unregisterReceiver(bluetoothClass.receiverDevice);
                }
            }
            if (bluetoothAdapter != null) {
                if (printClass != null) {
                    for (int a = 0; a < listSocket.size(); a++) {
                        BluetoothSocket socket = new BluetoothSocket(listSocket.get(a), bluetoothAdapter);

                        try {
                            socket.closeBT();
                        } catch (IOException e) {
                            Log.e("Error", e.getMessage().toString());
                        }
                    }
                    printClass = null;
                }
            }
        }
    }

}
