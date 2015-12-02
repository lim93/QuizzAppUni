package com.whs.quizzappuni.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.whs.quizzappuni.Presenter.GamePresenter;
import com.whs.quizzappuni.Presenter.LearnModePresenter;
import com.whs.quizzappuni.Presenter.TimeModePresenter;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.Utils;

public class GameActivity extends AppCompatActivity {

    private GamePresenter presenter;
    public ToggleButton Antwort1;
    public ToggleButton Antwort2;
    public ToggleButton Antwort3;
    public ToggleButton Antwort4;
    public TextView question;
    public TextView round_status;
    public TextView points;
    public ProgressBar progressBar;
    public CardView statusCard;
    public TextView playMode;
    public FloatingActionButton fabSend;
    public boolean fabSendAlreadyClicked = false;
    public boolean answerButtonClicked = false;
    public boolean timerIsFinished = false;
    public boolean fabSendStartsResultPage = false;
    public int answer;
    public String gamemode;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Laden der Informationen aus der Modusauswahl
        Bundle bundle = getIntent().getExtras();
        gamemode = bundle.getString("mode");

        //Den richtigen Presenter anhand des übergebenen Moduswertes erstellen
        if (gamemode.equals("learn"))
            presenter = new LearnModePresenter();
        if (gamemode.equals("time"))
            presenter = new TimeModePresenter();
        presenter.onTakeView(this);

        this.loadElements();
        presenter.loadQuestion();

    }

    public void loadElements() {
        //Erstellen der Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Antwort-Buttons initialisieren
        Antwort1 = (ToggleButton) findViewById(R.id.Antwort1);
        Antwort2 = (ToggleButton) findViewById(R.id.Antwort2);
        Antwort3 = (ToggleButton) findViewById(R.id.Antwort3);
        Antwort4 = (ToggleButton) findViewById(R.id.Antwort4);
        question = (TextView) findViewById(R.id.question);

        //Listener fuer Button Aenderung.
        Antwort1.setOnCheckedChangeListener(changeChecker);
        Antwort2.setOnCheckedChangeListener(changeChecker);
        Antwort3.setOnCheckedChangeListener(changeChecker);
        Antwort4.setOnCheckedChangeListener(changeChecker);

        statusCard = (CardView) findViewById(R.id.statusCard);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        round_status = (TextView) findViewById(R.id.round_status);
        points = (TextView) findViewById(R.id.points);

        playMode = (TextView) findViewById(R.id.play_mode);

        fabSend = (FloatingActionButton) findViewById(R.id.fabSend);
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabSendStartsResultPage){
                    presenter.resultPageStarten();
                }
                else {
                    fabSend.hide();
                    //Verhindern, dass bei schneller mehrfacher Betägigung des Buttons hintereinander, Fragen "übersprungen" werden
                    if (!fabSendAlreadyClicked) {
                        presenter.confirmChoice();
                    }
                    fabSendAlreadyClicked = true;
                    answerButtonClicked = true;
                }
            }
        });

        fabSend.hide();
    }



    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

                    //      Nur ein Button kann selected sein.
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        //überprüfen, ob schon ein Antwort-Button gedrückt wurde; Dieser Wert wird aktuell nur im Zeitmodus geändert, da hier direkt nach drücken eines Buttons das Ergebnis gezeigt wird
                        if(!answerButtonClicked)
                        {
                            if (timerIsFinished) {
                                Antwort1.setChecked(false);
                                Antwort2.setChecked(false);
                                Antwort3.setChecked(false);
                                Antwort4.setChecked(false);
                            }
                            else {
                                if (isChecked) {
                                    if (buttonView == Antwort1) {
                                        Antwort2.setChecked(false);
                                        Antwort3.setChecked(false);
                                        Antwort4.setChecked(false);
                                        answer = 0;
                                    }
                                    if (buttonView == Antwort2) {
                                        Antwort1.setChecked(false);
                                        Antwort3.setChecked(false);
                                        Antwort4.setChecked(false);
                                        answer = 1;
                                    }
                                    if (buttonView == Antwort3) {
                                        Antwort2.setChecked(false);
                                        Antwort1.setChecked(false);
                                        Antwort4.setChecked(false);
                                        answer = 2;
                                    }
                                    if (buttonView == Antwort4) {
                                        Antwort2.setChecked(false);
                                        Antwort3.setChecked(false);
                                        Antwort1.setChecked(false);
                                        answer = 3;
                                    }

                                    //FAB an
                                    presenter.doWhenAnswerIsChoosed();
                                } else {
                                    //FAB aus
                                    if (!Antwort1.isChecked() && !Antwort2.isChecked() && !Antwort3.isChecked() && !Antwort4.isChecked()) {
                                        fabSend.hide();
                                    }
                                }
                            }

                        }
                        else
                        {
                            //Verhindern, dass sich der Anzeigestatus der toggleButtons verändert, wenn schon eine Auswahl getroffen wurde
                                if (answer == 0) {
                                    Antwort1.setChecked(true);
                                    Antwort2.setChecked(false);
                                    Antwort3.setChecked(false);
                                    Antwort4.setChecked(false);
                                } if (answer == 1) {
                                    Antwort1.setChecked(false);
                                    Antwort2.setChecked(true);
                                    Antwort3.setChecked(false);
                                    Antwort4.setChecked(false);
                                } if (answer == 2) {
                                    Antwort1.setChecked(false);
                                    Antwort2.setChecked(false);
                                    Antwort3.setChecked(true);
                                    Antwort4.setChecked(false);
                                } if (answer == 3) {
                                    Antwort1.setChecked(false);
                                    Antwort2.setChecked(false);
                                    Antwort3.setChecked(false);
                                    Antwort4.setChecked(true);
                                }
                        }
                    }
    };

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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(R.string.quitAlert_Title);

        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.quitAlert_Content)
                .setCancelable(true)
                .setPositiveButton(R.string.quitAlert_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        GameActivity.this.finish();
                        presenter.MainStarten();
                }})
                .setNegativeButton(R.string.quitAlert_no, new DialogInterface.OnClickListener() {
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