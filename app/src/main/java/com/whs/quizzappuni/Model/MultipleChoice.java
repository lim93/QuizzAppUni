package com.whs.quizzappuni.Model;

/**
 * Created by krispin on 25.10.15.
 */
public class MultipleChoice extends Question {

    private static final int ANSWER_COUNT = 4;
    private static final int POINTS = 2;

    public MultipleChoice(QuestionAnswer[] answers) {
        super();

        if (answers.length != ANSWER_COUNT) {
            throw new IllegalArgumentException("Illegal Answer Count, must be " + ANSWER_COUNT);
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
