package mce.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Conso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conso);

        Intent intent_calc_conso = getIntent();
//        int temp = intent_calc_conso.getIntExtra("int_value",0);
//        String var = String.valueOf(temp);
//        Toast.makeText(getApplicationContext(),var,Toast.LENGTH_LONG).show();
        String co2 = intent_calc_conso.getExtras().getString("co2","0");
        String banquise = intent_calc_conso.getExtras().getString("banquise","0");
        String consoWh = intent_calc_conso.getExtras().getString("consoWh","0");

        final TextView co2_txt = findViewById(R.id.nb_eco_CO2);
        final TextView banquise_txt = findViewById(R.id.nb_banquise);
        final TextView consoWh_txt = findViewById(R.id.nb_Wh);
        final ImageButton btn_back = findViewById(R.id.btn_back_conso);


        co2_txt.setText(co2);
        banquise_txt.setText(banquise);
        consoWh_txt.setText(consoWh);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Conso.this.finish();
            }
        });


    }
}
