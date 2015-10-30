package quizzappuni.com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ToggleButton;

import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class LearnMultiplechoice extends AppCompatActivity {

    private Button Antwort1;
    private Button Antwort2;
    private Button Antwort3;
    private Button Antwort4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_multiplechoice);

        // Erstellen der Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Antwort1 = (ToggleButton) findViewById(R.id.Antwort1);
//        Antwort1.setTextOn("1.Antwort");
        Antwort2 = (ToggleButton) findViewById(R.id.Antwort2);
//        Antwort2.setText("2.Antwort");
//
        Antwort3 = (ToggleButton) findViewById(R.id.Antwort3);
//        Antwort3.setText("3.Antwort");
//
        Antwort4 = (ToggleButton) findViewById(R.id.Antwort4);
//        Antwort4.setText("4.Antwort");


//        Antwort1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(LearnMultiplechoice.this, MainActivity.class);
//                startActivity(i);
//            }
//        });
//


    }
}