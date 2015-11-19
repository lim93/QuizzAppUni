package com.whs.quizzappuni.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.whs.quizzappuni.Model.Definition;
import com.whs.quizzappuni.Model.MultipleChoice;
import com.whs.quizzappuni.Model.Question;
import com.whs.quizzappuni.Model.QuestionAnswer;
import com.whs.quizzappuni.Model.TrueFalse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by krispin on 30.10.15.
 */
public class QuizzDBHelper extends DBHelper {

    private static final String DB_NAME = "quizzDB.sqlite";

    public QuizzDBHelper(Context context) {
        super(context, DB_NAME);
    }


    public void createDatabase() {

        forceCreateDataBase();

    }


    public Question loadQuestionById(int questionId) {

        Question question;
        Definition definition;

        try {
            openReadOnly();

            Cursor resultSet = db.rawQuery("Select * from question where _id = " + questionId + ";", null);
            resultSet.moveToFirst();

            int qId = resultSet.getInt(0);
            String questionText = resultSet.getString(1);
            int questionType = resultSet.getInt(2);
            int definitionId = resultSet.getInt(3);

            if (definitionId == 0) {
                throw new IllegalStateException("Die Definitions-Id darf nicht '0' sein, Frage " + questionId + "!");
            } else {
                definition = loadDefinitionById(definitionId);
            }

            int points = resultSet.getInt(5);


            //TODO: Category

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
            question.setDefinition(definition);
            question.setPoints(points);

            resultSet.close();

            return question;

        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim öffnen oder abfragen der Datenbank: " + e.getMessage());
        } finally {
            close();
        }


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

    public List<Question> getRandomQuestions(int count) {

        List<Integer> questionIds = new ArrayList<Integer>();
        Set<Integer> randomQuestionIDs = new HashSet<Integer>();
        List<Question> returnQuestions = new ArrayList<Question>();

        try {
            openReadOnly();

            Cursor resultSet = db.rawQuery("Select _id from question;", null);

            resultSet.moveToFirst();

            while (!resultSet.isAfterLast()) {

                int id = resultSet.getInt(0);
                questionIds.add(id);
                resultSet.moveToNext();

            }

            resultSet.close();

            if (questionIds.size() < count) {
                throw new IllegalArgumentException("In der DB existieren nicht genug Fragen: " +
                        "Vorhanden " + questionIds.size() +
                        ", Abgefragt " + count);
            }

            while (randomQuestionIDs.size() < count) {

                Random rand = new Random();

                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                int id = rand.nextInt((questionIds.size() - 1) + 1) + 1;

                randomQuestionIDs.add(id);

            }

            for (Integer id : randomQuestionIDs) {
                returnQuestions.add(loadQuestionById(id));
            }

            return returnQuestions;
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim öffnen oder abfragen der Datenbank: " + e.getMessage());
        } finally {
            close();
        }


    }

    private Definition loadDefinitionById(int definitionId) {

        Definition definition;

        Cursor resultSet = db.rawQuery("Select * from definition where _id = " + definitionId + ";", null);
        resultSet.moveToFirst();

        int id = resultSet.getInt(0);
        String term = resultSet.getString(1);
        String definition_text = resultSet.getString(2);
        int category_id = resultSet.getInt(3);
        String source = resultSet.getString(4);
        //TODO: Category

        definition = new Definition(id, term, definition_text, null, source);

        resultSet.close();
        return definition;

    }


    public List<Definition> loadDefinitionList() {


        List<Definition> definitionList = new ArrayList<Definition>();

        try {
            openReadOnly();

            Cursor resultSet = db.rawQuery("Select * from definition ORDER by term ASC;", null);
            resultSet.moveToFirst();


            while (!resultSet.isAfterLast()) {

                int definitionId = resultSet.getInt(0);
                String term = resultSet.getString(1);
                String definition_text = resultSet.getString(2);
                int category_id = resultSet.getInt(3);
                String source = resultSet.getString(4);
                //TODO: Category

                definitionList.add(new Definition(definitionId, term, definition_text, null, source));

                resultSet.moveToNext();

            }

            resultSet.close();

            return definitionList;
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim öffnen oder abfragen der Datenbank: " + e.getMessage());
        } finally {
            close();
        }


    }


}
