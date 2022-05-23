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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Cours;
import com.quiz.models.Publication;
import com.quiz.services.CoursService;
import com.quiz.services.ThemeService;
import com.quiz.util.Const;
import com.quiz.util.DownloadImageFromInternet;
import com.quiz.util.Util;

import java.util.List;


public class PublicationListFragment extends BaseFragment {

    TextView seeMoreText;
    ProgressBar seeMoreProgress;
    ThemeService themeService;
    LinearLayout btnSearch;
    TextInputLayout searchTextLayout;
    int page = 1;
    int nPerPage = 5;
    String idTheme;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            idTheme = getArguments().getString("idTheme");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_publication_list, container, false);
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
                        List<Publication> publications = (List<Publication>) o;
                        PublicationListFragment.this.page = page;
                        setPublications(publications);

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
                    return themeService.findPublications(objects[0], idTheme, page, nPerPage);
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

    public void setPublications(List<Publication> publications) throws Exception {
        FlexboxLayout pubContainer = getView().findViewById(R.id.pub_list_container);
        if(page == 1) pubContainer.removeAllViewsInLayout();

        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(KidzyApplication.convertDpToPx(250), FlexboxLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = KidzyApplication.convertDpToPx(10);

        for(Publication pub: publications){
            View pubCardView = getLayoutInflater().inflate(R.layout.pub_card_layout, null);
            TextView pubTitle = pubCardView.findViewById(R.id.pub_title);
            pubTitle.setText(pub.getTitre());

            TextView pubUser = pubCardView.findViewById(R.id.pub_user);
            pubUser.setText(Util.coalesce(pub.getUser().getPrenom()) + " " + pub.getUser().getNom());

            TextView pubDate = pubCardView.findViewById(R.id.pub_date);
            pubDate.setText(Util.formatDate(pub.getDatePub()));
            //pubDate.setText(String.valueOf(pub.getDatePub()));

            if(pub.getImg() != null) {
                ImageView pubImg = pubCardView.findViewById(R.id.pub_img);
                new DownloadImageFromInternet(pubImg).execute(Const.BASE_URL + "/" + pub.getImg());
            }

            pubCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", pub.get_id());
                    System.out.println("id = "+pub.get_id());
                    Navigation.findNavController(getView()).navigate(R.id.pub_list_to_details, bundle);
                }
            });

            pubContainer.addView(pubCardView, lp);
        }

        if(publications.size() < nPerPage){
            seeMoreText.setVisibility(View.GONE);
        } else {
            seeMoreText.setVisibility(View.VISIBLE);
        }
    }
}
