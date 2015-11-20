package com.whs.quizzappuni.Utils;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewTreeObserver;

import com.whs.quizzappuni.Model.QuestionAnswer;

import java.util.Random;

/**
 * Created by marc on 12.11.15.
 */
public class Utils {

    public static void showFabWithAnimation(final FloatingActionButton fab, final int delay) {
        fab.setVisibility(View.INVISIBLE);
        fab.setScaleX(0.0F);
        fab.setScaleY(0.0F);
        fab.setAlpha(0.0F);
        fab.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fab.getViewTreeObserver().removeOnPreDrawListener(this);
                fab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fab.show();
                    }
                }, delay);
                return true;
            }
        });
    }


    public static void shuffleQuestionAnswers(QuestionAnswer[] questionAnswers) {
        Random rnd = new Random();
        for (int i = questionAnswers.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            QuestionAnswer questionAnswer = questionAnswers[index];
            questionAnswers[index] = questionAnswers[i];
            questionAnswers[i] = questionAnswer;
        }
    }
}
