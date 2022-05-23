package com.quiz.ui.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quiz.R;
import com.quiz.models.Publication;
import com.quiz.models.Theme;
import com.quiz.services.ThemeService;
import com.quiz.util.Const;
import com.quiz.util.DownloadImageFromInternet;
import com.quiz.util.Util;

import java.util.Objects;


public class PublicationFragment extends BaseFragment {


    ThemeService themeService;
    Publication pub;
    Dialog commentDialog;
    String id;

    ImageView pubImg;
    TextView pubTitle, pubDesc, pubUser, pubDate;

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
        View root = inflater.inflate(R.layout.fragment_publication, container, false);
        initBase(root);
        themeService = ThemeService.getInstance();


        pubTitle = root.findViewById(R.id.pub_title);
        pubDesc = root.findViewById(R.id.pub_desc);
        pubUser = root.findViewById(R.id.pub_user);
        pubDate = root.findViewById(R.id.pub_date);
        pubImg = root.findViewById(R.id.pub_img);


        commentDialog = new Dialog(getActivity(), R.style.AnimateDialog);
        commentDialog.setContentView(R.layout.comment_popup);

        FloatingActionButton commentBtn = root.findViewById(R.id.add_comment);
        commentBtn.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(commentDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                commentDialog.show();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    public void initData(){
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPostExecute(Object o) {
                try {
                    if (o instanceof Exception) {
                        Exception ex = (Exception) o;
                        ex.printStackTrace();
                        Util.showErrorMessage(ex.getMessage(), getView());
                    } else {
                        Publication pub = (Publication) o;
                        PublicationFragment.this.pub = pub;
                        setPublicationView();
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
                    return themeService.findPubById(id);
                } catch (Exception ex){
                    return ex;
                }
            }

        }.execute();

        startLoading();
    }

    public void setPublicationView() {
        pubTitle.setText(pub.getTitre());
        pubDesc.setText(pub.getDescription());
        pubUser.setText(Util.coalesce(pub.getUser().getPrenom()) + " " + pub.getUser().getNom());
        pubDate.setText(Util.formatDate(pub.getDatePub()));
        if(pub.getImg() != null){
            new DownloadImageFromInternet(pubImg).execute(Const.BASE_URL + "/" + pub.getImg());
        }
    }


}
