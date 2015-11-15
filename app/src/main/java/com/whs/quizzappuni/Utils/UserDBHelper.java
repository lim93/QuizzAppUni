package com.whs.quizzappuni.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.whs.quizzappuni.Model.Round;
import com.whs.quizzappuni.Model.RoundQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krispin on 24.10.15.
 */
public class UserDBHelper extends DBHelper {

    private static String DB_NAME = "userDB.sqlite";

    public UserDBHelper(Context context) {
        super(context, DB_NAME);
    }

    public void createDatabase() {

        createDataBase();
    }

    public List<Round> loadRoundList() {


        List<Round> resultList = new ArrayList<>();

        try {
            openReadOnly();

            Cursor resultSet = db.rawQuery("Select * from round;", null);
            resultSet.moveToFirst();

            while (!resultSet.isAfterLast()) {

                int rId = resultSet.getInt(0);
                long rStartDate = resultSet.getLong(1);
                int rDuration = resultSet.getInt(2);
                int rScore = resultSet.getInt(3);

                List<RoundQuestion> roundQuestions = loadRoundQuestionsById(rId);

                resultList.add(new Round(rId, rStartDate, rDuration, rScore, roundQuestions));

                resultSet.moveToNext();

            }

            resultSet.close();

            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim öffnen oder abfragen der Datenbank: " + e.getMessage());
        } finally {
            close();
        }


    }

    private List<RoundQuestion> loadRoundQuestionsById(int roundId) {

        Cursor resultSet = db.rawQuery("Select * from round_question " +
                "where round_id = " + roundId + ";", null);

        List<RoundQuestion> roundQuestions = new ArrayList<RoundQuestion>();


        resultSet.moveToFirst();

        while (!resultSet.isAfterLast()) {

            int id = resultSet.getInt(0);
            int rId = resultSet.getInt(1);
            int qId = resultSet.getInt(2);
            int points = resultSet.getInt(3);
            boolean correct = resultSet.getInt(4) != 0;

            RoundQuestion roundQuestion = new RoundQuestion(id, qId, rId, points, correct);

            roundQuestions.add(roundQuestion);
            resultSet.moveToNext();

        }

        resultSet.close();

        return roundQuestions;

    }


    public int writeRound(Round round) {

        try {
            openReadWrite();

            ContentValues values = new ContentValues();
            values.put("start_date", round.getStartDate());
            values.put("duration_sec", round.getDurationSeconds());
            values.put("score", round.getScore());

            int roundId = (int) db.insert("round", null, values);

            for (RoundQuestion roundQuestion : round.getRoundQuestions()) {
                roundQuestion.setRoundId(roundId);

                writeRoundQuestion(roundQuestion);
            }

            return roundId;
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim öffnen der Datenbank oder schreiben der Daten: " + e.getMessage());
        } finally {
            close();
        }

    }

    private int writeRoundQuestion(RoundQuestion roundQuestion) {

        ContentValues values = new ContentValues();
        values.put("round_id", roundQuestion.getRoundId());
        values.put("question_id", roundQuestion.getQuestionId());
        values.put("points", roundQuestion.getPoints());
        values.put("correct", roundQuestion.isCorrect());

        int id = (int) db.insert("round_question", null, values);

        return id;

    }


}
