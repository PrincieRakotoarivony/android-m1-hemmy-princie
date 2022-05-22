package com.quiz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.quiz.KidzyApplication;
import com.quiz.R;

public class SplashScreenActivity extends AppCompatActivity {

    Animation startAnimation, logoAnimation;
    ImageView logoImageView;
    //Button startBtn;
    ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Enter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        startAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom);
        logoAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_top);

        logoImageView = findViewById(R.id.sp_logo);
        loadingBar = findViewById(R.id.loadingBar);
        //startBtn = findViewById(R.id.start_btn);

        logoImageView.setAnimation(logoAnimation);
        /*startBtn.setAnimation(startAnimation);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });*/

        new Handler(Looper.getMainLooper())
            .postDelayed(new Runnable() {
                @Override
                public void run() {
                    Class redirect = LoginActivity.class;
                    if(KidzyApplication.has("token"))  redirect = MainActivity.class;
                    startActivity(new Intent(SplashScreenActivity.this, redirect));
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            }, 5000);
        loadingBar.setVisibility(View.VISIBLE);
    }
}
