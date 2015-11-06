package quizzappuni.com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import quizzappuni.com.whs.quizzappuni.Presenter.LearnmodePresenter;
import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class LearnMultiplechoice extends AppCompatActivity {

    private LearnmodePresenter presenter;
    public FloatingActionButton fabSend;
    public ToggleButton Antwort1;
    public ToggleButton Antwort2;
    public ToggleButton Antwort3;
    public ToggleButton Antwort4;
    public TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_multiplechoice);

        //Sicherstellen, dass ein Presenter existiert
        if (presenter == null)
            presenter = new LearnmodePresenter();
        presenter.onTakeView(this);

        // Erstellen der Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fabSend = (FloatingActionButton) findViewById(R.id.fabSend);
        fabSend.setVisibility(View.GONE);

        //Antwort-Buttons initialisieren
        Antwort1 = (ToggleButton) findViewById(R.id.Antwort1);
        Antwort2 = (ToggleButton) findViewById(R.id.Antwort2);
        Antwort3 = (ToggleButton) findViewById(R.id.Antwort3);
        Antwort4 = (ToggleButton) findViewById(R.id.Antwort4);
        question = (TextView) findViewById(R.id.question);

        presenter.starteRunde();

//        Listener für Button Änderung.
        Antwort1.setOnCheckedChangeListener(changeChecker);
        Antwort2.setOnCheckedChangeListener(changeChecker);
        Antwort3.setOnCheckedChangeListener(changeChecker);
        Antwort4.setOnCheckedChangeListener(changeChecker);

    }

    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

//      Nur ein Button kann selected sein.
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                if (buttonView == Antwort1) {
                    Antwort2.setChecked(false);
                    Antwort3.setChecked(false);
                    Antwort4.setChecked(false);
                }
                if (buttonView == Antwort2) {
                    Antwort1.setChecked(false);
                    Antwort3.setChecked(false);
                    Antwort4.setChecked(false);
                }
                if (buttonView == Antwort3) {
                    Antwort2.setChecked(false);
                    Antwort1.setChecked(false);
                    Antwort4.setChecked(false);
                }
                if (buttonView == Antwort4) {
                    Antwort2.setChecked(false);
                    Antwort3.setChecked(false);
                    Antwort1.setChecked(false);
                }

//                FAB an
                fabSend.setVisibility(View.VISIBLE);

            }   else {
//                FAB aus
                fabSend.setVisibility(View.GONE);
            }
        }
    };
}


