package com.quiz.ui.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Comment;
import com.quiz.models.Publication;
import com.quiz.models.Theme;
import com.quiz.services.ThemeService;
import com.quiz.util.Const;
import com.quiz.util.DownloadImageFromInternet;
import com.quiz.util.Util;

import java.util.List;
import java.util.Objects;


public class PublicationFragment extends BaseFragment {


    ThemeService themeService;
    Publication pub;
    Dialog commentDialog;
    String id;

    ImageView pubImg;
    TextView pubTitle, pubDesc, pubUser, pubDate;
    TextInputLayout commentText;

    Button commentSendBtn, commentCancelBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            id = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_publication, container, false);
        initBase(root);
        themeService = ThemeService.getInstance();


        pubTitle = root.findViewById(R.id.pub_title);
        pubDesc = root.findViewById(R.id.pub_desc);
        pubUser = root.findViewById(R.id.pub_user);
        pubDate = root.findViewById(R.id.pub_date);
        pubImg = root.findViewById(R.id.pub_img);


        commentDialog = new Dialog(getActivity(), R.style.AnimateDialog);
        commentDialog.setContentView(R.layout.comment_popup);
        commentText = commentDialog.findViewById(R.id.comment_text);
        commentSendBtn = commentDialog.findViewById(R.id.comment_send);
        commentCancelBtn = commentDialog.findViewById(R.id.comment_cancel);

        commentSendBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(true);
                commentDialog.dismiss();
            }
        });

        commentCancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDialog.dismiss();
            }
        });

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void initData(){
        initData(false);
    }

    public void initData(boolean comment){
        String commentStr = commentText.getEditText().getText().toString();
        new AsyncTask<String, Object, Object>() {
            @Override
            protected void onPostExecute(Object o) {
                try {
                    if (o instanceof Exception) {
                        Exception ex = (Exception) o;
                        ex.printStackTrace();
                        Util.showErrorMessage(ex.getMessage(), getView());
                    } else {
                        Publication pub = (Publication) o;
                        PublicationFragment.this.pub = pub;
                        setPublicationView();
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                } finally {
                    stopLoading();
                }

            }

            @Override
            protected Object doInBackground(String... objects) {
                try{
                    if(comment){
                        Comment c = new Comment();
                        c.setContent(objects[0]);
                        c.setId_pub(id);
                        themeService.comment(c);
                    }
                    return themeService.findPubById(id);
                } catch (Exception ex){
                    return ex;
                }
            }

        }.execute(commentStr);

        startLoading();
    }

    public void setPublicationView() {
        pubTitle.setText(pub.getTitre());
        pubDesc.setText(pub.getDescription());
        pubUser.setText(Util.coalesce(pub.getUser().getPrenom()) + " " + pub.getUser().getNom());
        pubDate.setText(Util.formatDate(pub.getDatePub()));
        if(pub.getImg() != null){
            new DownloadImageFromInternet(pubImg).execute(Const.BASE_URL + "/" + pub.getImg());
        }
        setCommentsView();
    }

    public void setCommentsView()  {
        LinearLayout commentContainer = getView().findViewById(R.id.comments_container);
        commentContainer.removeAllViewsInLayout();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = KidzyApplication.convertDpToPx(10);

        for(Comment c: pub.getComments()){
            View commentCardView = getLayoutInflater().inflate(R.layout.comment_layout, null);
            TextView comment_user = commentCardView.findViewById(R.id.comment_user);
            comment_user.setText(Util.coalesce(c.getUser().getPrenom()) + " " + c.getUser().getNom());

            TextView comment_date = commentCardView.findViewById(R.id.comment_date);
            comment_date.setText(Util.formatDate(c.getDateComment()));

            TextView comment_content = commentCardView.findViewById(R.id.comment_content);
            comment_content.setText(c.getContent());


            commentContainer.addView(commentCardView, lp);
        }
    }



}
