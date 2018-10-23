package ww.www.www.login_with_pin_code;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Locale;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class LaunchScreen extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "package ww.www.www.login_with_pin_code"; //az érték következő activitynek való átadásához kell (jelen esetben a lokalizációhoz)
    private Button Button_Select_Language_Proceed;
    private RadioButton Radio_Button_English, Radio_Button_Hungarian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        init();
    }

    /* Ez a megoldás a radio button választás után azonnal tovább lépett (nem volt submit gomb, így ha véletlenül rossz nyelvet választottunk ki, akkor is tovább lépett
        public String wichLanguageSelected(View view){
        boolean isChecked = ((RadioButton) view).isChecked();
        String selectedLanguage = "";
        switch (view.getId()){
            case R.id.Radio_Button_English:
                if (isChecked){
                    selectedLanguage = "English";
                }
                break;
            case R.id.Radio_Button_Hungarian:
                if (isChecked){
                    selectedLanguage = "Hungarian";
                }
        }
        return selectedLanguage;
    }*/

    public void init(){
        Button_Select_Language_Proceed = (Button) findViewById(R.id.Button_Select_Language_Proceed);
        Radio_Button_English = (RadioButton) findViewById(R.id.Radio_Button_English);
        Radio_Button_Hungarian = (RadioButton) findViewById(R.id.Radio_Button_Hungarian);

        setTitle(R.string.app_label);
    }

    public void whichLanguageSelected(View view){ // Ez a metódus a Proceed gomb lenyomására meghívódik (ld: activity_launch_screen.xml
        String selectedLanguage = "";
        if (Radio_Button_English.isChecked()){
            selectedLanguage = "en";
        }
        else if (Radio_Button_Hungarian.isChecked()){
            selectedLanguage = "hu";
        }

        Locale locale = new Locale(selectedLanguage);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        /* Nem váltja át a nyelvet
        Locale setLocaleTo = new Locale("hu");
        Configuration app_config = new Configuration();
        app_config.setLocale(setLocaleTo);*/

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, selectedLanguage); // nem is kell átadni a lokalizáció választást, meg lehet oldani a kezdőlapon is. Ettől függetlenül itt hagyom, hátha jó lesz még valamire.
        startActivity(intent);
    }
}
