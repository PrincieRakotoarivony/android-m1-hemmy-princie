package com.quiz.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;
import com.quiz.R;

public class RankingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ranking, container, false);
        MaterialCardView theme = root.findViewById(R.id.theme);
        theme.setOnClickListener(new MaterialCardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.theme_list_to_details);
            }
        });
        return root;
    }
}
