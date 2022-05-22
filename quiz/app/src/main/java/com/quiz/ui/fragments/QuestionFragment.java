package com.quiz.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.quiz.R;
import com.quiz.models.PartieSave;
import com.quiz.services.QuizService;


public class QuestionFragment extends BaseFragment {
    String idCategorie;
    QuizService quizService;
    int index;
    boolean withIndex;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCategorie = getArguments().getString("idCategorie");
            withIndex = getArguments().containsKey("index");
            index = getArguments().getInt("index", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_question, container, false);
        initBase(root, false);
        quizService = QuizService.getInstance();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(withIndex){

        } else {
            PartieSave lastP = quizService.getLastPartie(idCategorie);
            if(lastP == null) {

            } else {

            }
        }
    }
}
