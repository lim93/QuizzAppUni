package quizzappuni.com.whs.quizzappuni.Utils;

import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import quizzappuni.com.whs.quizzappuni.Model.MultipleChoice;
import quizzappuni.com.whs.quizzappuni.Model.Question;
import quizzappuni.com.whs.quizzappuni.Model.QuestionAnswer;
import quizzappuni.com.whs.quizzappuni.Model.TrueFalse;

/**
 * Created by krispin on 30.10.15.
 */
public class QuizzDBHelper extends DBHelper {

    private static final String DB_NAME = "quizzDB.sqlite";

    public QuizzDBHelper(Context context) {
        super(context, DB_NAME);
    }


    public void createAndOpenDatabase() throws IOException {
        forceCreateDataBase();
        openDataBase();
    }



    public Question loadQuestionById(int questionId) {

        Question question;

        Cursor resultSet = db.rawQuery("Select * from question where _id = " + questionId + ";", null);
        resultSet.moveToFirst();

        int qId = resultSet.getInt(0);
        String questionText = resultSet.getString(1);
        int questionType = resultSet.getInt(2);
        //TODO: Definition und Category

        QuestionAnswer[] questionAnswers = loadQuestionAnswersById(qId);

        switch (questionType) {
            case 1:
                question = new MultipleChoice(questionAnswers);
                break;
            case 2:
                question = new TrueFalse(questionAnswers);
                break;
            default:
                throw new IllegalStateException("Illegal Question Type.");
        }

        question.setId(qId);
        question.setQuestionText(questionText);

        resultSet.close();
        return question;

    }

    private QuestionAnswer[] loadQuestionAnswersById(int questionId) {

        Cursor resultSet = db.rawQuery("Select qa.answer_id, qa.question_id, a.answer_text, " +
                "qa.correct_answer from question_answer qa join answer a on qa.answer_id = a._id " +
                "where qa.question_id = " + questionId + ";", null);

        List<QuestionAnswer> questionAnswers = new ArrayList<QuestionAnswer>();


        resultSet.moveToFirst();

        while (!resultSet.isAfterLast()) {

            int id = resultSet.getInt(0);
            int qId = resultSet.getInt(1);
            String answerText = resultSet.getString(2);

            Boolean correctAnswer = false;
            if (resultSet.getInt(3) == 1) {
                correctAnswer = true;
            }

            QuestionAnswer questionAnswer = new QuestionAnswer(id, qId, answerText, correctAnswer);
            questionAnswers.add(questionAnswer);
            resultSet.moveToNext();

        }

        resultSet.close();

        QuestionAnswer[] qaArray = new QuestionAnswer[questionAnswers.size()];
        qaArray = questionAnswers.toArray(qaArray);
        return qaArray;

    }



}
