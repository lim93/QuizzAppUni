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
    QuizzDBHelper qHelper;
    //TODO:RundenlÃ¤nge dynamisch laden
    private int roundLength = 5;
    private int thisRound = 0;
    Round round = new Round();
    //private Throwable error;
    //QuizzDBHelper qHelper = new QuizzDBHelper(view.getApplicationContext());
    //UserDBHelper uHelper = new UserDBHelper(view.getApplicationContext());



    public LearnmodePresenter(){

    }

    public void onTakeView(LearnMultiplechoice view) {
        this.view = view;
    }

    public void openDB(){
        qHelper = new QuizzDBHelper(view.getApplicationContext());
        qHelper.openDataBase();
    }

    public void loadQuestion(){
        openDB();
        List<ToggleButton> buttons = new ArrayList<ToggleButton>();
        buttons.add(view.Antwort1);
        buttons.add(view.Antwort2);
        buttons.add(view.Antwort3);
        buttons.add(view.Antwort4);
        List<Question> randomQuestions = qHelper.getRandomQuestions(1);

        if(thisRound < roundLength) {
            for (Question question : randomQuestions) {
                view.question.setText(question.getQuestionText());
                QuestionAnswer[] questionAnswers = question.getAnswers();

                for (int i = 0; i < questionAnswers.length; i++) {
                    QuestionAnswer answer = questionAnswers[i];

                    buttons.get(i).setText(answer.getAnswerText());
                    buttons.get(i).setTextOn(answer.getAnswerText());
                    buttons.get(i).setTextOff(answer.getAnswerText());

                }
            }
        }
        else {
            //TODO: In DB schreiben

            //TODO:Anstatt zu beenden eine Auswertung anzeigen
            this.end();
        }
    }

    //Auf die BestÃ¤tigung nach der Antwort-Auswahl reagieren
    public void confirmChoice(){
        thisRound++;
        //Die View-Anzeige und View-Elemente neu laden, um Anzeigeprobleme zu beheben
        if(thisRound < roundLength) {
            view.setContentView(R.layout.activity_learn_multiplechoice);
            view.loadElements();
        }

        //TODO: Antwort auswerten + in Runde setzen

        loadQuestion();
    }

    public void check(){

    }

    public void end(){
        view.finish();
    }


}
