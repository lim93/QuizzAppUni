package quizzappuni.com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import quizzappuni.com.whs.quizzappuni.quizzappuni.R;
import quizzappuni.com.whs.quizzappuni.Presenter.MainPresenter;

public class MainActivity extends Activity {

    public FloatingActionButton fab;
    public FloatingActionButton dbButton;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sicherstellen, dass ein Presenter existiert
        if (presenter == null)
            presenter = new MainPresenter();
        presenter.onTakeView(this);

        //fab-Button initialisieren
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.learnmodeStarten();
            }
        });

        //db-Button initialisieren
        dbButton = (FloatingActionButton) findViewById(R.id.dbButton);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.dBmodeStarten();
            }
        });
    }
}
