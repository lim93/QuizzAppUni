package com.whs.quizzappuni.Utils;

import android.content.Context;
import android.database.Cursor;

import com.whs.quizzappuni.Model.Round;
import com.whs.quizzappuni.Model.RoundQuestion;

import java.io.IOException;
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


    public List<Round> loadRoundList() {


        List<Round> resultList = new ArrayList<Round>();

        Cursor resultSet = db.rawQuery("Select * from round ORDER by rId DESC;", null);
        resultSet.moveToFirst();


        while (!resultSet.isAfterLast()) {

            int rId = resultSet.getInt(0);
            int rDuration = resultSet.getInt(1);
            int rScore = resultSet.getInt(2);
            //TODO: Category

            List<RoundQuestion> roundQuestions = loadRoundQuestionsById(rId);

            resultList.add(new Round(rId, rDuration, rScore, roundQuestions));

            resultSet.moveToNext();

        }

        resultSet.close();

        return resultList;


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
