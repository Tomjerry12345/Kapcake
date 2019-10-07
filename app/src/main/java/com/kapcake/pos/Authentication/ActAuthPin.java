package com.kapcake.pos.Authentication;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kapcake.pos.Featured.UserSave;
import com.kapcake.pos.Activity.MainActivity;
import com.kapcake.pos.Model.ModelLogout;
import com.kapcake.pos.R;
import com.kapcake.pos.Retrofit.RetrofitAPI;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActAuthPin extends AppCompatActivity {
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnClear;
    private ProgressDialog progressDialog;
    private ImageView img1, img2, img3, img4;
    private String pin;
    private UserSave userSave;
    private ImageButton btnLogout;
    private int coba = 0;
    private boolean jalan = true;
    private int timeCountDown = 30;
    private View view;
    private TextView textUser;
    private CountDownTimer time;
    private boolean timeRun = false;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideStatusBar();
        setContentView(R.layout.act_auth_pin);

        init();

        onClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timeRun){
            time.cancel();
        }
    }

    private void init() {
        textUser = (TextView) findViewById(R.id.text_hint);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
        btn7 = (Button) findViewById(R.id.btn_7);
        btn8 = (Button) findViewById(R.id.btn_8);
        btn9 = (Button) findViewById(R.id.btn_9);
        btn0 = (Button) findViewById(R.id.btn_0);
        btnClear = (Button) findViewById(R.id.btn_clear);
        img1 = (ImageView) findViewById(R.id.img_1);
        img2 = (ImageView) findViewById(R.id.img_2);
        img3 = (ImageView) findViewById(R.id.img_3);
        img4 = (ImageView) findViewById(R.id.img_4);
        btnLogout = (ImageButton) findViewById(R.id.btn_logout);

        view = (View) findViewById(android.R.id.content);

        userSave = new UserSave(this);

        if (userSave.getKEY_USER() != null){
            textUser.setText(userSave.getKEY_USER().getUser().getNama());
        }

        img1.setImageResource(R.drawable.border_hitam_putih);
        img2.setImageResource(R.drawable.border_hitam_putih);
        img3.setImageResource(R.drawable.border_hitam_putih);
        img4.setImageResource(R.drawable.border_hitam_putih);
    }

    public void minimizeApp() {
        moveTaskToBack(true);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            minimizeApp();
            return;
        } else {
            Toast toast = Toast.makeText(ActAuthPin.this, "Tekan Cepat 2 Kali untuk Minimize", Toast.LENGTH_SHORT);
            toast.show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }

    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void onClick() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = null;
                img1.setImageResource(R.drawable.border_hitam_putih);
                img2.setImageResource(R.drawable.border_hitam_putih);
                img3.setImageResource(R.drawable.border_hitam_putih);
                img4.setImageResource(R.drawable.border_hitam_putih);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alertLogout();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("9");
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("0");
            }
        });
    }

    private void alertLogout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActAuthPin.this, R.style.MyProgressDialogTheme);

        alert.setTitle("Keluar");
        alert.setMessage("Apakah anda yakin ingin keluar dari akun?");
        alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                logoutUser();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void logoutUser() {
        progressShow("Mohon Tunggu");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.URL_LOGOUT)
                .addConverterFactory(GsonConverterFactory.create(gson)
                );
        RetrofitAPI api = retrofit.build().create(RetrofitAPI.class);


        Call<ModelLogout> call = api.signOut("perangkat/logout?perangkat_id=" +
                        userSave.getKEY_USER().getPerangkat().getIdPerangkat().toString(),
                "Bearer " + userSave.getKEY_USER().getToken().toString(),
                "application/json");

        Log.e("ID", userSave.getKEY_USER().getPerangkat().getIdPerangkat().toString());
        Log.e("Token", userSave.getKEY_USER().getToken().toString());
        call.enqueue(new Callback<ModelLogout>() {
            @Override
            public void onResponse(Call<ModelLogout> call, Response<ModelLogout> response) {
                customSnackbar(getResources().getString(R.string.text_berhasil_logout), R.drawable.snakbar_blue);
                Log.e("Data", response.body().getResponse().toString());

            }

            @Override
            public void onFailure(Call<ModelLogout> call, Throwable t) {
                progressDialog.dismiss();
                customSnackbar(t.getMessage().toString(), R.drawable.snakbar_red);
            }
        });

        userSave.setKEY_KODE("0");
        userSave.setKEY_TANGGAL("0");
        userSave.setKEY_USER(null);
        progressDialog.dismiss();
        startActivity(new Intent(ActAuthPin.this, ActSignIn.class));
        finish();
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

    private void setText(String value){
        if (coba < 3){
            if (pin == null){
                pin = value;
                img1.setImageResource(R.drawable.border_hitam);
                img2.setImageResource(R.drawable.border_hitam_putih);
                img3.setImageResource(R.drawable.border_hitam_putih);
                img4.setImageResource(R.drawable.border_hitam_putih);
            }
            else if (pin.length() == 1){
                pin = pin + value;
                img1.setImageResource(R.drawable.border_hitam);
                img2.setImageResource(R.drawable.border_hitam);
                img3.setImageResource(R.drawable.border_hitam_putih);
                img4.setImageResource(R.drawable.border_hitam_putih);
            }
            else if (pin.length() == 2){
                pin = pin + value;
                img1.setImageResource(R.drawable.border_hitam);
                img2.setImageResource(R.drawable.border_hitam);
                img3.setImageResource(R.drawable.border_hitam);
                img4.setImageResource(R.drawable.border_hitam_putih);
            }
            else if (pin.length() == 3){
                pin = pin + value;
                img1.setImageResource(R.drawable.border_hitam);
                img2.setImageResource(R.drawable.border_hitam);
                img3.setImageResource(R.drawable.border_hitam);
                img4.setImageResource(R.drawable.border_hitam);

                Log.e("Pin", Integer.toString(userSave.getKEY_USER().getUser().getPin()));
                if (pin.equals(Integer.toString(userSave.getKEY_USER().getUser().getPin()))){
                    cekTanggal();
                }else {
                    coba++;
                    img1.setImageResource(R.drawable.border_hitam_putih);
                    img2.setImageResource(R.drawable.border_hitam_putih);
                    img3.setImageResource(R.drawable.border_hitam_putih);
                    img4.setImageResource(R.drawable.border_hitam_putih);
                    pin = null;

                    customSnackbar(getResources().getString(R.string.error_pin_salah), R.drawable.snakbar_red);
                    if (coba == 3){
                        timer();
                        timeRun = true;
                    }
                }
            }
        }
        else {
            customSnackbar(getResources().getString(R.string.time_count_down)
                    + " " + Integer.toString(timeCountDown)
                    + " " + getResources().getString(R.string.time_count_down2), R.drawable.snakbar_red);
        }

    }

    private void cekTanggal() {
        if (userSave.getKEY_KODE() == null){
            startActivity(new Intent(ActAuthPin.this, MainActivity.class));
            finish();
        }
        else if (userSave.getKEY_KODE().equals("0")){
            startActivity(new Intent(ActAuthPin.this, MainActivity.class));
            finish();
        }
        else {
            compareDate();
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void compareDate() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        String formattedDate = df.format(c);

        try {
            Date batas = df.parse(userSave.getKEY_TANGGAL());
            Date current = df.parse(formattedDate);

            if (current.getTime() > batas.getTime()){
                userSave.setKEY_TANGGAL("0");
                userSave.setKEY_KODE("0");
            }

            startActivity(new Intent(ActAuthPin.this, MainActivity.class));
            finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void progressShow(String message) {
        progressDialog = new ProgressDialog(ActAuthPin.this, R.style.MyProgressDialogTheme);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);

        progressDialog.show();
    }

    private void setWebView() {
//        progressShow("Mohon tunggu");
//        MainActivity.web.clearCache(true);
//        MainActivity.web.clearHistory();
//
//        MainActivity.web.setWebChromeClient(new CustomWebChromeClient(this));
//        MainActivity.web.setWebViewClient(new WebViewClient());
//        MainActivity.web.getSettings().setLoadsImagesAutomatically(true);
//        MainActivity.web.getSettings().setJavaScriptEnabled(true);
//        MainActivity.web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        MainActivity.web.getSettings().setDomStorageEnabled(true);
//
//        MainActivity.web.getSettings().setSupportZoom(false);
//        MainActivity.web.getSettings().setBuiltInZoomControls(false);
//        MainActivity.web.getSettings().setDisplayZoomControls(false);
//
//        MainActivity.web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

//        MainActivity.web.loadUrl("file:///android_asset/index.html");
//
//        MainActivity.web.loadUrl("https://kasir.kapcake.com/");
//        MainActivity.web.setWebViewClient(new WebViewClient() {
//            public void onPageFinished(WebView view, String weburl) {
//                pin = null;
//                MainActivity.toAuth = 0;
//
//                if (MainActivity.jalan) {
//                    MainActivity.web.loadUrl("javascript:loginUser(" + new Gson().toJson(userSave.getKEY_USER()) + ")");
//                    MainActivity.jalan = false;
//                }
//
//                new Handler().postDelayed(new Runnable()
//                {
//                    public void run()
//                    {
//                        progressDialog.dismiss();
//                        finish();
//                    }
//                }, 2000L);
//            }
//        });
    }

    private void timer(){
        time = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeCountDown--;
                Log.e("Timer",Integer.toString(timeCountDown));

                if (timeCountDown == 0){
                    coba = 0;
                    timeCountDown = 30;
                    timeRun = false;
                    cancel();
                }
            }

            public void onFinish() {
                Log.e("Timer",Integer.toString(timeCountDown));
            }

        }.start();
    }
}
