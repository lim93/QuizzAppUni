package quizzappuni.com.whs.quizzappuni.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import quizzappuni.com.whs.quizzappuni.Model.Definition;
import quizzappuni.com.whs.quizzappuni.Utils.QuizzDBHelper;
import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

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
    /*

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Intent newActivity = new Intent(parent.getContext(), DefinitionDetailActivity.class);
            //newActivity.putExtra("username","bharath");
            startActivity(newActivity,null);

        }
    };


    private ListView definitionListView;
    private QuizzDBHelper quizzDBHelper;
    public CardView definitionCard;


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

        definitionCard = (CardView) findViewById(R.id.definitionCard);


        //QuizzDBHelper initialisieren
        quizzDBHelper = new QuizzDBHelper(this.getApplicationContext());
        quizzDBHelper.openDataBase();

        //Definitionen laden
        List<Definition> definitionList = quizzDBHelper.loadDefinitionList();

        String[] values = new String[definitionList.size()];

        for (int i = 0; i < definitionList.size(); i++) {
            values[i] = definitionList.get(i).getTerm();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        definitionListView.setAdapter(adapter);

        // ListView Item Click Listener
        definitionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                // ListView Clicked item index
                int itemPosition = position;

                Definition definition = quizzDBHelper.loadDefinitionById(position+1);


                // Show Alert
                Toast.makeText(getApplicationContext(),
                        definition.getDefinitionText(), Toast.LENGTH_LONG)
                        .show();

            }

        });
    }
    */
}




