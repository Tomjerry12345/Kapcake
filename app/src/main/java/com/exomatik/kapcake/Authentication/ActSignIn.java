package com.exomatik.kapcake.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.kapcake.Featured.UserSave;
import com.exomatik.kapcake.MainActivity;
import com.exomatik.kapcake.Model.ModelUser;
import com.exomatik.kapcake.R;
import com.exomatik.kapcake.Retrofit.PostCekLogin;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActSignIn extends AppCompatActivity {
    private Button btnLogin, btnSignUp;
    private ProgressDialog progressDialog;
    private TextInputLayout etInputEmail, etInputPass;
    private View v;
    private UserSave userSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_sign_in);

        init();

        etInputEmail.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                etInputEmail.setError(null);
                etInputEmail.getEditText().setFocusable(false);
                etInputPass.getEditText().setFocusable(true);
                return false;
            }
        });

        etInputPass.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                etInputEmail.setError(null);
                etInputPass.setError(null);
                etInputEmail.getEditText().setFocusable(false);
                etInputPass.getEditText().setFocusable(false);
                etInputEmail.getEditText().setFocusableInTouchMode(true);
                etInputPass.getEditText().setFocusableInTouchMode(true);
                return false;
            }
        });

        etInputEmail.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etInputEmail.setError(null);
            }
        });

        etInputPass.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etInputPass.setError(null);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://backoffice.kapcake.com/register";
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
                        etInputEmail.setError(getResources().getString(R.string.error_data_kosong));
                        etInputEmail.getEditText().setBackground(getResources().getDrawable(R.drawable.border_et_putih_hitam));
                    }
                    if (pass.isEmpty()){
                        etInputPass.setError(getResources().getString(R.string.error_data_kosong));
                        etInputPass.getEditText().setBackground(getResources().getDrawable(R.drawable.border_et_putih_hitam));
                    }
                    if (pass.length() < 6){
                        etInputPass.setError(getResources().getString(R.string.error_password_kurang));
                        etInputPass.getEditText().setBackground(getResources().getDrawable(R.drawable.border_et_putih_hitam));
                    }
                }
                else {
                    progressDialog = new ProgressDialog(ActSignIn.this);
                    progressDialog.setMessage(getResources().getString(R.string.progress_title1));
                    progressDialog.setTitle(getResources().getString(R.string.progress_text1));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    postLoginUser(email, pass);
                }
            }
        });
    }

    private void init() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        etInputEmail = (TextInputLayout) findViewById(R.id.et_input_email);
        etInputPass = (TextInputLayout) findViewById(R.id.et_input_pass);
        v = (View) findViewById(android.R.id.content);

        userSave = new UserSave(this);
    }

    private void postLoginUser(String email, String pass){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostCekLogin.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostCekLogin apiSearchMovie = retrofit.create(PostCekLogin.class);

        Call<ModelUser> call = apiSearchMovie.searchMovie("kasir/login?email=" + email +"&password=" + pass);

        call.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                ModelUser films = response.body();

                if (films == null){
                    Snackbar snackbar = Snackbar
                            .make(v, getResources().getString(R.string.error_email_not_found), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else {
                    userSave.setKEY_USER(films);
                    startActivity(new Intent(ActSignIn.this, ActAuthPin.class));
                    finish();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Snackbar snackbar = Snackbar
                        .make(v, t.getMessage().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}
