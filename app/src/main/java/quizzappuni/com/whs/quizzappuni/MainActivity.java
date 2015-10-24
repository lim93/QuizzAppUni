package quizzappuni.com.whs.quizzappuni;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.support.design.widget.FloatingActionButton;


import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class MainActivity extends Activity {

    FloatingActionButton fab;
    ImageButton FAB;
    ImageButton dbButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, LearnMultiplechoice.class);
                startActivity(i);
            }
        });

        dbButton = (ImageButton) findViewById(R.id.dbButton);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, DB.class);
                startActivity(i);
            }
        });
    }
}
