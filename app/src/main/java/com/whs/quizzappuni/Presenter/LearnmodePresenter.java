package com.whs.quizzappuni.Presenter;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import com.whs.quizzappuni.Activities.LearnMultiplechoiceActivity;
import com.whs.quizzappuni.Activities.ResultActivity;
import com.whs.quizzappuni.Model.MultipleChoice;
import com.whs.quizzappuni.Model.Question;
import com.whs.quizzappuni.Model.QuestionAnswer;
import com.whs.quizzappuni.Model.Round;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.QuizzDBHelper;

/**
 * Created by M on 31.10.2015.
 */
public class LearnmodePresenter {

    private LearnMultiplechoiceActivity view;

    QuizzDBHelper qHelper;
    //TODO:RundenlÃ¤nge dynamisch laden
    private int roundLength = 5;
    private int thisRound = 0;
    Round round = new Round();
    List<ToggleButton> buttons = new ArrayList<ToggleButton>();
    List<Question> randomQuestions;
    Question question;
    CharSequence answer;
    //private Throwable error;
    //QuizzDBHelper qHelper = new QuizzDBHelper(view.getApplicationContext());
    //UserDBHelper uHelper = new UserDBHelper(view.getApplicationContext());

    public LearnmodePresenter(){

    }

    public void onTakeView(LearnMultiplechoiceActivity view) {
        this.view = view;
    }

    public void openDB(){
        qHelper = new QuizzDBHelper(view.getApplicationContext());
        qHelper.openDataBase();
    }

    public void loadQuestion(){
        //Beim ersten Starten die DB öffnen, Buttons zur ButtonList hinzufügen und zufällige Fragen laden
        if(thisRound == 0) {
            openDB();
            buttons.add(view.Antwort1);
            buttons.add(view.Antwort2);
            buttons.add(view.Antwort3);
            buttons.add(view.Antwort4);
            randomQuestions = qHelper.getRandomQuestions(roundLength);
        }

        //Rundendurchlauf
        if(thisRound < roundLength) {
            //Fragen in einer Runde behandeln
            question = randomQuestions.get(thisRound);
                view.question.setText(question.getQuestionText());
                QuestionAnswer[] questionAnswers = question.getAnswers();

                //Behandlung der verschiedenen Fragetypen, um die View entsprechend des jeweiligen Fragetyps anzupassen
                if (question instanceof MultipleChoice){
                    view.Antwort1.setVisibility(View.VISIBLE);
                    view.Antwort2.setVisibility(View.VISIBLE);
                    view.Antwort3.setVisibility(View.VISIBLE);
                    view.Antwort4.setVisibility(View.VISIBLE);
                }
                else{
                    view.Antwort1.setVisibility(View.VISIBLE);
                    view.Antwort2.setVisibility(View.VISIBLE);
                    view.Antwort3.setVisibility(View.GONE);
                    view.Antwort4.setVisibility(View.GONE);
                }

                //Durchlaufen der Antworten zur Frage
                for (int i = 0; i < questionAnswers.length; i++) {
                    QuestionAnswer answer = questionAnswers[i];

                    buttons.get(i).setText(answer.getAnswerText());
                    buttons.get(i).setTextOn(answer.getAnswerText());
                    buttons.get(i).setTextOff(answer.getAnswerText());
                }
        }
        else {
            //TODO: In DB schreiben
            Intent result = new Intent(view, ResultActivity.class);
            view.startActivity(result);
        }
    }

    //Auf die Bestaetigung nach der Antwort-Auswahl reagieren
    public void confirmChoice(){
        //TODO: Antwort auswerten + in Runde setzen

        question = randomQuestions.get(thisRound);
        QuestionAnswer[] questionAnswers = question.getAnswers();
        QuestionAnswer answer = questionAnswers[view.answer];
         if (answer.isCorrectAnswer()){
             view.statusCard.setBackgroundColor(ContextCompat.getColor(view.getApplicationContext(), R.color.rightAnswer));

         }
        else{
             view.statusCard.setBackgroundColor(ContextCompat.getColor(view.getApplicationContext(), R.color.wrongAnswer));
         }
/**
 *if(view.Antwort1.isChecked())
 *view.Antwort1.setBackgroundColor(view.getResources().getColor(R.color.wrongAnswer));
 *if(view.Antwort2.isChecked())
 *view.Antwort2.getTextOn();
 *if(view.Antwort3.isChecked())
 *view.Antwort3.getTextOn();
 *if(view.Antwort4.isChecked())
 *view.Antwort4.getTextOn();
 */

        //Warte für 3 Sekunden
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 3 seconds
                //TODO:FAB-Button ausblenden
                uncheckButtons();
                thisRound++;
                loadQuestion();
            }
        }, 3000);
    }
    public void uncheckButtons(){
        view.Antwort1.setChecked(false);
        view.Antwort2.setChecked(false);
        view.Antwort3.setChecked(false);
        view.Antwort4.setChecked(false);
    }

    public void check(){

    }

    public void end(){
        view.finish();
    }


}
