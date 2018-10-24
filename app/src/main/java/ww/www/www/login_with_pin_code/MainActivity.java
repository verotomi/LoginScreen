package ww.www.www.login_with_pin_code;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button Button_1,Button_2,Button_3,Button_4,Button_5,Button_6,Button_7,Button_8,Button_9,Button_0, Button_back, Button_enter, Button_logout;
    private TextView Text_View_Enter_PIN_Code;
    private TextView Text_View_Stars;
    private TextView Text_View_Dialogue;
    private String enteredPincode="", stars="";
    final String storedPincode="123456";
    boolean loginState = false;
    boolean banned = false;
    private int wrongAttempts = 0;

    public void init(){
        Button_1 = (Button) findViewById(R.id.Button_1);
        Button_2 = (Button) findViewById(R.id.Button_2);
        Button_3 = (Button) findViewById(R.id.Button_3);
        Button_4 = (Button) findViewById(R.id.Button_4);
        Button_5 = (Button) findViewById(R.id.Button_5);
        Button_6 = (Button) findViewById(R.id.Button_6);
        Button_7 = (Button) findViewById(R.id.Button_7);
        Button_8 = (Button) findViewById(R.id.Button_8);
        Button_9 = (Button) findViewById(R.id.Button_9);
        Button_0 = (Button) findViewById(R.id.Button_0);
        Button_back = (Button) findViewById(R.id.Button_back);
        Button_enter = (Button) findViewById(R.id.Button_enter);
        Button_logout = (Button) findViewById(R.id.Button_logout);
        Text_View_Enter_PIN_Code = (TextView) findViewById(R.id.Text_View_Enter_PIN_Code); //töröltem
        Text_View_Stars = (TextView) findViewById(R.id.Text_View_Stars);
        Text_View_Dialogue = (TextView) findViewById(R.id.Text_View_Dialogue);

        setTitle(R.string.app_label);
    }

    public void banned() {
        final int waitingTime = getResources().getInteger(R.integer.timeToWait);
        banned = true;

        new CountDownTimer(waitingTime*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                Text_View_Dialogue.setText(getString(R.string.text_wrong_pin_code) + " " + getString(R.string.text_banned_1) + (millisUntilFinished / 1000) + " " + getString(R.string.text_banned_2));
            }
            public void onFinish() {
                Text_View_Dialogue.setText(R.string.text_not_logged_in);
                banned = false;
            }
        }.start();

        // Text_View_Dialogue.setText(R.string.text_banned_1 + R.string.text_banned_2); // leállt hibaüzenettel
        // Text_View_Dialogue.setText(R.string.text_banned_1 + "" + R.string.text_banned_2); // ez már nem állt le hibaüzenettel, de számokat írt ki.
        // Text_View_Dialogue.setText(R.string.text_banned_1.toString() + "" + R.string.text_banned_2); // itt már kaptam fordítás előtti hibaüzenetet, amivel el tudtam indulni
        // Text_View_Dialogue.setText(getString(R.string.text_banned_1) + getString(R.string.text_banned_2)); // végül rájöttem, hogy ez a megoldás

        /* ebben a metódusban nem működött a Text_View_Dialogue szöveg-módosítása. Nem volt fordítási hiba, de futás közben leállt a program, érdemleges hibaüzenet nélkül.
            Úgy sikerült megoldanom, hogy elé raktam a "MainActivity."-t így: MainActivity.Text_View_Dialogue.setText("..."). Így már alá lettt húzva pirossal, Alt+Enterrel
            static-ká tettem a Text_View_Dialogue-t. Ezek után az elejéről elhagytam a "MainActivity."-t. Úgy véltem, felesleges oda.
            Viszont nem igazán értem, hogy miért kellett static-ra állítani?
            UPDATE!!! Erre a fenti staticos dologra nincs szükség, enélkül is megy. Nem töröltem a megjegyzésemet, hátra jó lesz még valamire */

        /* ez sem működik, itt sincs visszaszámlálás :(
        for (int i = 0; i < waitingTime; i++) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after x seconds
                    Text_View_Dialogue.setText(getString(R.string.text_wrong_pin_code) + " " + getString(R.string.text_banned_1) + (waitingTime) + " " + getString(R.string.text_banned_2));
                }
            }, 1000);
            Text_View_Dialogue.setText(getString(R.string.text_wrong_pin_code) + " " + getString(R.string.text_banned_1) + (waitingTime-i) + " " + getString(R.string.text_banned_2));
        }*/

        /* ez sem működik, kiírja, hogy x sec tiltás van, de valójában nincs tiltás :(
        for (int i = 0; i < waitingTime; i++) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Text_View_Dialogue.setText(getString(R.string.text_wrong_pin_code) + " " + getString(R.string.text_banned_1) + (waitingTime) + " " + getString(R.string.text_banned_2));
                }
            }, 1000);
        }*/

        /* ez a verzió nem működött. Kiírta ugyan az üzenetet, de csak a beállított idő leteltével. Addig le volt fagyva az UI
        try {
            for (int i = 0; i < waitingTime; i++){
            Thread.sleep(1000);
                Text_View_Dialogue.setText(getString(R.string.text_wrong_pin_code) + " " + getString(R.string.text_banned_1) + (waitingTime-i) + " " + getString(R.string.text_banned_2));
                // ezeket a " "-ket sajnos nem tudom átrakni a values->strings.xml-be. Ha ott rakok be a szöveg elejére egy üres space-t, nem lesz figyelembe véve
                // érdekes módon a szöveg végén figyelembe van véve.
            }
        }
        catch (InterruptedException ie){
            Thread.currentThread().interrupt();
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String selectedLanguage = intent.getStringExtra(LaunchScreen.sentParameters); // végül nem is kell átadni a lokalizáció választást, meg tudom oldani a kezdőlapon is. Ettől függetlenül itt hagyom, hátha jó lesz még valamire.
        init();

        // Toast.makeText(this, "Test: pincode variable's length is:" + enteredPincode.length(), Toast.LENGTH_SHORT).show(); // Teszthez kellett!! Csak a változót nem tudtam Toast-olni, elé kellett írni valami stringet!

        Button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "1";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "2";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "3";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "4";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "5";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "6";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "7";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "8";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "9";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() < 6 && !banned) {
                    enteredPincode += "0";
                    stars += "*";
                    Text_View_Stars.setText(stars);
                    Text_View_Dialogue.setText(R.string.text_not_logged_in);
                }
            }
        });
        Button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState == false && enteredPincode.length() > 0 && !banned) {
                    enteredPincode = enteredPincode.substring(0, enteredPincode.length() - 1);
                    stars = stars.substring(0, stars.length() - 1);
                    Text_View_Stars.setText(stars);
                }
            }
        });
        Button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!banned) {
                    if (enteredPincode.length() >= 4) { // 6--nál nagyobb nem lehet, ld: számgombok
                        if (enteredPincode.equals(storedPincode)) { // itt eredetileg ==-t írtam, de azzal nem működött:(
                            Text_View_Dialogue.setText(getString(R.string.text_logged_in));
                            loginState = true;
                            stars = "";
                            enteredPincode = "";
                            Text_View_Stars.setText(stars);
                            Text_View_Enter_PIN_Code.setVisibility(View.INVISIBLE);
                            Button_logout.setVisibility(View.VISIBLE);
                        } else {
                            Text_View_Dialogue.setText(getString(R.string.text_wrong_pin_code));
                            wrongAttempts++;
                            if (wrongAttempts == getResources().getInteger(R.integer.maxWrongAttempts)) {
                                banned();
                                wrongAttempts = 0;
                                // Text_View_Dialogue.setText(R.string.text_not_logged_in); // átraktam a banned metódusba!
                            }
                            stars = "";
                            enteredPincode = "";
                            Text_View_Stars.setText(stars);
                        }
                    } else {
                        if (loginState == false) {
                            Text_View_Dialogue.setText(getString(R.string.text_pin_code_rule));
                        }
                        stars = "";
                        enteredPincode = "";
                        Text_View_Stars.setText(stars);
                    }
                }
            }
        });
        Button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginState) {
                    loginState = false;
                    Text_View_Enter_PIN_Code.setVisibility(View.VISIBLE);
                    Text_View_Dialogue.setText(getString(R.string.text_not_logged_in));
                    Button_logout.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}

// meg lehetne oldani a 0...9 gombokat generikus kóddal?
// az enter gombnál az == nem működik, csak az equals!0
// megcsinálni: "You are logged in" helyett : "Logging in", majd egy új képernyő jelenjen meg (Új activity induljon el??)
// hogyan lehet átalakítani a gombokat kerekre? oválisra meg tudom csinálni, de kerekre nem
// árnyékokat szeretnék rakni a gombokon lévő betűk alá
// a LOG OUT gombot kisbetűsíteni szeretném
// LOG OUT gombot ki szeretném szélesíteni a számgombok széléig
// a lenyomott gomboknak árnyéka van. Miért?
// ha visszalépek a kezdőképernyőre, a megfelelő checkbox legyen beállítva
// kommentelni az utasításokat, hogy mi mit csinál és miért