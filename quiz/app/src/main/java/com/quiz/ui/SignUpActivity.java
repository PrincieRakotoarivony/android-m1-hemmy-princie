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

public class SignUpActivity extends AppCompatActivity {

    Animation animTop, animBottom, animToRight, animToLeft;
    LinearLayout headerLayout;
    Button signUpBtn;
    TextInputLayout layoutLastName, layoutFirstName, layoutEmail, layoutPassword, layoutConfirmPassword;
    TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Enter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        animTop = AnimationUtils.loadAnimation(this, R.anim.anim_top);
        animBottom = AnimationUtils.loadAnimation(this, R.anim.anim_bottom);
        animToRight = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right);
        animToLeft = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left);

        headerLayout = findViewById(R.id.header);
        signUpBtn = findViewById(R.id.sign_up_btn);
        layoutLastName = findViewById(R.id.lastname);
        layoutFirstName = findViewById(R.id.firstname);
        layoutEmail = findViewById(R.id.email);
        layoutPassword = findViewById(R.id.password);
        layoutConfirmPassword = findViewById(R.id.confirmPassword);
        loginText = findViewById(R.id.login_text);

        headerLayout.setAnimation(animTop);
        signUpBtn.setAnimation(animBottom);
        layoutLastName.setAnimation(animToLeft);
        layoutFirstName.setAnimation(animToRight);
        layoutEmail.setAnimation(animToLeft);
        layoutPassword.setAnimation(animToRight);
        layoutConfirmPassword.setAnimation(animToLeft);
        loginText.setAnimation(animBottom);

        loginText.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}
