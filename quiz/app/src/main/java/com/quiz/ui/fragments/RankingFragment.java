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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Cours;
import com.quiz.models.Theme;
import com.quiz.services.CoursService;
import com.quiz.services.ThemeService;
import com.quiz.util.Const;
import com.quiz.util.DownloadImageFromInternet;
import com.quiz.util.Util;

import java.util.List;

public class RankingFragment extends BaseFragment {

    boolean all = true;
    TextView seeMoreText;
    ProgressBar seeMoreProgress;
    ThemeService themeService;
    LinearLayout btnSearch;
    TextInputLayout searchTextLayout;
    int page = 1;
    int nPerPage = 5;


    public RankingFragment(){}
    public RankingFragment(boolean all){
        this.all = all;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ranking, container, false);
        themeService = ThemeService.getInstance();
        initBase(root);

        seeMoreText = root.findViewById(R.id.seeMoreBtn);
        seeMoreProgress = root.findViewById(R.id.seeMoreProgress);
        btnSearch = root.findViewById(R.id.search_btn);
        searchTextLayout = root.findViewById(R.id.search_input);


        seeMoreText.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeMore();
            }
        });
        btnSearch.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
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
        initData(1);
    }

    public void seeMore(){
        initData(page + 1);
    }

    public void initData(int page){
        String search = searchTextLayout.getEditText().getText().toString();
        new AsyncTask<String, Object, Object>() {
            @Override
            protected void onPostExecute(Object o) {
                try {
                    if (o instanceof Exception) {
                        Exception ex = (Exception) o;
                        ex.printStackTrace();
                        Util.showErrorMessage(ex.getMessage(), getView());
                    } else {
                        List<Theme> themes = (List<Theme>) o;
                        RankingFragment.this.page = page;
                        setThemes(themes);

                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                } finally {
                    if(page == 1) {
                        stopLoading();
                    } else{
                        seeMoreProgress.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            protected Object doInBackground(String... objects) {
                try{
                    return themeService.findThemes(objects[0], all, page, nPerPage);
                } catch (Exception ex){
                    return ex;
                }
            }

        }.execute(search);

        if(page == 1) {
            startLoading();
        } else{
            seeMoreText.setVisibility(View.GONE);
            seeMoreProgress.setVisibility(View.VISIBLE);
        }
    }

    public void setThemes(List<Theme> themes) {
        LinearLayout themeContainer = getView().findViewById(R.id.theme_list_container);
        if(page == 1) themeContainer.removeAllViewsInLayout();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, KidzyApplication.convertDpToPx(80));
        lp.topMargin = KidzyApplication.convertDpToPx(10);

        for(Theme t: themes){
            View themeCardView = getLayoutInflater().inflate(R.layout.theme_card_layout, null);
            TextView videoDesc = themeCardView.findViewById(R.id.theme_title);
            videoDesc.setText(t.getNom());

            if(t.getImg() != null) {
                ImageView catImg = themeCardView.findViewById(R.id.theme_img);
                new DownloadImageFromInternet(catImg).execute(Const.BASE_URL + "/" + t.getImg());
            }

            themeCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", t.get_id());
                    Navigation.findNavController(getView()).navigate(R.id.theme_list_to_details, bundle);
                }
            });

            themeContainer.addView(themeCardView, lp);
        }

        if(themes.size() < nPerPage){
            seeMoreText.setVisibility(View.GONE);
        } else {
            seeMoreText.setVisibility(View.VISIBLE);
        }
    }
}
