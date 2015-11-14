package com.whs.quizzappuni.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageButton;

import com.whs.quizzappuni.Presenter.MainPresenter;
import com.whs.quizzappuni.Utils.Utils;
import com.whs.quizzappuni.R;

public class MainActivity extends Activity {

    public FloatingActionButton fab;
    public ImageButton dbButton;
    public ImageButton definitionButton;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sicherstellen, dass ein Presenter existiert
        if (presenter == null)
            presenter = new MainPresenter();
        presenter.onTakeView(this);

        //Datenbanken erstellen
        presenter.createDBs();

        //fab-Button initialisieren
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.learnmodeStarten();
            }
        });


        //db-Button initialisieren
        dbButton = (ImageButton) findViewById(R.id.dbButton);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.resultListStarten();
            }
        });

        //definition-Button initialisieren
        definitionButton = (ImageButton) findViewById(R.id.definitionButton);
        definitionButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    presenter.definitionListStarten();
                                                }
                                            }


        );
    }

    protected void onResume() {
        super.onResume();
        Utils.showFabWithAnimation(fab, 500);
    }
}
