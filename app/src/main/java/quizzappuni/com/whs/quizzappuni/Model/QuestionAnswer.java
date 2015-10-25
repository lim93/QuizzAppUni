package quizzappuni.com.whs.quizzappuni.Model;

/**
 * Created by krispin on 25.10.15.
 */
public class QuestionAnswer {

    private int id;
    private int questionId;
    private String answerText;
    private boolean correctAnswer;

    public QuestionAnswer() {
    }

    public QuestionAnswer(int id, int questionId, String answerText, boolean correctAnswer) {
        this.id = id;
        this.questionId = questionId;
        this.answerText = answerText;
        this.correctAnswer = correctAnswer;
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

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
