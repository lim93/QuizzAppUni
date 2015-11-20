package com.whs.quizzappuni.Presenter;

import android.content.Intent;
import android.os.Bundle;

import com.whs.quizzappuni.Activities.GameActivity;
import com.whs.quizzappuni.Activities.ResultActivity;
import com.whs.quizzappuni.Activities.MainActivity;

/**
 * Created by Marc on 10.11.2015.
 */
public class ResultPresenter {

    private ResultActivity view;

    public ResultPresenter() {
    }

    public void startLearnmode() {
        Bundle bundle = new Bundle();
        bundle.putString("mode", "learn");
        Intent game = new Intent(view, GameActivity.class);
        game.putExtras(bundle);
        view.startActivity(game);
    }

    public void startTimemode() {
        Bundle bundle = new Bundle();
        bundle.putString("mode", "time");
        Intent game = new Intent(view, GameActivity.class);
        game.putExtras(bundle);
        view.startActivity(game);
    }

    public void startMain() {
        Intent main = new Intent(view, MainActivity.class);
        view.startActivity(main);
    }

    public void onTakeView(ResultActivity view) {
        this.view = view;
    }

    public void end() {
        view.finish();
    }

}
