package com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.whs.quizzappuni.Presenter.ResultPresenter;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.Utils;

public class ResultActivity extends AppCompatActivity {

    public FloatingActionButton fab;
    private ResultPresenter presenter;
    private TextView points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Sicherstellen, dass ein Presenter existiert
        if (presenter == null)
            presenter = new ResultPresenter();
        presenter.onTakeView(this);

        //Lade übergebene Werte
        Bundle bundle = getIntent().getExtras();
        //Befülle die Endergebnis-Anzeige dieser Runde mit den übergebenen Punktwerten aus der Runde
        points = (TextView) findViewById(R.id.result_points);
        points.setText(String.format("%d Punkte", bundle.getInt("points")));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.learnmodeStarten();
            }
        });
        Utils.showFabWithAnimation(fab, 500);

    }
}

