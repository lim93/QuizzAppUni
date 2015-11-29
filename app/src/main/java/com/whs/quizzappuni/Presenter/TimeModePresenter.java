package com.whs.quizzappuni.Presenter;

import android.os.CountDownTimer;

import java.util.concurrent.TimeUnit;

/**
 * Created by krispin on 20.11.15.
 */
public class TimeModePresenter extends GamePresenter {
    private long maxTime;
    public long actTime;

    @Override
    public void setRoundInformations(){
        //10 Sekunden , 1 Sekunde (in Millisekunden)
        final Counter timer = new Counter(10000, 1000);

    }

    @Override
    public void setMaxRoundInformation(){
        int maximalTime = (int) maxTime;
        view.progressBar.setMax(maximalTime);
    }

     public class Counter extends CountDownTimer{

        public Counter(long maxTime, long countDownInterval){
            super(maxTime, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished){

            long millis = millisUntilFinished;
            actTime = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
            int actualTime = (int) actTime;
            view.progressBar.setProgress(actualTime);
        }

        @Override
        public void onFinish(){
        //TODO: Diese einzelne Fragenrunde beenden

        }

    }

}
