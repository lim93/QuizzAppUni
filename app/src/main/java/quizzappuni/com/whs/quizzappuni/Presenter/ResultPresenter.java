package quizzappuni.com.whs.quizzappuni.Presenter;

import android.content.Intent;

import java.io.IOException;

import quizzappuni.com.whs.quizzappuni.Activities.LearnMultiplechoiceActivity;
import quizzappuni.com.whs.quizzappuni.Activities.ResultActivity;
import quizzappuni.com.whs.quizzappuni.Utils.QuizzDBHelper;
import quizzappuni.com.whs.quizzappuni.Utils.UserDBHelper;

/**
 * Created by Marc on 10.11.2015.
 */
public class ResultPresenter {

    private ResultActivity view;
    //private Throwable error;

    QuizzDBHelper qHelper;
    UserDBHelper uHelper;

    public ResultPresenter(){
    }

    public void createDB(){

        qHelper = new QuizzDBHelper(view.getApplicationContext());
        uHelper = new UserDBHelper(view.getApplicationContext());

        //Todo: In activity_main ausführen
        //QuizzDB erstellen und öffnen
        try {
            qHelper.createAndOpenDatabase();
        } catch (IOException ioe) {
            throw new Error("Unable to create QuizzDB");
        }

        //UserDB erstellen und öffnen
        try {
            uHelper.createAndOpenDatabase();
        } catch (IOException ioe) {
            throw new Error("Unable to create UserDB");
        }
    }

    public void learnmodeStarten(){
        Intent learnmode = new Intent(view, LearnMultiplechoiceActivity.class);
        view.startActivity(learnmode);
    }

    public void onTakeView(ResultActivity view) {
        this.view = view;
    }

    public void end(){
        view.finish();
    }

}
