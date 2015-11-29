package com.whs.quizzappuni.Presenter;

import android.content.Intent;
import android.widget.ListView;

import com.whs.quizzappuni.Activities.MainActivity;
import com.whs.quizzappuni.Activities.ResultListActivity;
import com.whs.quizzappuni.Model.Round;
import com.whs.quizzappuni.R;
import com.whs.quizzappuni.Utils.ResultAdapter;
import com.whs.quizzappuni.Utils.UserDBHelper;

import java.util.List;

/**
 * Created by Marc on 10.11.2015.
 */
public class ResultListPresenter {

    private ResultListActivity view;
    private ListView resultListView;
    private UserDBHelper uHelper;

    public ResultListPresenter(){


    }

    public void displayResults(){

        //UserDBHelper initialisieren
        uHelper = new UserDBHelper(view.getApplicationContext());

        //Definitionen laden
        List<Round> resultList = uHelper.loadRoundList();

        // Adapter erstellen und dem ListView zuweisen
        ResultAdapter adapter = new ResultAdapter(view,0, resultList);
        resultListView = (ListView) view.findViewById(R.id.resultList);
        resultListView.setAdapter(adapter);
    }

    public void mainStarten(){
        Intent main = new Intent(view, MainActivity.class);
        view.startActivity(main);
    }


    public void onTakeView(ResultListActivity view) {
        this.view = view;
    }

    public void end(){
        view.finish();
    }

}
