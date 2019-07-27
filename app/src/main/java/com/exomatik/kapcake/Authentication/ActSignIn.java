package com.exomatik.kapcake.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exomatik.kapcake.MainActivity;
import com.exomatik.kapcake.R;

import androidx.appcompat.app.AppCompatActivity;

public class ActSignIn extends AppCompatActivity {
    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_sign_in);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActSignIn.this, ActAuthPin.class));
                finish();
            }
        });
    }
}
