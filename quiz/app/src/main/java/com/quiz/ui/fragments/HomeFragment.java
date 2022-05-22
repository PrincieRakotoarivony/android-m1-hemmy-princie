package com.quiz.ui.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Categorie;
import com.quiz.services.QuizService;
import com.quiz.ui.LoginActivity;
import com.quiz.util.Const;
import com.quiz.util.DownloadImageFromInternet;
import com.quiz.util.Util;

import java.util.List;

public class HomeFragment extends BaseFragment {

    QuizService quizService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initBase(root);
        quizService = QuizService.getInstance();

        /*
        LinearLayout catAnimal = root.findViewById(R.id.cat_animal);
        catAnimal.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("amount", 10);
                Navigation.findNavController(root).navigate(R.id.home_to_question, bundle);
            }
        });
        */


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
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
                        List<Categorie> categories = (List<Categorie>) o;
                        setCategories(categories);
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
                    return quizService.findCategories();
                } catch (Exception ex){
                    return ex;
                }
            }

        }.execute();
        startLoading();
    }

    public void setCategories(List<Categorie> categories){
        LinearLayout catContainer = getView().findViewById(R.id.cat_container);
        catContainer.removeAllViewsInLayout();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, KidzyApplication.convertDpToPx(110));
        lp.topMargin = KidzyApplication.convertDpToPx(10);

        for(Categorie cat: categories){
            View catView = getLayoutInflater().inflate(R.layout.one_cat, null);
            TextView catName = catView.findViewById(R.id.cat_name);
            catName.setText(cat.getNom());

            ImageView catImg = catView.findViewById(R.id.cat_img);
            new DownloadImageFromInternet(catImg).execute(Const.BASE_URL + "/" + cat.getImg());

            catView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("idCategorie", cat.get_id());
                    Navigation.findNavController(getView()).navigate(R.id.home_to_question, bundle);
                }
            });

            catContainer.addView(catView, lp);
        }
    }
}
