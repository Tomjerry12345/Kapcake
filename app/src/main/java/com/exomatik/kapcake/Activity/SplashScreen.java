package com.exomatik.kapcake.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.exomatik.kapcake.Authentication.ActAuthPin;
import com.exomatik.kapcake.Authentication.ActSignIn;
import com.exomatik.kapcake.Featured.UserSave;
import com.exomatik.kapcake.R;

import androidx.appcompat.app.AppCompatActivity;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {
    private UserSave userSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        hideStatusBar();
        setContentView(R.layout.act_splash_screen);

        init();

        //handler untuk menunggu selama 2 detik
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                Intent localIntent = null;
                if (userSave.getKEY_USER() == null){
                     localIntent = new Intent(SplashScreen.this, ActSignIn.class);
                }else {
                    localIntent = new Intent(SplashScreen.this, ActAuthPin.class);
                }
                //berpindah activity
                startActivity(localIntent);
                //menghentikan activity sekarang
                finish();
            }
        }, 2000L);
    }

    private void init() {
        userSave = new UserSave(this);
    }

    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
