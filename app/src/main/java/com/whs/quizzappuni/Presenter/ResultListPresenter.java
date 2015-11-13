package com.whs.quizzappuni.Presenter;

import java.io.IOException;

import com.whs.quizzappuni.Activities.ResultListActivity;
import com.whs.quizzappuni.Utils.QuizzDBHelper;
import com.whs.quizzappuni.Utils.UserDBHelper;

/**
 * Created by Marc on 10.11.2015.
 */
public class ResultListPresenter {

    private ResultListActivity view;
    //private Throwable error;

    QuizzDBHelper qHelper;
    UserDBHelper uHelper;

    public ResultListPresenter(){
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


    public void onTakeView(ResultListActivity view) {
        this.view = view;
    }

    public void end(){
        view.finish();
    }

}
