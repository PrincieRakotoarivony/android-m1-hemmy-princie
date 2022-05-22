package com.quiz.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Categorie;
import com.quiz.models.PartieSave;
import com.quiz.models.Question;
import com.quiz.models.QuestionItem;
import com.quiz.services.QuizService;
import com.quiz.ui.slideshow.MyChoiceButton;
import com.quiz.util.Const;
import com.quiz.util.DownloadImageFromInternet;
import com.quiz.util.Util;

import java.util.ArrayList;
import java.util.List;


public class QuestionFragment extends BaseFragment {
    String idCategorie;
    QuizService quizService;
    PartieSave lastP;

    TextView stepText, questionText;
    ImageView questionImage;
    LinearLayout choiceContainer;
    List<MyChoiceButton> choices;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCategorie = getArguments().getString("idCategorie");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_question, container, false);
        initBase(root, false);
        quizService = QuizService.getInstance();

        stepText = root.findViewById(R.id.step_text);
        questionText = root.findViewById(R.id.question_text);
        questionImage = root.findViewById(R.id.question_img);
        choiceContainer = root.findViewById(R.id.choice_container);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lastP = quizService.getLastPartie(idCategorie);
        if(lastP == null) {
            getQuestions();
        } else {
            setQuestion();
        }

    }

    public void getQuestions(){
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPostExecute(Object o) {
                try {
                    if (o instanceof Exception) {
                        Exception ex = (Exception) o;
                        ex.printStackTrace();
                        Util.showErrorMessage(ex.getMessage(), getView());
                    } else {
                        List<Question> questions = (List<Question>) o;
                        setNewQuestions(questions);
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
                    return quizService.getQuestions(idCategorie);
                } catch (Exception ex){
                    return ex;
                }
            }

        }.execute();
        startLoading();
    }

    public void setNewQuestions(List<Question> questions) throws Exception{
        System.out.println(Util.getGson().toJson(questions));
        lastP = new PartieSave(KidzyApplication.get("idUser"), idCategorie, questions);
        quizService.savePartie(lastP);
        setQuestion();
    }

    public void setQuestion(){
        Question q = lastP.getCurrentQuestion();
        stepText.setText(String.format("%d/%d", lastP.getLastIndex()+1, lastP.getNbrTotal()));

        if(q.getTarget().getImage() == null){
            questionImage.setVisibility(View.GONE);
        } else{
            new DownloadImageFromInternet(questionImage).execute(Const.BASE_URL + "/" + q.getTarget().getImage());
            questionImage.setVisibility(View.VISIBLE);
        }

        if(q.getTarget().getNom() == null){
            questionText.setVisibility(View.GONE);
        } else{
            questionText.setText(q.getTarget().getNom());
            questionText.setVisibility(View.VISIBLE);
        }

        choiceContainer.removeAllViewsInLayout();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = KidzyApplication.convertDpToPx(20);

        choices = new ArrayList<>();
        for(QuestionItem choice: q.getSuggestions()){
            Button choiceBtn = (Button)getLayoutInflater().inflate(R.layout.quiz_choice_btn, null);
            choiceBtn.setText(choice.getNom());


            choiceBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(choice.isCorrect()){

                    } else{
                        choiceBtn.setBackgroundResource(R.drawable.choice_bg_error);
                    }

                    for(MyChoiceButton cb: choices){
                        cb.getBtn().setEnabled(false);
                        if(cb.isCorrect()){
                            cb.getBtn().setBackgroundResource(R.drawable.choice_bg_success);
                        }
                    }
                }
            });

            choiceContainer.addView(choiceBtn, lp);
            choices.add(new MyChoiceButton(choiceBtn, choice.isCorrect()));

        }
    }
}
