package com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whs.quizzappuni.Presenter.ResultPresenter;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.Utils;

public class ResultActivity extends AppCompatActivity {

    public FloatingActionButton fab;
    private ResultPresenter presenter;
    private TextView points;
    private TextView time;
    private LinearLayout container_time;
    private String gamemode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Erstellen der Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);



        //Sicherstellen, dass ein Presenter existiert
        if (presenter == null)
            presenter = new ResultPresenter();
        presenter.onTakeView(this);

        //Lade übergebene Werte
        Bundle bundle = getIntent().getExtras();
        //Befülle die Endergebnis-Anzeige dieser Runde mit den übergebenen Punktwerten aus der Runde
        gamemode = bundle.getString("mode");
        points = (TextView) findViewById(R.id.result_points);
        points.setText(String.format("%d %s", bundle.getInt("points"), getResources().getString(R.string.points)));

        container_time = (LinearLayout) findViewById(R.id.container_time);
        time = (TextView) findViewById(R.id.time);

        if (bundle.getLong("seconds") == 0) {
            container_time.setVisibility(View.GONE);
        } else {
            container_time.setVisibility(View.VISIBLE);
            time.setText(String.format("%d %s", bundle.getLong("seconds"), getResources().getString(R.string.seconds)));
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gamemode.equals("learn"))
                    presenter.startLearnmode();
                if (gamemode.equals("time"))
                    presenter.startTimemode();
            }
        });
        Utils.showFabWithAnimation(fab, 500);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        presenter.startMain();
    }
}

