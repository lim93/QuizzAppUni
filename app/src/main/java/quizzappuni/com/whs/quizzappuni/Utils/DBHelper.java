package quizzappuni.com.whs.quizzappuni.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import quizzappuni.com.whs.quizzappuni.Model.MultipleChoice;
import quizzappuni.com.whs.quizzappuni.Model.Question;
import quizzappuni.com.whs.quizzappuni.Model.QuestionAnswer;
import quizzappuni.com.whs.quizzappuni.Model.TrueFalse;

/**
 * Created by krispin on 24.10.15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/quizzappuni.com.whs.quizzappuni/databases/";
    private static String DB_NAME = "quizzdb.sqlite";
    private final Context context;
    private SQLiteDatabase quizzdb;

    /**
     * Konstruktor - Bekommt eine Referenz auf den Context um auf den assets-Ordner zugreifen zu können
     *
     * @param context
     */
    public DBHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    /**
     * Leere DB erzeugen und mit der quizzdb überschreiben.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = false;
        //checkDataBase();

        if (dbExist) {
            //nichts zu tun
        } else {

            //Leere DB erzeugen
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check ob es die DB schon gibt. Dazu wird versucht, die DB zu öffnen
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String fullPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(fullPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //DB existiert nocht nicht
        }

        if (checkDB != null) {
            //falls es die DB schon gibt, wieder schliessen
            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Kopiert die quizzdb aus dem assets-Ordner und überschreibt damit die zuvor erzeugte leere DB
     */
    private void copyDataBase() throws IOException {

        //qizzdb als InputStream öffnen
        InputStream quizzdb = context.getAssets().open(DB_NAME);

        String fileName = DB_PATH + DB_NAME;

        //leere db als OutputStream öffnen
        OutputStream output = new FileOutputStream(fileName);

        //überschreiben
        byte[] buffer = new byte[1024];
        int length;
        while ((length = quizzdb.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        //Streams schliessen
        output.flush();
        output.close();
        quizzdb.close();

    }

    public void openDataBase() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        quizzdb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    public String loadQuestion() {

        Cursor resultSet = quizzdb.rawQuery("Select * from question", null);
        resultSet.moveToFirst();
        String question = resultSet.getString(1);

        return question;

    }

    public Question loadQuestionById(int questionId) {

        Question question;

        Cursor resultSet = quizzdb.rawQuery("Select * from question where _id = " + questionId + ";", null);
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

        Cursor resultSet = quizzdb.rawQuery("Select qa.answer_id, qa.question_id, a.answer_text, " +
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


    @Override
    public synchronized void close() {

        if (quizzdb != null)
            quizzdb.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
