package quizzappuni.com.whs.quizzappuni.Presenter;

import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    QuizzDBHelper qHelper;


    public LearnmodePresenter(){
    }

    public void onTakeView(LearnMultiplechoice view) {
        this.view = view;
    }

    /**public void openDb(){

     */
    public void openDB(){
        qHelper = new QuizzDBHelper(view.getApplicationContext());
        qHelper.openDataBase();
    }

    public void starteRunde(){
        openDB();
        Round round = new Round();
        List<Question> randomQuestions = qHelper.getRandomQuestions(1);

        List<ToggleButton> buttons = new ArrayList<ToggleButton>();
        buttons.add(view.Antwort1);
        buttons.add(view.Antwort2);
        buttons.add(view.Antwort3);
        buttons.add(view.Antwort4);

        for (Question question:randomQuestions){
            view.question.setText(question.getQuestionText());
            QuestionAnswer[] questionAnswers = question.getAnswers();

            for (int i = 0; i<questionAnswers.length;i++) {
                QuestionAnswer answer = questionAnswers[i];

                buttons.get(i).setText(answer.getAnswerText());
                buttons.get(i).setTextOn(answer.getAnswerText());
                buttons.get(i).setTextOff(answer.getAnswerText());

            }

            //TODO: Auf UserInput warten und Antwort auswerten + in Runde setzen
        }

        //TODO: In DB schreiben





    }



    public void check(){

    }

    public void end(){
        view.finish();
    }


}
