package com.kapcake.pos.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.kapcake.pos.Adapter.SwipeAdapter;
import com.kapcake.pos.Featured.UserSave;
import com.kapcake.pos.Model.ModelUser;
import com.kapcake.pos.R;
import com.kapcake.pos.Retrofit.RetrofitAPI;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.santalu.autoviewpager.AutoViewPager;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActSignIn extends AppCompatActivity {
    private Button btnLogin, btnSignUp;
    private TextView btnForgetPass;
    private ProgressDialog progressDialog;
    private TextInputLayout etInputEmail, etInputPass;
    private View view;
    private UserSave userSave;
    private WormDotsIndicator dotsIndicator;
    private AutoViewPager viewPager;
    private SwipeAdapter swipeAdapter;
    private ImageView imgSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideStatusBar();
        setContentView(R.layout.act_sign_in);
        init();
        setAdapter();

        onClick();
    }

    private void init() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        imgSwipe = (ImageView) findViewById(R.id.imgSwipe);
        etInputEmail = (TextInputLayout) findViewById(R.id.et_input_email);
        etInputPass = (TextInputLayout) findViewById(R.id.et_input_pass);
        view = (View) findViewById(android.R.id.content);
        viewPager = (AutoViewPager) findViewById(R.id.viewPager);
        dotsIndicator = (WormDotsIndicator) findViewById(R.id.dotsIndicator);
        btnForgetPass = findViewById(R.id.btnForgetPass);

        userSave = new UserSave(this);
        overridePendingTransition(0, 0);
        imgSwipe.setImageResource(R.drawable.logo_white);
    }

    private void setAdapter() {
        String isi[] = {"Tingkatkan pendapatan usaha Anda dengan Kapcake POS",
                "Tampilan Kapcake yang mudah digunakan oleh siapapun",
                "Atur menu dengan mudah mulai dari harga, foto, kategori, variasi dan jumlah sesuai kebutuhan",
                "Memudahkan pengguna dalam mengatur stok barang dan inventory secara akurat"
        };
        String title[] = {"Kapcake Point of Sale",
                "Sangat Mudah!",
                "Pengaturan Menu",
                "Manajemen Stok"
        };

        swipeAdapter = new SwipeAdapter(this, title, isi);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(swipeAdapter);
        dotsIndicator.setViewPager(viewPager);
    }

    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void onClick() {
        etInputEmail.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                etInputEmail.getEditText().setFocusable(false);
                etInputPass.getEditText().setFocusable(true);
                return false;
            }
        });

        etInputPass.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                etInputEmail.getEditText().setFocusable(false);
                etInputPass.getEditText().setFocusable(false);
                etInputEmail.getEditText().setFocusableInTouchMode(true);
                etInputPass.getEditText().setFocusableInTouchMode(true);
                return false;
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answers.getInstance().logCustom(new CustomEvent("Sign Up Clicked"));
                Crashlytics.setUserIdentifier("Sign Up");
                String url = "https://backoffice.kapcake.com/daftar";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://backoffice.kapcake.com/lupa-password";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etInputEmail.getEditText().getText().toString();
                String pass = etInputPass.getEditText().getText().toString();

                if (email.isEmpty() || pass.isEmpty() || pass.length() < 6){
                    if (email.isEmpty()){
                        customSnackbar("Email " + getResources().getString(R.string.error_data_kosong), R.drawable.snakbar_red);
                    }
                    else if (pass.isEmpty()){
                        customSnackbar("Password " + getResources().getString(R.string.error_data_kosong), R.drawable.snakbar_red);
                    }
                    else if (pass.length() < 6){
                        customSnackbar("Password " + getResources().getString(R.string.error_password_kurang), R.drawable.snakbar_red);
                    }
                }
                else {
                    progressDialog = new ProgressDialog(ActSignIn.this, R.style.MyProgressDialogTheme);
                    progressDialog.setMessage(getResources().getString(R.string.progress_title1));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    postLoginUser(email, pass);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

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

    private void postLoginUser(String email, String pass){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI api = retrofit.create(RetrofitAPI.class);

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e("Mac", androidId);
        Call<ModelUser> call = api.signIn("kasir/login?email=" + email +
                "&operating_system=android" +"&password=" + pass + "&mac=" + androidId);

        call.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                ModelUser dataUser = response.body();

                if (dataUser == null){
                    customSnackbar(getResources().getString(R.string.error_email_not_found), R.drawable.snakbar_red);
                }
                else {
                    if (dataUser.getUser().getPin() != null){
                        Log.e("Data user", dataUser.getUser().getEmail());
                        userSave.setKEY_USER(dataUser);
                        Intent intent = new Intent(getApplicationContext(), ActAuthPin.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        customSnackbar("Mohon maaf, data user anda tidak valid", R.drawable.snakbar_red);
                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                progressDialog.dismiss();
                if (t.getMessage().toString().contains("Unable to resolve host")){
                    customSnackbar("Mohon periksa koneksi Internet Anda", R.drawable.snakbar_red);
                }
                else {
                    customSnackbar(t.getMessage().toString(), R.drawable.snakbar_red);
                }
            }
        });
    }
}
