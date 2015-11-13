package com.whs.quizzappuni.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.whs.quizzappuni.R;

public class DefinitionDetailActivity extends AppCompatActivity {

    public TextView definitionContentCard;
    public TextView definitionTitleCard;


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
        Bundle bundle = intent.getExtras();



        definitionTitleCard = (TextView) findViewById(R.id.definitionTitleCard);
        definitionTitleCard.setText(bundle.getString("term"));
        definitionContentCard = (TextView) findViewById(R.id.definitionContentCard);
        definitionContentCard.setText(bundle.getString("definitionText"));

    }
}




