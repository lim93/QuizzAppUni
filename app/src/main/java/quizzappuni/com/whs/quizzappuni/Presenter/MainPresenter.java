package quizzappuni.com.whs.quizzappuni.Presenter;

import android.content.Intent;

import java.io.IOException;

import quizzappuni.com.whs.quizzappuni.Activities.DB;
import quizzappuni.com.whs.quizzappuni.Activities.DefinitionListActivity;
import quizzappuni.com.whs.quizzappuni.Activities.LearnMultiplechoiceActivity;
import quizzappuni.com.whs.quizzappuni.Activities.MainActivity;
import quizzappuni.com.whs.quizzappuni.Activities.ResultListActivity;
import quizzappuni.com.whs.quizzappuni.Utils.QuizzDBHelper;
import quizzappuni.com.whs.quizzappuni.Utils.UserDBHelper;

/**
 * Created by M on 31.10.2015.
 */
public class MainPresenter {

    private MainActivity view;
    //private Throwable error;

    QuizzDBHelper qHelper;
    UserDBHelper uHelper;

    public MainPresenter(){
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

    public void dBmodeStarten(){
        Intent db = new Intent(view, DB.class);
        view.startActivity(db);
    }

    public void resultListStarten(){
        Intent resultList = new Intent(view, ResultListActivity.class);
        view.startActivity(resultList);
    }

    public void definitionListStarten(){
        Intent definitionList = new Intent(view, DefinitionListActivity.class);
        view.startActivity(definitionList);
    }


    public void onTakeView(MainActivity view) {
        this.view = view;
    }

    public void end(){
        view.finish();
    }

}
