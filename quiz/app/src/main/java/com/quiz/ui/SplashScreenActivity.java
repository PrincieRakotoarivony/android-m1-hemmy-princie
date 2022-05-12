package com.quiz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.quiz.R;

public class SplashScreenActivity extends AppCompatActivity {

    Animation logo_animation;
    ImageView logo_imageView;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Enter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo_animation = AnimationUtils.loadAnimation(this, R.anim.anim_top);
        logo_imageView = findViewById(R.id.sp_logo);
//        lottieAnimationView = findViewById(R.id.Logloading);

        logo_imageView.setAnimation(logo_animation);
//        lottieAnimationView.setVisibility(View.VISIBLE);

    }
}
