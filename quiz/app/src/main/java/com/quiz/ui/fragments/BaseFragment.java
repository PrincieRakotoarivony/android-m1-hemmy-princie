package com.quiz.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quiz.ui.MainActivity;

public class BaseFragment extends Fragment {
    private RelativeLayout loadingLayout;
    public MainActivity getMainActivity(){
        return (MainActivity)getActivity();
    }

    public void setLoadingLayout(RelativeLayout loadingLayout){
        this.loadingLayout = loadingLayout;
    }

    public void startLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
    }

    public void stopLoading(){
        loadingLayout.setVisibility(View.GONE);
    }


}
