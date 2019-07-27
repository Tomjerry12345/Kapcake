package com.exomatik.kapcake;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.exomatik.kapcake.Authentication.ActAuthPin;
import com.exomatik.kapcake.Authentication.ActSignIn;
import com.wang.avi.AVLoadingIndicatorView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private AVLoadingIndicatorView loadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash_screen);

        loadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);

        //handler untuk menunggu selama 2 detik
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                Intent localIntent = new Intent(SplashScreen.this, ActSignIn.class);
                //berpindah activity
                startActivity(localIntent);
                //menghentikan activity sekarang
                finish();
            }
        }, 2000L);
    }
}
