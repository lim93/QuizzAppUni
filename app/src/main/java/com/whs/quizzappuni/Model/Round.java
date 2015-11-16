package com.whs.quizzappuni.Model;

import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by krispin on 25.10.15.
 */
public class Round {

    private int id;
    private long startDate;
    private long endDate;
    private long durationSeconds;
    private int score;
    private List<RoundQuestion> roundQuestions;

    private boolean countTime;

    public Round() {
        this.roundQuestions = new ArrayList<>();
    }

    public Round(int id, long startDate, long durationSeconds, int score, List<RoundQuestion> roundQuestions) {
        this.id = id;
        this.startDate = startDate;
        this.durationSeconds = durationSeconds;
        this.score = score;
        this.roundQuestions = roundQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public long getDurationSeconds() {
        return durationSeconds;
    }

    public int getScore() {
        return score;
    }

    public List<RoundQuestion> getRoundQuestions() {
        return roundQuestions;
    }

    public void setRoundQuestions(List<RoundQuestion> roundQuestions) {
        this.roundQuestions = roundQuestions;
    }


    public void start(boolean countTime) {
        this.score = 0;
        this.durationSeconds = 0l;
        this.countTime = countTime;
        this.startDate = Calendar.getInstance().getTimeInMillis();
    }

    public void stop() {

        if (countTime) {
            this.endDate = Calendar.getInstance().getTimeInMillis();
            Duration duration = new Duration(startDate, endDate);
            this.durationSeconds = duration.getStandardSeconds();
        }

        updateScore();

    }


    public void addRoundQuestion(int questionId, int points, boolean correct) {

        RoundQuestion roundQuestion = new RoundQuestion(questionId, points, correct);
        this.roundQuestions.add(roundQuestion);
        updateScore();

    }

    private void updateScore() {

        this.score = 0;

        for (RoundQuestion roundQuestion : this.roundQuestions) {
            if (roundQuestion.isCorrect()) {
                this.score += roundQuestion.getPoints();
            }
        }


    }
}
