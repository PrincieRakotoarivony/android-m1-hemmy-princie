package com.quiz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Utilisateur;
import com.quiz.services.AuthService;
import com.quiz.util.MultipleException;
import com.quiz.util.MyMap;
import com.quiz.util.Util;

public class SignUpActivity extends AppCompatActivity {

    AuthService authService;

    Animation animTop, animBottom, animToRight, animToLeft;
    LinearLayout headerLayout;
    LinearLayout signUpBtn;
    TextInputLayout layoutLastName, layoutFirstName, layoutEmail, layoutPassword, layoutConfirmPassword;
    TextView loginText;
    ProgressBar signUpProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Enter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        authService = AuthService.getInstance();

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
        signUpProgress = findViewById(R.id.sign_up_progress);


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

        signUpBtn.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUpProgress.getVisibility() == View.VISIBLE) return;

                String nom = layoutLastName.getEditText().getText().toString();
                String prenom = layoutFirstName.getEditText().getText().toString();
                String mail = layoutEmail.getEditText().getText().toString();
                String mdp = layoutPassword.getEditText().getText().toString();
                String confirmMdp = layoutConfirmPassword.getEditText().getText().toString();

                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);
                utilisateur.setMail(mail);
                utilisateur.setMdp(mdp);
                utilisateur.setConfirmMdp(confirmMdp);

                new AsyncTask<Utilisateur, Object, Object>() {
                    @Override
                    protected void onPostExecute(Object o) {
                        if(o instanceof Exception){
                            if(o instanceof MultipleException){
                                MultipleException ex = (MultipleException)o;
                                layoutLastName.setError(ex.getErrors().get("nom"));
                                layoutFirstName.setError(ex.getErrors().get("prenom"));
                                layoutEmail.setError(ex.getErrors().get("mail"));
                                layoutPassword.setError(ex.getErrors().get("mdp"));
                            } else{
                                Exception ex = (Exception) o;
                                ex.printStackTrace();
                                Util.showErrorMessage(ex.getMessage(), v);
                            }

                        }  else {
                            String token = (String) o;
                            KidzyApplication.set("token", token);
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        }
                        signUpProgress.setVisibility(View.GONE);
                    }

                    @Override
                    protected Object doInBackground(Utilisateur... objects) {
                        try{
                            return authService.signUp(objects[0]);
                        } catch (Exception ex){
                            return ex;
                        }
                    }


                }.execute(utilisateur);
                signUpProgress.setVisibility(View.VISIBLE);
            }
        });
    }
}
