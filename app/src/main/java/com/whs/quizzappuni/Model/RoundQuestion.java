package com.whs.quizzappuni.Model;

/**
 * Created by krispin on 25.10.15.
 */
public class RoundQuestion {

    private int id;
    private int questionId;
    private int roundId;
    private int points;
    private boolean correct;


    public RoundQuestion(int id, int questionId, int roundId, int points, boolean correct) {

        this.id = id;
        this.questionId = questionId;
        this.roundId = roundId;
        this.points = points;
        this.correct = correct;
    }

    public RoundQuestion(int questionId, int points, boolean correct) {
        this.questionId = questionId;
        this.points = points;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
