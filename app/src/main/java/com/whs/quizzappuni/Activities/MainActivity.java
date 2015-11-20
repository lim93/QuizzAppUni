package com.whs.quizzappuni.Activities;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.whs.quizzappuni.Presenter.MainPresenter;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.Utils;

public class MainActivity extends AppCompatActivity {

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


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(R.string.exitAlert_Title);

        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.exitAlert_Content)
                .setCancelable(true)
                .setPositiveButton(R.string.exitAlert_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton(R.string.exitAlert_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
