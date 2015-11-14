package com.whs.quizzappuni.Presenter;

import android.content.Intent;

import com.whs.quizzappuni.Activities.LearnMultiplechoiceActivity;
import com.whs.quizzappuni.Activities.ResultActivity;

/**
 * Created by Marc on 10.11.2015.
 */
public class ResultPresenter {

    private ResultActivity view;

    public ResultPresenter() {
    }


    public void learnmodeStarten() {
        Intent learnmode = new Intent(view, LearnMultiplechoiceActivity.class);
        view.startActivity(learnmode);
    }

    public void onTakeView(ResultActivity view) {
        this.view = view;
    }

    public void end() {
        view.finish();
    }

}
