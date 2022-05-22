package com.quiz.ui.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quiz.R;


public class VideoPlayFragment extends BaseFragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_video_play, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar progressBar = view.findViewById(R.id.progress_circular);
        VideoView videoView = view.findViewById(R.id.video_view);

        progressBar.setVisibility(View.VISIBLE);
        videoView.setMediaController(new MediaController(getActivity()));
        String url = "https://cdn-cf-east.streamable.com/video/mp4/6j8m7u.mp4?Expires=1653074460&Signature=FfWmri9wXZvjYEkAzaJHWfnNUwq5149kyEjcPNGhY7Ehy25nztBEdbeN6cEBcIa4jzYXa1WDgrcfBQN1ThZ47v5TWAqZQNzBZ5gtup2IRjTMyQX3hwbkyIew0jxEId4KkZZGk-Cw0gpIV1GwlvOWTxbGpSpEugW4bnNSKvw15WVvOezqgUYTdYRQAWT201~UUYAEiw6~qwV6H-5g9QVn1f2rn8qA3ID60sPW7tWduf6Thtm32WiIwNz6g-4lXVBchdxMy0j1ALgPzpZ0LltUSXUs0vtSEJijxo6qdio7EE4wW1b38XBIL7Ukvm43GYQ5K022TxAFlIFZQC6NHAoXWg__&Key-Pair-Id=APKAIEYUVEN4EVB2OKEQ";
        //url = "android.resource://com.quiz/" + R.raw.vid;
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                videoView.start();
            }
        });

    }
}
