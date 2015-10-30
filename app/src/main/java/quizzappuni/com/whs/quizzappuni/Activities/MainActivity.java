package quizzappuni.com.whs.quizzappuni.Activities;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;


import quizzappuni.com.whs.quizzappuni.quizzappuni.R;

public class MainActivity extends Activity {

    int modeRequest;
    int roundResultRequest;
    String mode;
    String roundResult1;

    //Startet beim Start der App automatisch die Start-Activity, damit der Modus ausgewählt werden kann
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //explicit Intent call
        Intent start = new Intent(MainActivity.this, Start.class);
        startActivityForResult(start, modeRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Abfangen der Modusauswahl-Ergebnisse
        if (resultCode == RESULT_OK && requestCode == modeRequest && data.hasExtra("modeChoice")) {
            mode = data.getExtras().getString("modeChoice");

            //Modusauswahl: Lernmodus wurde ausgewählt
            if (mode.equals("LearnMultipleChoice")) {
                lernmodus();
            }

            //Modusauswahl: DB-Aktivität
            if (mode.equals("DB")) {
                Intent db = new Intent(MainActivity.this, DB.class);
                startActivityForResult(db, roundResultRequest);
            }
        }

        //Abfangen der Ergebnisse eines Modus
        if (resultCode == RESULT_OK && requestCode == roundResultRequest && data.hasExtra("result")) {
            //Hier: alle Ergebnisse aus einem Bundle annehmen
            //Dann in die DB wegschreiben
            roundResult1 = data.getExtras().getString("result");
            //einfügen: mach irgendwas mit dem Ergebnis

        }

        if (resultCode == RESULT_CANCELED){
            Intent start = new Intent(MainActivity.this, Start.class);
            startActivityForResult(start, modeRequest);
        }


    }

    public void lernmodus(){
        Intent learn = new Intent(MainActivity.this, LearnMultiplechoice.class);
        learn.putExtra("Antwort1", "Hier steht Antwort1");
        startActivityForResult(learn, roundResultRequest);
    }
}
