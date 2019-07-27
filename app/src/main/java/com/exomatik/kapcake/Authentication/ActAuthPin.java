package com.exomatik.kapcake.Authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.exomatik.kapcake.R;

import androidx.appcompat.app.AppCompatActivity;

public class ActAuthPin extends AppCompatActivity {
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnClear;
    private ImageView img1, img2, img3, img4;
    private String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_auth_pin);

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

    private void setText(String value){
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

            Toast.makeText(this, "Pin : " + pin, Toast.LENGTH_SHORT).show();
        }

    }
}
