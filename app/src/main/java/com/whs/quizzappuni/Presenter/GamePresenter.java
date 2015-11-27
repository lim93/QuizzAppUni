package com.whs.quizzappuni.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ToggleButton;

import com.whs.quizzappuni.Activities.GameActivity;
import com.whs.quizzappuni.Activities.MainActivity;
import com.whs.quizzappuni.Activities.ResultActivity;
import com.whs.quizzappuni.Model.MultipleChoice;
import com.whs.quizzappuni.Model.Question;
import com.whs.quizzappuni.Model.QuestionAnswer;
import com.whs.quizzappuni.Model.Round;
import com.whs.quizzappuni.Model.TrueFalse;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.QuizzDBHelper;
import com.whs.quizzappuni.Utils.UserDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M on 31.10.2015.
 */
public class GamePresenter {

    private GameActivity view;
    private QuizzDBHelper qHelper;
    private UserDBHelper userDBHelper;

    private List<ToggleButton> buttons = new ArrayList<ToggleButton>();

    private Round round;
    private Question question;
    private List<Question> randomQuestions;

    //TODO:Rundenlänge dynamisch laden?
    private int roundLength = 5;
    private int currentQuestion = 0;

    public GamePresenter() {

    }

    public void onTakeView(GameActivity view) {
        this.view = view;
    }

    public void loadQuestion() {
        //Beim ersten Starten den QuizzDBHelper initialisieren, zufällige Fragen laden und Buttons zur ButtonList hinzufügen
        if (currentQuestion == 0) {
            round = new Round();
            qHelper = new QuizzDBHelper(view.getApplicationContext());
            randomQuestions = qHelper.getRandomQuestions(roundLength);

            buttons.add(view.Antwort1);
            buttons.add(view.Antwort2);
            buttons.add(view.Antwort3);
            buttons.add(view.Antwort4);

            view.progressBar.setMax(roundLength);

            //Runde starten, Sekunden zählen?
            round.start(true);
        }

        //Rundeninformationen in der View anpassen
        view.progressBar.setProgress(currentQuestion);
        view.points.setText(String.format("%d %s", round.getScore(), view.getResources().getString(R.string.points)));

        //Rundendurchlauf
        if (currentQuestion < roundLength) {
            //Runde hochzählen
            view.round_status.setText(String.format("%d/%d", currentQuestion + 1, roundLength));

            //Fragen in einer Runde behandeln
            question = randomQuestions.get(currentQuestion);
            view.question.setText(question.getQuestionText());
            QuestionAnswer[] questionAnswers = question.getAnswers();

            //Behandlung der verschiedenen Fragetypen, um die View entsprechend des jeweiligen Fragetyps anzupassen
            if (question instanceof MultipleChoice) {
                view.Antwort1.setVisibility(View.VISIBLE);
                view.Antwort2.setVisibility(View.VISIBLE);
                view.Antwort3.setVisibility(View.VISIBLE);
                view.Antwort4.setVisibility(View.VISIBLE);
            } else if (question instanceof TrueFalse) {
                view.Antwort1.setVisibility(View.VISIBLE);
                view.Antwort2.setVisibility(View.VISIBLE);
                view.Antwort3.setVisibility(View.GONE);
                view.Antwort4.setVisibility(View.GONE);
            } else {
                throw new IllegalStateException("Der Fragetyp " + question.getClass() + " wird noch nicht unterstützt!");
            }

            //Durchlaufen der Antworten zur Frage
            for (int i = 0; i < questionAnswers.length; i++) {
                QuestionAnswer answer = questionAnswers[i];

                buttons.get(i).setText(answer.getAnswerText());
                buttons.get(i).setTextOn(answer.getAnswerText());
                buttons.get(i).setTextOff(answer.getAnswerText());
            }
        } else {
            //Runde stoppen
            round.stop();
            userDBHelper = new UserDBHelper(view.getApplicationContext());
            userDBHelper.writeRound(round);

            //Punkte der Runde an die Result-Activity übergeben und diese Activity schließlich starten
            Bundle bundle = new Bundle();
            bundle.putInt("points", round.getScore());
            bundle.putLong("seconds", round.getDurationSeconds());
            bundle.putString("mode", view.gamemode);
            Intent result = new Intent(view, ResultActivity.class);
            result.putExtras(bundle);
            view.startActivity(result);
        }
    }

    //Auf die Bestaetigung nach der Antwort-Auswahl reagieren
    public void confirmChoice() {

        question = randomQuestions.get(currentQuestion);
        QuestionAnswer[] questionAnswers = question.getAnswers();
        QuestionAnswer selectedAnswer = questionAnswers[view.answer];

        if (selectedAnswer.isCorrectAnswer()) {
            view.statusCard.setCardBackgroundColor(ContextCompat.getColor(view.getApplicationContext(), R.color.rightAnswer));
        } else {
            view.statusCard.setCardBackgroundColor(ContextCompat.getColor(view.getApplicationContext(), R.color.wrongAnswer));
        }

        round.addRoundQuestion(question.getId(), question.getPoints(), selectedAnswer.isCorrectAnswer());

/**
 *if(view.Antwort1.isChecked())
 *view.Antwort1.setCardBackgroundColor(view.getResources().getColor(R.color.wrongAnswer));
 *if(view.Antwort2.isChecked())
 *view.Antwort2.getTextOn();
 *if(view.Antwort3.isChecked())
 *view.Antwort3.getTextOn();
 *if(view.Antwort4.isChecked())
 *view.Antwort4.getTextOn();
 */

        //Informationen über die beantwortete Frage (Ergebnis war richtig oder falsch, etc.) werden für eine bestimmte "Wartezeit" lang angezeigt, bevor die nächste Frage geladen wird
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //Auszuführende nach der Wartezeit
                //TODO:FAB-Button ausblenden
                uncheckButtons();
                view.statusCard.setCardBackgroundColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));
                view.fabSendAlreadyClicked = false;
                currentQuestion++;
                loadQuestion();
            }
            //Folgend: Angabe der Wartezeit
        }, 1000);
    }

    public void uncheckButtons() {
        view.Antwort1.setChecked(false);
        view.Antwort2.setChecked(false);
        view.Antwort3.setChecked(false);
        view.Antwort4.setChecked(false);
    }

    public void MainStarten() {
        Intent main = new Intent(view, MainActivity.class);
        view.startActivity(main);
    }

    public void check() {

    }

    public void end() {
        view.finish();
    }


}
