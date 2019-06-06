package mce.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    private String address = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.address = this.getIntent().getStringExtra(DeviceList.EXTRA_ADDRESS);

        final LinearLayout gestion = (LinearLayout) findViewById(R.id.layout_management);
        final LinearLayout simu = (LinearLayout) findViewById(R.id.layout_simu);
        final LinearLayout conso = (LinearLayout) findViewById(R.id.layout_conso);

        gestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),address,Toast.LENGTH_LONG).show();
               Intent i = new Intent(getApplicationContext(),DeviceList.class);
               startActivity(i);
            }
        });

        simu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(getApplicationContext(),Gestion.class);
              //  startActivity(i);
            }
        });

        conso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CalculConso.class);
                startActivity(i);
                //Menu.this.finish();
            }
        });
    }
}
