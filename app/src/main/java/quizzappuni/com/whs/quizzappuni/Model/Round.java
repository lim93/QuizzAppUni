package quizzappuni.com.whs.quizzappuni.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krispin on 25.10.15.
 */
public class Round {

    private int id;
    private int durationSeconds;
    private int score;
    private List<RoundQuestion> roundQuestions;

    public Round() {
        this.score=0;
        this.durationSeconds = 0;
        this.roundQuestions = new ArrayList<RoundQuestion>();
    }

    public Round(int id, int durationSeconds, int score, List<RoundQuestion> roundQuestions) {
        this.id = id;
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

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<RoundQuestion> getRoundQuestions() {
        return roundQuestions;
    }

    public void setRoundQuestions(List<RoundQuestion> roundQuestions) {
        this.roundQuestions = roundQuestions;
    }
}
