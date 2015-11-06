package quizzappuni.com.whs.quizzappuni.Presenter;

import java.io.IOException;

import quizzappuni.com.whs.quizzappuni.Model.Question;
import quizzappuni.com.whs.quizzappuni.Model.QuestionAnswer;
import quizzappuni.com.whs.quizzappuni.Model.Round;
import quizzappuni.com.whs.quizzappuni.Model.RoundQuestion;
import quizzappuni.com.whs.quizzappuni.Utils.QuizzDBHelper;
import quizzappuni.com.whs.quizzappuni.Utils.UserDBHelper;
import quizzappuni.com.whs.quizzappuni.quizzappuni.R;
import quizzappuni.com.whs.quizzappuni.Activities.LearnMultiplechoice;

/**
 * Created by M on 31.10.2015.
 */
public class LearnmodePresenter {

    private LearnMultiplechoice view;
    //private Throwable error;
    //QuizzDBHelper qHelper = new QuizzDBHelper(view.getApplicationContext());
    //UserDBHelper uHelper = new UserDBHelper(view.getApplicationContext());


    public LearnmodePresenter(){
    }

    public void onTakeView(LearnMultiplechoice view) {
        this.view = view;
    }

    /**public void openDb(){

     */

    public void check(){

    }

    public void end(){
        view.finish();
    }


}
