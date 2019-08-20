package com.exomatik.kapcake.Featured;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.exomatik.kapcake.Activity.GetKoneksi;
import com.exomatik.kapcake.Authentication.ActSignIn;
import com.exomatik.kapcake.Model.ModelPesanan;
import com.exomatik.kapcake.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

/**
 * Created by IrfanRZ on 03/08/2019.
 */

public class WebViewJavaScriptInterface {
    private Context context;
    private Activity activity;
    private UserSave userSave;
    private ProgressDialog progressDialog;
    private Bluetooth bluetoothClass;
    private BluetoothAdapter bluetoothAdapter;
    private Print printClass;
    /*
     * Need a reference to the context in order to sent a post message
     */

    public WebViewJavaScriptInterface(Context context, Activity activity, ProgressDialog progressDialog
            , Bluetooth bluetoothClass, Print printClass) {
        this.context = context;
        this.activity = activity;
        this.progressDialog = progressDialog;
        this.bluetoothClass = bluetoothClass;
        this.printClass = printClass;

        userSave = new UserSave(context);
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    public void logout() {
        userSave.setKEY_USER(null);
        activity.startActivity(new Intent(context, ActSignIn.class));
        activity.finish();
        Toast.makeText(context, context.getResources().getString(R.string.text_berhasil_logout), Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void progressShow(String title, String message) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(title);
        progressDialog.setTitle(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @JavascriptInterface
    public void progressDismiss() {
        progressDialog.dismiss();
    }

    @JavascriptInterface
    public void ToastMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void printPesanan(String message) {
        ModelPesanan obj = new Gson().fromJson(message, ModelPesanan.class);

        printClass.printData(obj);
    }

    @JavascriptInterface
    public void cekAndRequestBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth tidak didukung", Toast.LENGTH_SHORT).show();
        }

        if (bluetoothAdapter.isEnabled()) {
            Toast.makeText(context, "Bluetooh Aktif", Toast.LENGTH_SHORT).show();

            bluetoothClass.btnDiscover(2);
        }
        else {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, 0);
            Toast.makeText(context, "Menyalakan", Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public void connectingToBluetooth(String macAddress) {
        printClass = new Print(bluetoothAdapter.getRemoteDevice(macAddress), activity, bluetoothAdapter);

        printClass.hubungkanDevice();
    }

    @JavascriptInterface
    public void tesPrint(String namaOutlet, String hp, String alamat, String tipe, String macAddress) {
        Log.e("Tes", "Succes print");

        if (namaOutlet.isEmpty()){
            namaOutlet = "Kapcake";
        }
        if (hp.isEmpty()){
            hp = "-";
        }
        if (alamat.isEmpty()){
            alamat = "Villa Samata";
        }
        if (tipe.isEmpty()){
            tipe = "Bluetooth";
        }
        if (macAddress.isEmpty()){
            macAddress = "-";
        }
        printClass.tesPrint(namaOutlet, hp, alamat, tipe, macAddress);
    }

    @JavascriptInterface
    public void stopConnected() {
        activity.unregisterReceiver(bluetoothClass.receiverDevice);
        try {
            printClass.closeBT();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
