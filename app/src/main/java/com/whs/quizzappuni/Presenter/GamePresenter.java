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
import com.whs.quizzappuni.Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by M on 31.10.2015.
 */
public class GamePresenter {

    public GameActivity view;
    public QuizzDBHelper qHelper;
    public UserDBHelper userDBHelper;

    public List<ToggleButton> buttons = new ArrayList<ToggleButton>();

    public Round round;
    public Question question;
    public List<Question> randomQuestions;

    //Hier wird die Anzahl der Fragen in einer Runde bestimmt (Aktuell: 7)!
    public int roundLength = 7;
    public int currentQuestion = 0;

    public int waitingTimePerQuestionRound = 1500;
    public int waitedTime = waitingTimePerQuestionRound * roundLength;

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

            setMaxRoundInformation();

            //Runde starten, Sekunden zählen?
            round.start(true);
        }

        setRoundInformations();

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
            //Achtung: Hier sind die Punkte pro Frage fest eingetragen (6Punkte pro richtige Frage)
            bundle.putInt("maxPoints", roundLength * 6);
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

            view.playMode.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));
            view.points.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));
            view.playMode.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));
            view.round_status.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));
            view.progressBar.setVisibility(View.GONE);
        }

        else {
            //StatusCard und den ausgewählten Button mit rot markieren
            view.statusCard.setCardBackgroundColor(ContextCompat.getColor(view.getApplicationContext(), R.color.wrongAnswer));

            view.playMode.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));
            view.points.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));
            view.playMode.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));
            view.round_status.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));
            view.progressBar.setVisibility(View.GONE);


            //TODO: hier müsste der gewählte  Button ggf. auf rot gesetzt werden; Dies funktioniert mit der aktuellen Einstellung der ToggleButtons nicht

            //richtige Antwort finden und den entsprechenden Button auf checked setzen um anzuzeigen, welches die richtige Antwort gewesen wäre
            if (question instanceof MultipleChoice) {
                if (questionAnswers[0].isCorrectAnswer())
                    view.Antwort1.setChecked(true);
                if (questionAnswers[1].isCorrectAnswer())
                    view.Antwort2.setChecked(true);
                if (questionAnswers[2].isCorrectAnswer())
                    view.Antwort3.setChecked(true);
                if (questionAnswers[3].isCorrectAnswer())
                    view.Antwort4.setChecked(true);
            } else if (question instanceof TrueFalse) {
                if (questionAnswers[0].isCorrectAnswer())
                    view.Antwort1.setChecked(true);
                if (questionAnswers[1].isCorrectAnswer())
                    view.Antwort2.setChecked(true);
            }
        }

        round.addRoundQuestion(question.getId(), question.getPoints(), selectedAnswer.isCorrectAnswer());

        startNextQuestion();
    }

    public void startNextQuestion(){
        //Informationen über die beantwortete Frage (Ergebnis war richtig oder falsch, etc.) werden für eine bestimmte "Wartezeit" lang angezeigt, bevor die nächste Frage geladen wird
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //Auszuführendes nach der Wartezeit
                resetCardColor();
                view.fabSendAlreadyClicked = false;
                view.answerButtonClicked = false;
                uncheckButtons();
                currentQuestion++;
                loadQuestion();
            }
            //Folgend: Angabe der Wartezeit in Millisekunden
        }, waitingTimePerQuestionRound);
    }

    public void uncheckButtons() {
        view.Antwort1.setChecked(false);
        view.Antwort2.setChecked(false);
        view.Antwort3.setChecked(false);
        view.Antwort4.setChecked(false);
    }

    public void setRoundInformations(){
        //Rundeninformationen in der View anpassen
        view.progressBar.setProgress(currentQuestion);
        view.points.setText(String.format("%d %s", round.getScore(), view.getResources().getString(R.string.points)));
    }

    public void setMaxRoundInformation(){
        view.progressBar.setMax(roundLength);
    }

    public void MainStarten() {
        Intent main = new Intent(view, MainActivity.class);
        view.startActivity(main);
    }

    public void resetCardColor(){
        view.statusCard.setCardBackgroundColor(ContextCompat.getColor(view.getApplicationContext(), R.color.lightwhite));

        view.playMode.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.black));
        view.points.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.black));
        view.playMode.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.black));
        view.round_status.setTextColor(ContextCompat.getColor(view.getApplicationContext(), R.color.black));
        view.progressBar.setVisibility(View.VISIBLE);
    }

    public void doWhenAnswerIsChoosed() {
        Utils.showFabWithAnimation(view.fabSend, 50);
    }

    public int getWaitedTime() {
        return waitedTime;
    }

    public void check() {

    }

    public void end() {
        view.finish();
    }
}
