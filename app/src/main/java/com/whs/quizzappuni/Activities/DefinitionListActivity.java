package com.whs.quizzappuni.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.whs.quizzappuni.Model.Definition;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.DefinitionAdapter;
import com.whs.quizzappuni.Utils.QuizzDBHelper;

import java.util.List;



/**
 * Created by krispin on 07.11.15.
 */
public class DefinitionListActivity extends AppCompatActivity {

    ListView definitionListView;
    QuizzDBHelper quizzDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadAndShowDefinitionList();

    }

    @Override
    public void onStop() {

        if (null != quizzDBHelper) {
            quizzDBHelper.close();
        }

        super.onStop();
    }

    @Override
    public void onResume() {

        super.onResume();

        loadAndShowDefinitionList();

    }


    private void loadAndShowDefinitionList() {
        setContentView(R.layout.activity_definition);

        // Erstellen der Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        definitionListView = (ListView) findViewById(R.id.definitionList);

        //QuizzDBHelper initialisieren
        quizzDBHelper = new QuizzDBHelper(this.getApplicationContext());
        quizzDBHelper.openDataBase();

        //Definitionen laden
        List<Definition> definitionList = quizzDBHelper.loadDefinitionList();

        DefinitionAdapter adapter = new DefinitionAdapter(this,0, definitionList);


        // Assign adapter to ListView
        definitionListView.setAdapter(adapter);

        // ListView Item Click Listener
        definitionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent newActivity = new Intent(parent.getContext(), DefinitionDetailActivity.class);
                Definition definition = (Definition) parent.getItemAtPosition(position);

                newActivity.putExtra("term", definition.getTerm());
                newActivity.putExtra("definitionText", definition.getDefinitionText());
                startActivity(newActivity, null);

            }
        });
        
    }

}




