package com.whs.quizzappuni.Presenter;

import android.content.Intent;
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
import com.whs.quizzappuni.Utils.QuizzDBHelper;

/**
 * Created by M on 31.10.2015.
 */
public class LearnmodePresenter {

    private LearnMultiplechoiceActivity view;

    QuizzDBHelper qHelper;
    //TODO:RundenlÃ¤nge dynamisch laden
    private int roundLength = 3;
    private int thisRound = 0;
    Round round = new Round();
    List<ToggleButton> buttons = new ArrayList<ToggleButton>();
    List<Question> randomQuestions;
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
            Question question = randomQuestions.get(thisRound);
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
        thisRound++;
        //TODO:Problembehebung (s. folgendes Kommentarfeld) löschen, wenn nicht mehr benötigt
        /**
         * //Die View-Anzeige und View-Elemente neu laden, um Anzeigeprobleme zu beheben
         * //Problembehebung sollte nun überflüssig sein
         * if(thisRound < roundLength) {
         *  view.setContentView(R.layout.activity_learn_multiplechoice);
         *  view.loadElements();
         * }
         *  */
        uncheckButtons();
        //TODO: Antwort auswerten + in Runde setzen

        loadQuestion();
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
