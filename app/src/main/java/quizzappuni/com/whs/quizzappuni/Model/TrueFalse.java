package quizzappuni.com.whs.quizzappuni.Model;

/**
 * Created by krispin on 25.10.15.
 */
public class TrueFalse extends Question {

    private static final int ANSWER_COUNT = 2;
    private static final int POINTS = 1;

    public TrueFalse(QuestionAnswer[] answers) {
        super();

        if (answers.length != ANSWER_COUNT) {
            throw new IllegalArgumentException("Illegal Answer Count, must be " + ANSWER_COUNT + " but was " + answers.length);
        }
        this.answers = answers;

    }

    public static int getAnswerCount() {
        return ANSWER_COUNT;
    }

    public static int getPOINTS() {
        return POINTS;
    }

}
