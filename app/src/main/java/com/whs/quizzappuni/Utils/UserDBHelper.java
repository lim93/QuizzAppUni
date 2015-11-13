package com.whs.quizzappuni.Utils;

import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.whs.quizzappuni.Model.Round;
import com.whs.quizzappuni.Model.RoundQuestion;

/**
 * Created by krispin on 24.10.15.
 */
public class UserDBHelper extends DBHelper {

    private static String DB_NAME = "userDB.sqlite";

    public UserDBHelper(Context context) {
        super(context, DB_NAME);
    }

    public void createAndOpenDatabase() throws IOException {
        createDataBase();
        openDataBase();
    }

    public Round loadRoundById(int roundId) {

        Cursor resultSet = db.rawQuery("Select * from round where _id = " + roundId + ";", null);
        resultSet.moveToFirst();

        int rId = resultSet.getInt(0);
        int rDuration = resultSet.getInt(1);
        int rScore = resultSet.getInt(2);


        List<RoundQuestion> roundQuestions = loadRoundQuestionsById(rId);

        Round round = new Round(rId, rDuration, rScore, roundQuestions);

        resultSet.close();
        return round;

    }


    private List<RoundQuestion> loadRoundQuestionsById(int roundId) {

        Cursor resultSet = db.rawQuery("Select _id, round_id, question_id, correct from round_question " +
                "where round_id = " + roundId + ";", null);

        List<RoundQuestion> roundQuestions = new ArrayList<RoundQuestion>();


        resultSet.moveToFirst();

        while (!resultSet.isAfterLast()) {

            int id = resultSet.getInt(0);
            int rId = resultSet.getInt(1);
            int qId = resultSet.getInt(2);
            boolean correct = resultSet.getInt(3) != 0;

            RoundQuestion roundQuestion = new RoundQuestion(id, qId, rId, correct);

            roundQuestions.add(roundQuestion);
            resultSet.moveToNext();

        }

        resultSet.close();

        return roundQuestions;

    }


}