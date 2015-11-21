package com.whs.quizzappuni.Activities;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.whs.quizzappuni.Presenter.MainPresenter;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.Utils;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton fab;
    public ImageButton dbButton;
    public ImageButton definitionButton;
    private MainPresenter presenter;
    final Context context = this;
    public ImageButton backButton;
    public ImageButton forwardButton;
    public TextView playModeTitle;
    public TextView playModeDescription;
    public CardView statusCard;
    public boolean timeMode = false;
    public View view;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

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
                if(timeMode) {
                    presenter.timemodeStarten();
                } else {
                    presenter.learnmodeStarten();
                }
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

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(timeMode) {
                        showLearnMode();
                    } else {
                        showTimeMode();
                    }
                }
        });

        forwardButton = (ImageButton) findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeMode) {
                    showLearnMode();
                } else {
                    showTimeMode();
                }
            }
        });

        playModeTitle = (TextView) findViewById(R.id.play_mode);
        playModeDescription = (TextView) findViewById(R.id.play_mode_description);



        statusCard = (CardView) findViewById(R.id.status_card);

        final GestureDetectorCompat gestureDetector = new GestureDetectorCompat(context,
                new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    boolean result = false;
                    try {
                        if(timeMode) {
                            showLearnMode();
                        } else {
                            showTimeMode();
                        }
                        result = true;

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    return result;
                }

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                });

        statusCard.setOnTouchListener(new View.OnTouchListener() {
        @Override
            public boolean onTouch(View view, MotionEvent event) {
                gestureDetector.onTouchEvent(event);  // here we pass events to detector above
                return true;
            }
        });
    }

    protected void showTimeMode() {
        playModeTitle.setText(R.string.time_mode);
        playModeDescription.setText(R.string.time_mode_description);

        fab.hide();

        fab.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_timer_white_24dp));
                    }
                }, 300);
        Utils.showFabWithAnimation(fab, 500);

        timeMode = true;
    }

    protected void showLearnMode() {
        playModeTitle.setText(R.string.learning_mode);
        playModeDescription.setText(R.string.learning_mode_description);

        fab.hide();

        fab.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play_arrow_white_24dp));
                    }
                }, 300);
        Utils.showFabWithAnimation(fab, 500);

        timeMode = false;
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
