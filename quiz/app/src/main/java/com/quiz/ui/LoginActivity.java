package com.quiz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.quiz.R;

public class LoginActivity extends AppCompatActivity {

    Animation animTop, animBottom, animToRight, animToLeft;
    LinearLayout headerLayout;
    Button loginBtn;
    TextInputLayout layoutEmail, layoutPassword;
    TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Enter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        animTop = AnimationUtils.loadAnimation(this, R.anim.anim_top);
        animBottom = AnimationUtils.loadAnimation(this, R.anim.anim_bottom);
        animToRight = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right);
        animToLeft = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left);

        headerLayout = findViewById(R.id.header);
        loginBtn = findViewById(R.id.login_btn);
        layoutEmail = findViewById(R.id.email);
        layoutPassword = findViewById(R.id.password);
        signUpText = findViewById(R.id.sign_up_text);

        headerLayout.setAnimation(animTop);
        loginBtn.setAnimation(animBottom);
        layoutEmail.setAnimation(animToLeft);
        layoutPassword.setAnimation(animToRight);
        signUpText.setAnimation(animBottom);

        signUpText.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        loginBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
}
