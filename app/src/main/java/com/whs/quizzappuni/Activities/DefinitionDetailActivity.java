package com.whs.quizzappuni.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whs.quizzappuni.R;

public class DefinitionDetailActivity extends AppCompatActivity {

    public TextView definitionContentCard;
    public TextView definitionTitleCard;
    public Button definitionLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition_detail);

        // Erstellen der Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Load Definition Text
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();



        definitionTitleCard = (TextView) findViewById(R.id.definitionTitleCard);
        definitionTitleCard.setText(bundle.getString("term"));
        definitionContentCard = (TextView) findViewById(R.id.definitionContentCard);
        definitionContentCard.setText(bundle.getString("definitionText"));

        definitionLink = (Button) findViewById(R.id.definitionLink);
        definitionLink.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(bundle.getString("source"))));
            }
        });

    }
}




