package quizzappuni.com.whs.quizzappuni.Presenter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import quizzappuni.com.whs.quizzappuni.quizzappuni.R;
import quizzappuni.com.whs.quizzappuni.Activities.LearnMultiplechoice;
import quizzappuni.com.whs.quizzappuni.Activities.DB;
import quizzappuni.com.whs.quizzappuni.Activities.MainActivity;

/**
 * Created by M on 31.10.2015.
 */
public class MainPresenter {

    private MainActivity view;
    //private Throwable error;

    public MainPresenter(){
    }


    public void setLearnFab(){
        view.fab = (FloatingActionButton) view.findViewById(R.id.fab);
        view.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent learnmode = new Intent(view, LearnMultiplechoice.class);
                view.startActivity(learnmode);
            }
        });
    }

    public void setDBFab(){
        view.dbButton = (FloatingActionButton) view.findViewById(R.id.dbButton);
        view.dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent db = new Intent(view, DB.class);
                view.startActivity(db);
            }
        });
    }

    public void onTakeView(MainActivity view) {
        this.view = view;
    }

    public void end(){
        view.finish();
    }


}
