package com.exomatik.kapcake.Authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.exomatik.kapcake.Featured.UserSave;
import com.exomatik.kapcake.MainActivity;
import com.exomatik.kapcake.R;

/**
 * Created by IrfanRZ on 03/08/2019.
 */

public class WebViewJavaScriptInterface {
    private Context context;
    private Activity activity;
    private UserSave userSave;
    private ProgressDialog progressDialog;

    /*
     * Need a reference to the context in order to sent a post message
     */
    public WebViewJavaScriptInterface(Context context, Activity activity, ProgressDialog progressDialog){
        this.context = context;
        this.activity = activity;
        this.progressDialog = progressDialog;

        userSave = new UserSave(context);
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    public void logout(){
        userSave.setKEY_USER(null);
        activity.startActivity(new Intent(context, ActSignIn.class));
        activity.finish();
        Toast.makeText(context, context.getResources().getString(R.string.text_berhasil_logout), Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void progressShow(){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(context.getResources().getString(R.string.progress_title1));
        progressDialog.setTitle(context.getResources().getString(R.string.progress_text1));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @JavascriptInterface
    public void progressDismiss(){
        progressDialog.dismiss();
    }
}
