package com.exomatik.kapcake.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.kapcake.Featured.UserSave;
import com.exomatik.kapcake.MainActivity;
import com.exomatik.kapcake.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

public class ActAuthPin extends AppCompatActivity {
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnClear;
    private ImageView img1, img2, img3, img4;
    private String pin;
    private UserSave userSave;
    private ImageButton btnLogout;
    private int coba = 0;
    private boolean jalan = true;
    private int timeCountDown = 30;
    private View v;
    private TextView textUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_auth_pin);

        init();

        img1.setImageResource(R.drawable.border_hitam_putih);
        img2.setImageResource(R.drawable.border_hitam_putih);
        img3.setImageResource(R.drawable.border_hitam_putih);
        img4.setImageResource(R.drawable.border_hitam_putih);

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
                userSave.setKEY_USER(null);
                startActivity(new Intent(ActAuthPin.this, ActSignIn.class));
                finish();
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

        v = (View) findViewById(android.R.id.content);

        userSave = new UserSave(this);

        textUser.setText(userSave.getKEY_USER().getUser().getNama());
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

                if (pin.equals(Integer.toString(userSave.getKEY_USER().getUser().getPin()))){
                    pin = null;
                    startActivity(new Intent(ActAuthPin.this, MainActivity.class));
                    finish();
                }else {
                    coba++;
                    img1.setImageResource(R.drawable.border_hitam_putih);
                    img2.setImageResource(R.drawable.border_hitam_putih);
                    img3.setImageResource(R.drawable.border_hitam_putih);
                    img4.setImageResource(R.drawable.border_hitam_putih);
                    pin = null;

//                    Snackbar snackbar = Snackbar
//                            .make(v, , Snackbar.LENGTH_LONG);
//
//                    snackbar.show();

                    Toast.makeText(this, getResources().getString(R.string.error_pin_salah), Toast.LENGTH_SHORT).show();

                    if (coba == 3){
                        timer();
                    }
                }
            }
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.time_count_down)
                    + " " + Integer.toString(timeCountDown)
                    + " " + getResources().getString(R.string.time_count_down2), Toast.LENGTH_SHORT).show();
        }

    }

    private void timer(){
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeCountDown--;
                Log.e("Timer",Integer.toString(timeCountDown));

                if (timeCountDown == 0){
                    coba = 0;
                    timeCountDown = 30;
                    cancel();
                }
            }

            public void onFinish() {
                Log.e("Timer",Integer.toString(timeCountDown));
            }

        }.start();
    }

    public void tambah(View view) {

    }
}
