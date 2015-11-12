package quizzappuni.com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import quizzappuni.com.whs.quizzappuni.Presenter.ResultPresenter;
import quizzappuni.com.whs.quizzappuni.Utils.Utils;
import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class ResultActivity extends AppCompatActivity {

    public FloatingActionButton fab;
    private ResultPresenter presenter;


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

        //Datenbank erstellen
        presenter.createDB();

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

