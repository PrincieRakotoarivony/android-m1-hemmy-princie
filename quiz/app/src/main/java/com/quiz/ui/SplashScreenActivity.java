package com.quiz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.quiz.MainActivity;
import com.quiz.R;

public class SplashScreenActivity extends AppCompatActivity {

    Animation start_animation, logo_animation;
    ImageView logo_imageView;
    Button start_btn;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Enter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        start_animation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom);
        logo_animation = AnimationUtils.loadAnimation(this, R.anim.anim_top);

        logo_imageView = findViewById(R.id.sp_logo);
        start_btn = findViewById(R.id.start_btn);
//        lottieAnimationView = findViewById(R.id.Logloading);

        logo_imageView.setAnimation(logo_animation);
        start_btn.setAnimation(start_animation);
//        lottieAnimationView.setVisibility(View.VISIBLE);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
