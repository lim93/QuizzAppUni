package com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.whs.quizzappuni.Presenter.ResultListPresenter;
import com.whs.quizzappuni.R;

/**
 * Created by krispin on 07.11.15.
 */
public class ResultListActivity extends AppCompatActivity {


    private ResultListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Sicherstellen, dass ein Presenter existiert
        if (presenter == null)
            presenter = new ResultListPresenter();
        presenter.onTakeView(this);

        //Datenbank erstellen
        presenter.createDB();


    }

}




