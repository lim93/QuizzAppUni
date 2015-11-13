package com.whs.quizzappuni.Presenter;

import android.widget.ListView;

import com.whs.quizzappuni.Activities.ResultListActivity;
import com.whs.quizzappuni.Model.Round;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.ResultAdapter;
import com.whs.quizzappuni.Utils.UserDBHelper;

import java.io.IOException;
import java.util.List;

/**
 * Created by Marc on 10.11.2015.
 */
public class ResultListPresenter {

    private ResultListActivity view;
    //private Throwable error;

    ListView resultListView;

    UserDBHelper uHelper;

    public ResultListPresenter(){
    }

    public void createDB(){

        uHelper = new UserDBHelper(view.getApplicationContext());


        //UserDB erstellen und öffnen
        try {
            uHelper.createAndOpenDatabase();
        } catch (IOException ioe) {
            throw new Error("Unable to create UserDB");
        }

        resultListView = (ListView) view.findViewById(R.id.resultList);

        //QuizzDBHelper initialisieren
        uHelper = new UserDBHelper(view.getApplicationContext());
        uHelper.openDataBase();

        //Definitionen laden
        List<Round> resultList = uHelper.loadRoundList();

        ResultAdapter adapter = new ResultAdapter(view,0, resultList);


        // Assign adapter to ListView
        resultListView.setAdapter(adapter);
    }


    public void onTakeView(ResultListActivity view) {
        this.view = view;
    }

    public void end(){
        view.finish();
    }

}
