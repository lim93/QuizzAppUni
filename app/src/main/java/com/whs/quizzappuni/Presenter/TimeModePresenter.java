package com.whs.quizzappuni.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.whs.quizzappuni.Activities.ResultActivity;
import com.whs.quizzappuni.Model.MultipleChoice;
import com.whs.quizzappuni.Model.QuestionAnswer;
import com.whs.quizzappuni.Model.Round;
import com.whs.quizzappuni.Model.TrueFalse;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.QuizzDBHelper;
import com.whs.quizzappuni.Utils.UserDBHelper;

import java.util.concurrent.TimeUnit;

/**
 * Created by krispin on 20.11.15.
 */
public class TimeModePresenter extends GamePresenter {
    private int maxTime = 10000;
    public long actTime;
    public final Counter timer = new Counter(10000, 10);

    //timer: 10 Sekunden maxTime (in Millisekunden angegeben) , 10 Millisekunden Zählintervall

    //innere COUNTER-Klasse
    public class Counter extends CountDownTimer{

        public Counter(long maxTime, long countDownInterval){
            super(maxTime, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished){
            long millis = millisUntilFinished;
            actTime = millis;
            setActualTimeInformations();
        }

        @Override
        public void onFinish(){
            //verhindern, dass nach Ablauf der Zeit noch schnell eine andere Antwort gesetzt wird
            view.fabSendAlreadyClicked = true;

            //Runde stoppen
            timer.cancel();

            //Wenn keine Antwort innerhalb der Zeit ausgewählt wurde, dann soll eine richtige Antwort vermieden werden
            if (!view.Antwort1.isChecked() && !view.Antwort2.isChecked() && !view.Antwort3.isChecked() && !view.Antwort4.isChecked()) {
                view.answer = 0;
                question = randomQuestions.get(currentQuestion);
                QuestionAnswer[] questionAnswers = question.getAnswers();
                QuestionAnswer selectedAnswer = questionAnswers[view.answer];
                if(selectedAnswer.isCorrectAnswer() && view.answer < 3) {
                    view.answer++;
                }
            }

            confirmChoice();
        }

    }

    public void setActualTimeInformations(){
        int actualTime = (int) actTime;
        view.progressBar.setProgress(actualTime);
    }

    @Override
    public void setRoundInformations(){
        //Rundeninformationen in der View anpassen
        view.points.setText(String.format("%d %s", round.getScore(), view.getResources().getString(R.string.points)));
        view.playMode.setText(view.getResources().getString(R.string.time_mode));

    }

    @Override
    public void setMaxRoundInformation(){
        //int maximalTime = (int) maxTime;
        view.progressBar.setMax(maxTime);
    }

    @Override
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

        //Zusätlich hier: Den COUNTER starten (hier wird der Count-Down-Timer gestartet und die Fragerunde so zeitlich begrenzt)
        timer.start();
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
            round.stop();
            timer.cancel();
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

    @Override
    public void confirmChoice() {
        //Nach Bestätigung muss hier zusätzlich der Timer gestoppt werden
        timer.cancel();

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

        //Informationen über die beantwortete Frage (Ergebnis war richtig oder falsch, etc.) werden für eine bestimmte "Wartezeit" lang angezeigt, bevor die nächste Frage geladen wird
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //Auszuführendes nach der Wartezeit
                uncheckButtons();
                resetCardColor();
                view.fabSendAlreadyClicked = false;
                currentQuestion++;
                loadQuestion();
            }
            //Folgend: Angabe der Wartezeit in Millisekunden
        }, waitingTimePerQuestionRound);
    }

}
