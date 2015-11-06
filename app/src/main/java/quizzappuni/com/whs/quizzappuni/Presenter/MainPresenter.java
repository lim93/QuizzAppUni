package quizzappuni.com.whs.quizzappuni.Presenter;

import android.content.Intent;

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

    public void learnmodeStarten(){
        Intent learnmode = new Intent(view, LearnMultiplechoice.class);
        view.startActivity(learnmode);
    }

    public void dBmodeStarten(){
        Intent db = new Intent(view, DB.class);
        view.startActivity(db);
    }

    public void onTakeView(MainActivity view) {
        this.view = view;
    }

    public void end(){
        view.finish();
    }

}
