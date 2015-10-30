package quizzappuni.com.whs.quizzappuni.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import quizzappuni.com.whs.quizzappuni.Model.Question;
import quizzappuni.com.whs.quizzappuni.Model.QuestionAnswer;
import quizzappuni.com.whs.quizzappuni.Model.Round;
import quizzappuni.com.whs.quizzappuni.Model.RoundQuestion;
import quizzappuni.com.whs.quizzappuni.Utils.QuizzDBHelper;
import quizzappuni.com.whs.quizzappuni.Utils.UserDBHelper;
import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class DB extends Activity implements View.OnClickListener {

    FloatingActionButton dbButton;
    TextView dbCreate;
    TextView dbOpen;
    TextView quizzdbResult;
    TextView userdbResult;


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
        quizzdbResult = (TextView) findViewById(R.id.quizzdbResult);
        userdbResult = (TextView) findViewById(R.id.userdbResult);

        QuizzDBHelper qHelper = new QuizzDBHelper(this.getApplicationContext());
        UserDBHelper uHelper = new UserDBHelper(this.getApplicationContext());

        //Todo: In activity_main ausführen
        try {
            qHelper.createAndOpenDatabase();
        } catch (IOException ioe) {
            throw new Error("Unable to create QuizzDB");
        }

        dbCreate.setText("QuizzDB created and opened");

        try {
            uHelper.createAndOpenDatabase();
        } catch (IOException ioe) {
            throw new Error("Unable to create UserDB");
        }

        dbOpen.setText("UserDB created and opened");


        Question question = qHelper.loadQuestionById(2);

        QuestionAnswer[] questionAnswers = question.getAnswers();
        StringBuilder sb = new StringBuilder();

        for (QuestionAnswer qa : questionAnswers) {
            sb.append(qa.getAnswerText());
            sb.append(", ");
        }

        quizzdbResult.setText("Question: " + question.getQuestionText() +
                " Answers: " + sb.toString());


        Round round = uHelper.loadRoundById(1);

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

        userdbResult.setText(sb.toString());

    }


    public void onBackPressed() {
        // Call the MainActivity, if the back-button is pressed.
        setResult(RESULT_CANCELED);
        finish();
        //Intent i = new Intent(DB.this, MainActivity.class);
        //startActivity(i);
    }


}