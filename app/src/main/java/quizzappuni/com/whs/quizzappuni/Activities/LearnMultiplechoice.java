package quizzappuni.com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class LearnMultiplechoice extends Activity {

    private Button Antwort1;
    private Button Antwort2;
    private Button Antwort3;
    private Button Antwort4;
    String antwortmoeglichkeit1;
    String roundResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_multiplechoice);

        //Übergebene Fragen und Antworten entgegennehmen
        Intent start = getIntent();
        antwortmoeglichkeit1 = start.getExtras().getString("Antwort1");

        Antwort1 = (Button)findViewById(R.id.Antwort1);
        Antwort1.setText(antwortmoeglichkeit1);
        Antwort1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("result", roundResult);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        Antwort2 = (Button)findViewById(R.id.Antwort2);
        Antwort2.setText("2.Antwort");

        Antwort3 = (Button)findViewById(R.id.Antwort3);
        Antwort3.setText("3.Antwort");

        Antwort4 = (Button)findViewById(R.id.Antwort4);
        Antwort4.setText("4.Antwort");

    }

    public void onBackPressed() {
        // Call the MainActivity, if the back-button is pressed.
        Intent i = new Intent(LearnMultiplechoice.this, MainActivity.class);
        startActivity(i);
    }

}
