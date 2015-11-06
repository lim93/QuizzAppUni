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

        //Buttons setzen
        presenter.setLearnFab();
        presenter.setDBFab();
    }
}
