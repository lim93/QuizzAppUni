package com.whs.quizzappuni.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.whs.quizzappuni.Model.Question;
import com.whs.quizzappuni.Model.QuestionAnswer;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.QuizzDBHelper;
import com.whs.quizzappuni.Utils.UserDBHelper;

import java.util.List;

public class DB extends Activity implements View.OnClickListener {

    FloatingActionButton dbButton;
    TextView dbCreate;
    TextView dbOpen;
    TextView quizzdbResult;
    TextView userdbResult;

    QuizzDBHelper qHelper;
    UserDBHelper uHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        dbButton = (FloatingActionButton) findViewById(R.id.dbButton);
        dbButton.setOnClickListener(this);

        dbCreate = (TextView) findViewById(R.id.dbCreate);
        dbOpen = (TextView) findViewById(R.id.dbOpen);

        qHelper = new QuizzDBHelper(this.getApplicationContext());
        uHelper = new UserDBHelper(this.getApplicationContext());

        //Todo: In activity_main ausführen

        qHelper.createDatabase();
        dbCreate.setText("QuizzDB created");

        uHelper.createDatabase();
        dbOpen.setText("UserDB created");

    }

    @Override
    public void onClick(View v) {


        quizzdbResult = (TextView) findViewById(R.id.quizzdbResult);
        userdbResult = (TextView) findViewById(R.id.userdbResult);


        List<Question> randomQuestions = qHelper.getRandomQuestions(3);
        StringBuilder sb = new StringBuilder();

        for (Question question : randomQuestions) {

            sb.append("Question: " + question.getQuestionText() +
                    " Answers: ");

            QuestionAnswer[] questionAnswers = question.getAnswers();

            for (QuestionAnswer qa : questionAnswers) {
                sb.append(qa.getAnswerText());
                sb.append(", ");
            }
        }

        quizzdbResult.setText(sb.toString());


       /* Round round = uHelper.loadRoundById(1);

        sb = new StringBuilder();
        sb.append("Runde: ");
        sb.append(round.getId());
        sb.append(", Dauer:");
        sb.append(round.getDurationSeconds() + "Sekunden");
        sb.append(", Fragen:");

        for (RoundQuestion rq : round.getRoundQuestions()) {
            sb.append(rq.getQuestionId());
            sb.append(",");

            if (rq.isCorrect()) {
                sb.append("Richtig beantwortet");
            } else {
                sb.append(" Falsch beantwortet");
            }
            sb.append("; ");

        }

        sb.append("Punkte: " + round.getScore());

        userdbResult.setText(sb.toString());*/

    }


    public void onBackPressed() {
        // Call the MainActivity, if the back-button is pressed.
        setResult(RESULT_CANCELED);
        finish();
        //Intent i = new Intent(DB.this, MainActivity.class);
        //startActivity(i);
    }


}