package mce.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CalculConso extends AppCompatActivity {

    //public static int NUMBER_CONSO = 0;
    //private int val_conso;

    public double val_surface = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul_conso);


        final Button btn_calc = (Button) findViewById(R.id.btn_calc_conso);
        final ImageButton btn_back = findViewById(R.id.btn_back_calc_conso);
        final EditText surfaceNbPicker = (EditText) findViewById(R.id.numberPick_surface);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculConso.this.finish();
            }
        });

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String var = surfaceNbPicker.getText().toString();
                 val_surface = Double.parseDouble(var);

                    MyResult res = calculConsommation();
                    String co2_evite = res.getFirst();
                    String banquise = res.getSecond();
                    String consoWh = res.getThird();
//                    // Toast.makeText(getApplicationContext(),"CO2 : "+ co2_evite+"kg",Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),"banquise : "+ banquise,Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),"Conso : "+ consoWh,Toast.LENGTH_SHORT).show();
////
                    Intent i = new Intent(CalculConso.this, Conso.class);
                    Bundle extras = new Bundle();
                    extras.putString("co2", co2_evite);
                    extras.putString("banquise", banquise);
                    extras.putString("consoWh", consoWh);
                    i.putExtras(extras);
                    startActivity(i);


            }
        });
    }


    public MyResult calculConsommation(){

    final CheckBox led = (CheckBox) findViewById(R.id.p_led);
    final CheckBox ventil = (CheckBox) findViewById(R.id.p_ventil);
    final CheckBox temp_eau = (CheckBox) findViewById(R.id.p_temp_eau);
    final CheckBox veille = (CheckBox) findViewById(R.id.p_veille);
    final CheckBox isole = (CheckBox) findViewById(R.id.p_isole);
    final CheckBox seche = (CheckBox) findViewById(R.id.p_seche);
    final CheckBox aere = (CheckBox) findViewById(R.id.p_aere);
    final CheckBox ecoA = (CheckBox) findViewById(R.id.p_ecoA);

        double surface = val_surface;
        double nrj_eco = 0.0 ,nrj_eco_A =0.0 ,nrj_eco_F = 0.0, kWhpm2=0.0;
        double coef=0.0;

        if (led.isChecked()){
            nrj_eco_A = nrj_eco_A + 1197.0;
            nrj_eco_F = nrj_eco_F + 1197.0;
        }

        if (ventil.isChecked()){
            nrj_eco_A = nrj_eco_A + 476.0;
            nrj_eco_F = nrj_eco_F + 2127.0;
        }

        if (temp_eau.isChecked()){
            nrj_eco_A = nrj_eco_A + 789.0;
            nrj_eco_F = nrj_eco_F + 789.0;
        }

        if (veille.isChecked()){
            nrj_eco_A = nrj_eco_F + 81.0;
            nrj_eco_F = nrj_eco_F + 548.0;
        }

        if(isole.isChecked())
            kWhpm2 = 50.0;
	    else
	        kWhpm2 = 350.0;

        if(seche.isChecked()){
            nrj_eco_A = nrj_eco_A + 192.0;
            nrj_eco_F = nrj_eco_F + 288.0;
        }

        if(aere.isChecked())
            coef = 1.0;
        else
            coef = 1.1;

        if(ecoA.isChecked())
            nrj_eco = nrj_eco_A + 5148.0;
	    else
	        nrj_eco = nrj_eco_F;

        nrj_eco = nrj_eco + (surface*350.0*1.1 - surface*kWhpm2*coef);

        double co2_evite = nrj_eco *2.58*65.0/1000.0;
        double banquise_sauvee = (co2_evite*1000*0.000001*3.0);
        DecimalFormat dec = new DecimalFormat("#0.00");

        return new MyResult(dec.format(co2_evite),dec.format(banquise_sauvee),dec.format(nrj_eco));

    }

    public class MyResult {
        private String first;
        private String second;
        private String third;

        public MyResult(String first, String second, String third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }

        public String getThird() {
            return third;
        }
    }

}
