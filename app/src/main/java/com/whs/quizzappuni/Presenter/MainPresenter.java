package com.whs.quizzappuni.Presenter;

import android.content.Intent;

import com.whs.quizzappuni.Activities.DB;
import com.whs.quizzappuni.Activities.DefinitionListActivity;
import com.whs.quizzappuni.Activities.LearnMultiplechoiceActivity;
import com.whs.quizzappuni.Activities.MainActivity;
import com.whs.quizzappuni.Activities.ResultListActivity;
import com.whs.quizzappuni.Utils.QuizzDBHelper;
import com.whs.quizzappuni.Utils.UserDBHelper;

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

    public void createDBs(){

        qHelper = new QuizzDBHelper(view.getApplicationContext());
        uHelper = new UserDBHelper(view.getApplicationContext());

        //QuizzDB erstellen (Bei jedem Start der App)
        qHelper.createDatabase();

        //UserDB erstellen (einmalig)
        uHelper.createDatabase();

        //TODO: Hier eventuell die Exception abfangen und dem User anzeigen, dass es ein generelles Problem mit der App gibt
    }

    public void learnmodeStarten(){
        Intent learnmode = new Intent(view, LearnMultiplechoiceActivity.class);
        view.startActivity(learnmode);
    }

    public void dBmodeStarten(){
        //TODO: Löschen wenn nicht mehr benötigt
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
