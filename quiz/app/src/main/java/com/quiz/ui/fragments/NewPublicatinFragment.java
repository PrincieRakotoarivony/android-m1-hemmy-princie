package com.quiz.ui.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Publication;
import com.quiz.models.Utilisateur;
import com.quiz.services.ThemeService;
import com.quiz.ui.MainActivity;
import com.quiz.ui.SignUpActivity;
import com.quiz.util.MultipleException;
import com.quiz.util.MyMap;
import com.quiz.util.Util;

public class NewPublicatinFragment extends BaseFragment {

    String idTheme;
    ThemeService themeService;
    TextInputLayout pubTitle, pubDesc;
    LinearLayout saveBtn;
    ProgressBar saveProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idTheme = getArguments().getString("idTheme");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_publicatin, container, false);
        initBase(root, false);
        themeService = ThemeService.getInstance();
        pubTitle = root.findViewById(R.id.pub_title);
        pubDesc = root.findViewById(R.id.pub_description);
        saveBtn = root.findViewById(R.id.save_btn);
        saveProgress = root.findViewById(R.id.save_progress);

        saveBtn.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveProgress.getVisibility() == View.VISIBLE) return;

                String titre = pubTitle.getEditText().getText().toString();
                String description = pubDesc.getEditText().getText().toString();


                Publication pub = new Publication();
                pub.setTitre(titre);
                pub.setDescription(description);
                pub.setId_theme(idTheme);

                new AsyncTask<Publication, Object, Object>() {
                    @Override
                    protected void onPostExecute(Object o) {
                        if(o instanceof Exception){
                            if(o instanceof MultipleException){
                                MultipleException ex = (MultipleException)o;
                                pubTitle.setError(ex.getErrors().get("titre"));
                                pubDesc.setError(ex.getErrors().get("description"));

                            } else{
                                Exception ex = (Exception) o;
                                ex.printStackTrace();
                                Util.showErrorMessage(ex.getMessage(), v);
                            }

                        }  else {
                            String idPub = (String) o;

                        }
                        saveProgress.setVisibility(View.GONE);
                    }

                    @Override
                    protected Object doInBackground(Publication... objects) {
                        try{
                            return themeService.savePublication(objects[0]);
                        } catch (Exception ex){
                            return ex;
                        }
                    }


                }.execute(pub);
                saveProgress.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }
}
