package quizzappuni.com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.view.View;
import android.support.design.widget.FloatingActionButton;


import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class Start extends Activity {

    FloatingActionButton fab;
    FloatingActionButton dbButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("modeChoice", "LearnMultipleChoice");
                setResult(RESULT_OK, data);
                finish();
            }
        });

        dbButton = (FloatingActionButton) findViewById(R.id.dbButton);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("modeChoice", "DB");
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
    public void onBackPressed() {
        //Intent data = new Intent();
        //data.putExtra("exit", "exit");
        //setResult(RESULT_CANCELED, data);
        setResult(RESULT_CANCELED);
        finish();
    }
}
