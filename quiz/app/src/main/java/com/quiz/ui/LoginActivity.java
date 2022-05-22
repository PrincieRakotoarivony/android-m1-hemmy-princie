package com.quiz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Utilisateur;
import com.quiz.services.AuthService;
import com.quiz.util.MyMap;
import com.quiz.util.Util;

public class LoginActivity extends AppCompatActivity {

    AuthService authService;

    Animation animTop, animBottom, animToRight, animToLeft;
    LinearLayout headerLayout;
    LinearLayout loginBtn;
    TextInputLayout layoutEmail, layoutPassword;
    TextView signUpText;
    ProgressBar loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Enter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authService = AuthService.getInstance();

        animTop = AnimationUtils.loadAnimation(this, R.anim.anim_top);
        animBottom = AnimationUtils.loadAnimation(this, R.anim.anim_bottom);
        animToRight = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right);
        animToLeft = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left);

        headerLayout = findViewById(R.id.header);
        loginBtn = findViewById(R.id.login_btn);
        layoutEmail = findViewById(R.id.email);
        layoutPassword = findViewById(R.id.password);
        signUpText = findViewById(R.id.sign_up_text);
        loginProgress = findViewById(R.id.login_progress);

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

        loginBtn.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginProgress.getVisibility() == View.VISIBLE) return;
                String email = layoutEmail.getEditText().getText().toString();
                String password = layoutPassword.getEditText().getText().toString();
                new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected void onPostExecute(Object o) {
                        if(o instanceof Exception){
                            Exception ex = (Exception)o;
                            ex.printStackTrace();
                            Util.showErrorMessage(ex.getMessage(), v);
                        } else {
                            MyMap result = (MyMap) o;
                            KidzyApplication.set("token", (String)result.get("token"));
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        loginProgress.setVisibility(View.GONE);
                    }

                    @Override
                    protected Object doInBackground(Object... objects) {
                        try{
                            Utilisateur utilisateur = new Utilisateur();
                            utilisateur.setMail((String)objects[0]);
                            utilisateur.setMdp((String)objects[1]);
                            return authService.login(utilisateur);
                        } catch (Exception ex){
                            return ex;
                        }
                    }


                }.execute(email, password);
                loginProgress.setVisibility(View.VISIBLE);
            }
        });
    }
}
