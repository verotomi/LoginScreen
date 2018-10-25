package ww.www.www.login_with_pin_code;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import java.util.Locale;

public class LaunchScreen extends AppCompatActivity {

    public static final String sentParameters = "package ww.www.www.login_with_pin_code"; //érték következő activitynek való átadásához kell ez a prefix. Értéke az aktuális apckage: ld: 1.sor!
    private Button Button_Select_Language_Proceed;
    private RadioButton Radio_Button_English, Radio_Button_Hungarian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
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
        setTitle(R.string.app_label); //App címke átírása/frissítése (lokalizáció miatt kell ide is, nem csak az AndroidManifest-be)
    }

    public void whichLanguageSelected(View view){ // Ez a metódus a Proceed gomb lenyomására meghívódik (ld: activity_launch_screen.xml)
        String selectedLanguage = "";
        if (Radio_Button_English.isChecked()){
            selectedLanguage = "en";
        }
        else if (Radio_Button_Hungarian.isChecked()){
            selectedLanguage = "hu";
        }

        // Lokalizáció - Applikációnként kell beállítani, nem pedig Activity-nként. Viszont csak egy új Activity elindításakor lép életbe (Többek között újra be kell tölteni a resource/string értékeket.)
        Locale setLocaleTo = new Locale(selectedLanguage);
        Locale.setDefault(setLocaleTo);
        Configuration config = getBaseContext().getResources().getConfiguration(); // kiolvassuk a jelenlegi configot
        config.locale = setLocaleTo; // a kiolvasott configban módosítjuk a lokalizációt
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics()); // módosítások mentése

        // Új Activity
        Intent intent = new Intent(this, MainActivity.class); // új Activity példányosítása
        intent.putExtra(sentParameters, selectedLanguage); // kulcs-érték páros átadása. Jelen esetben nincs rá szükség, meg lehet oldani a kezdőlapon is. Ettől függetlenül itt hagyom, hátha jó lesz még valamire.
        startActivity(intent); // Új Activity elindítása
    }
}
