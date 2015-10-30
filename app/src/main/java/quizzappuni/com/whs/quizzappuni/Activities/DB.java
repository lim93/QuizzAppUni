package quizzappuni.com.whs.quizzappuni.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import quizzappuni.com.whs.quizzappuni.Model.Question;
import quizzappuni.com.whs.quizzappuni.Model.QuestionAnswer;
import quizzappuni.com.whs.quizzappuni.Utils.DBHelper;
import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class DB extends Activity implements View.OnClickListener {

    FloatingActionButton dbButton;
    TextView dbCreate;
    TextView dbOpen;
    TextView dbResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        dbButton = (FloatingActionButton) findViewById(R.id.dbButton);
        dbButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        dbCreate = (TextView) findViewById(R.id.dbCreate);
        dbOpen = (TextView) findViewById(R.id.dbOpen);
        dbResult = (TextView) findViewById(R.id.dbResult);

        DBHelper dbHelper = new DBHelper(this);

        //Todo: In activity_main ausführen?
        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        dbCreate.setText("DB created!");

        try {
            dbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        dbOpen.setText("DB opened!");

        Question question = dbHelper.loadQuestionById(2);

        QuestionAnswer[] questionAnswers = question.getAnswers();
        StringBuilder sb = new StringBuilder();

        for(QuestionAnswer qa : questionAnswers) {
            sb.append(qa.getAnswerText());
            sb.append(", ");
        }

        dbResult.setText("Question: " + question.getQuestionText() +
                " Answers: " + sb.toString());

    }

    public void onBackPressed() {
        // Call the MainActivity, if the back-button is pressed.
        setResult(RESULT_CANCELED);
        finish();
        //Intent i = new Intent(DB.this, MainActivity.class);
        //startActivity(i);
    }
}
