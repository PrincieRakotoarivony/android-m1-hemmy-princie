package com.quiz.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.quiz.R;
import com.quiz.models.Theme;
import com.quiz.services.ThemeService;
import com.quiz.util.Const;
import com.quiz.util.DownloadImageFromInternet;
import com.quiz.util.Util;

import java.util.List;


public class ThemeFragment extends BaseFragment {

    String id;
    Theme theme;
    ThemeService themeService;
    TextView themeTitle, themeDesc;
    ImageView themeImg;
    Button btnSubscribe, btnUnsubscribe, btnSeePub, btnNewPub;
    SwitchMaterial themeNotif;
    LinearLayout notifContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            id = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_theme, container, false);
        themeService = ThemeService.getInstance();
        initBase(root);

        themeTitle = root.findViewById(R.id.theme_title);
        themeDesc = root.findViewById(R.id.theme_desc);
        themeImg = root.findViewById(R.id.theme_img);
        btnSubscribe = root.findViewById(R.id.btn_subscribe);
        btnUnsubscribe = root.findViewById(R.id.btn_unsubscribe);
        btnSeePub = root.findViewById(R.id.btn_see_pub);
        btnNewPub = root.findViewById(R.id.btn_new_pub);
        themeNotif = root.findViewById(R.id.theme_notif);
        notifContainer = root.findViewById(R.id.notif_container);

        btnSubscribe.setVisibility(View.GONE);
        btnUnsubscribe.setVisibility(View.GONE);
        notifContainer.setVisibility(View.GONE);
        //btnSeePub.setVisibility(View.GONE);
        btnNewPub.setVisibility(View.GONE);

        btnSubscribe.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(true, null);
            }
        });

        btnUnsubscribe.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(false, null);
            }
        });

        themeNotif.setOnClickListener(new SwitchMaterial.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(null, themeNotif.isChecked());
            }
        });

        btnNewPub.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putString("idTheme", id);
                Navigation.findNavController(root).navigate(R.id.theme_to_new_pub, params);
            }
        });

        btnSeePub.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putString("idTheme", id);
                Navigation.findNavController(root).navigate(R.id.theme_to_pub_list, params);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void initData(){
        initData(null, null);
    }

    public void initData(Boolean subscription, Boolean notif){
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPostExecute(Object o) {
                try {
                    if (o instanceof Exception) {
                        Exception ex = (Exception) o;
                        ex.printStackTrace();
                        Util.showErrorMessage(ex.getMessage(), getView());
                    } else {
                        Theme theme = (Theme) o;
                        ThemeFragment.this.theme = theme;
                        setThemeView();
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                } finally {
                    stopLoading();
                }

            }

            @Override
            protected Object doInBackground(Object... objects) {
                try{
                    if(subscription != null){
                        if(subscription) themeService.subscribe(id);
                        else themeService.unsubscribe(id);
                    }
                    if(notif != null){
                        themeService.changeNotif(id, notif);
                    }
                    return themeService.findThemeById(id);
                } catch (Exception ex){
                    return ex;
                }
            }

        }.execute();

        startLoading();
    }

    public void setThemeView() {
        themeTitle.setText(theme.getNom());
        themeDesc.setText(theme.getDescription());
        if(theme.getImg() != null)
            new DownloadImageFromInternet(themeImg).execute(Const.BASE_URL + "/" + theme.getImg());
        if(theme.getAbonm() == null){
            btnSubscribe.setVisibility(View.VISIBLE);
            btnUnsubscribe.setVisibility(View.GONE);
            notifContainer.setVisibility(View.GONE);
            //btnSeePub.setVisibility(View.GONE);
            btnNewPub.setVisibility(View.GONE);
        } else{
            btnSubscribe.setVisibility(View.GONE);
            btnUnsubscribe.setVisibility(View.VISIBLE);
            notifContainer.setVisibility(View.VISIBLE);
            //btnSeePub.setVisibility(View.VISIBLE);
            btnNewPub.setVisibility(View.VISIBLE);
            themeNotif.setChecked(theme.getAbonm().isNotif());
        }
    }
}
