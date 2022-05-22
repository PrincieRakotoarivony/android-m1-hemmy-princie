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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quiz.KidzyApplication;
import com.quiz.R;
import com.quiz.models.Categorie;
import com.quiz.models.PartieSave;
import com.quiz.models.Question;
import com.quiz.services.QuizService;
import com.quiz.util.Const;
import com.quiz.util.DownloadImageFromInternet;
import com.quiz.util.Util;

import java.util.List;


public class QuestionFragment extends BaseFragment {
    String idCategorie;
    QuizService quizService;
    PartieSave lastP;

    TextView stepText, questionText;
    ImageView questionImage;

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
    }
}
