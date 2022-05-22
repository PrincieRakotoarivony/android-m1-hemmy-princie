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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Categorie;
import com.quiz.models.Cours;
import com.quiz.services.CoursService;
import com.quiz.util.Const;
import com.quiz.util.DownloadImageFromInternet;
import com.quiz.util.Util;

import java.util.List;

public class VideoListFragment extends BaseFragment {

    CoursService coursService;
    int page = 1;
    int nPerPage = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_video_list, container, false);
        coursService = CoursService.getInstance();
        initBase(root);

        /*MaterialCardView videoCard = root.findViewById(R.id.video_card);
        videoCard.setOnClickListener(new MaterialCardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.courses_to_play);
            }
        });*/
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void initData(){
        initData(1);
    }

    public void initData(int page){
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPostExecute(Object o) {
                try {
                    if (o instanceof Exception) {
                        Exception ex = (Exception) o;
                        ex.printStackTrace();
                        Util.showErrorMessage(ex.getMessage(), getView());
                    } else {
                        List<Cours> cours = (List<Cours>) o;
                        setCours(cours);
                        VideoListFragment.this.page = page;
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
                    return coursService.findCours("", page, nPerPage);
                } catch (Exception ex){
                    return ex;
                }
            }

        }.execute();
        startLoading();
    }

    public void setCours(List<Cours> cours) {
        LinearLayout videoContainer = getView().findViewById(R.id.video_list_container);
        videoContainer.removeAllViewsInLayout();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, KidzyApplication.convertDpToPx(100));
        lp.topMargin = KidzyApplication.convertDpToPx(10);

        for(Cours c: cours){
            View videoCardView = getLayoutInflater().inflate(R.layout.video_card_layout, null);
            TextView videoDesc = videoCardView.findViewById(R.id.video_text);
            videoDesc.setText(c.getDescription());

            ImageView catImg = videoCardView.findViewById(R.id.video_img);
            new DownloadImageFromInternet(catImg).execute(Const.BASE_URL + "/" + c.getImage());

            videoCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("link", c.getLink());
                    Navigation.findNavController(getView()).navigate(R.id.courses_to_play, bundle);
                }
            });

            videoContainer.addView(videoCardView, lp);
        }
    }
}
