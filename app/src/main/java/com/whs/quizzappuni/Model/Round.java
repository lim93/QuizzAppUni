package com.whs.quizzappuni.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by krispin on 25.10.15.
 */
public class Round {

    private int id;
    private long startDate;
    private int durationSeconds;
    private int score;
    private List<RoundQuestion> roundQuestions;

    public Round() {

        this.startDate = Calendar.getInstance().getTimeInMillis();
        this.score = 0;
        this.durationSeconds = 0;
        this.roundQuestions = new ArrayList<>();
    }

    public Round(int id, long startDate, int durationSeconds, int score, List<RoundQuestion> roundQuestions) {
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

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
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
