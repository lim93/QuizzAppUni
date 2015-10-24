package quizzappuni.com.whs.quizzappuni;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class MainActivity extends Activity {

    ImageButton FAB;
    ImageButton dbButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FAB = (ImageButton) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
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
