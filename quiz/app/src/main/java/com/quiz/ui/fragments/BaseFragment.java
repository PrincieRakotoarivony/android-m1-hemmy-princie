package com.quiz.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.quiz.R;
import com.quiz.ui.MainActivity;

public class BaseFragment extends Fragment {
    private RelativeLayout loadingLayout;
    private SwipeRefreshLayout refreshLayout;

    public MainActivity getMainActivity(){
        return (MainActivity)getActivity();
    }

    public void initBase(View root){
        setLoadingLayout(root.findViewById(R.id.common_loading));
        SwipeRefreshLayout refresher = root.findViewById(R.id.refresher);
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                refresher.setRefreshing(false);
            }
        });

        setRefreshLayout(refresher);

    }

    public void setLoadingLayout(RelativeLayout loadingLayout){
        this.loadingLayout = loadingLayout;
    }

    public void setRefreshLayout(SwipeRefreshLayout refreshLayout){
        this.refreshLayout = refreshLayout;
    }

    public void startLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
    }

    public void stopLoading(){
        loadingLayout.setVisibility(View.GONE);
    }

    public void initData(){}

}
