package com.quiz.ui.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quiz.R;

import java.util.Objects;


public class PublicationFragment extends BaseFragment {

    Dialog commentDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_publication, container, false);

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


}
