package com.quiz.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Utilisateur;
import com.quiz.services.AuthService;
import com.quiz.ui.LoginActivity;
import com.quiz.ui.MainActivity;
import com.quiz.util.MyMap;
import com.quiz.util.Util;

import java.util.Objects;


public class LogoutFragment extends Fragment {

    AuthService authService;

    Dialog logoutDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        authService = AuthService.getInstance();

        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        logoutDialog = new Dialog(getActivity(), R.style.AnimateDialog);
        logoutDialog.setContentView(R.layout.logout_popup);
        Objects.requireNonNull(logoutDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logoutDialog.setCanceledOnTouchOutside(false);

        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPostExecute(Object o) {
                if(o instanceof Exception){
                    Exception ex = (Exception)o;
                    ex.printStackTrace();
                    Util.showErrorMessage(ex.getMessage(), root);
                } else {
                    KidzyApplication.remove("token");
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                logoutDialog.dismiss();
            }

            @Override
            protected Object doInBackground(Object... objects) {
                try{
                    authService.logout(KidzyApplication.get("token"));
                    return "ok";
                } catch (Exception ex){
                    return ex;
                }
            }


        }.execute();

        logoutDialog.show();
        return root;
    }
}
