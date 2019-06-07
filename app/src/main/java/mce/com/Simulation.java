package mce.com;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Simulation extends AppCompatActivity {

    ImageButton btn_back;
    SwitchCompat chaufJ,chaufN,RefJ,RefN;
    String address = null;
    TextView lumn, textView;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket m_btSocket = null;
    private boolean isbtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private InputStream m_inputStream;
    private OutputStream m_outputStream;
    boolean stopThread;
    final int handlerState = 0;                        //used to identify handler message
    //HandlerConnect bluetoothInHandler;
    byte[] buffer;
    //private ConnectedThread mConnectedThread;
    private StringBuilder recDataString = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        address = this.getIntent().getStringExtra(Device_list_Scenario.EXTRA_ADDRESS);
        setContentView(R.layout.activity_simulation);
        btn_back = findViewById(R.id.btn_back_scenario);
        chaufJ = findViewById(R.id.switch_scen_chauJ);
        chaufN = findViewById(R.id.switch_scen_chauN);
        RefJ = findViewById(R.id.switch_scen_RefJ);
        RefN = findViewById(R.id.switch_scen_RefN);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("STOP");
                Disconnect();
            }
        });
        chaufJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("ChaufferJour");
                if(RefJ.isChecked()){
                    RefJ.setChecked(false);
                }
                if(RefN.isChecked()){
                    RefN.setChecked(false);
                }
                if(chaufN.isChecked()){
                    chaufN.setChecked(false);
                }
            }
        });
        chaufN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("ChaufferNuit");
                if(RefN.isChecked()){
                    RefN.setChecked(false);
                }
                if(RefJ.isChecked()){
                    RefJ.setChecked(false);
                }
                if(chaufJ.isChecked()){
                    chaufJ.setChecked(false);
                }
            }
        });
        RefJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("RefroidirJour");
                if(chaufJ.isChecked()){
                    chaufJ.setChecked(false);
                }
                if(RefN.isChecked()){
                    RefN.setChecked(false);
                }
                if(chaufN.isChecked()){
                    chaufN.setChecked(false);
                }

            }
        });
        RefN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("RefroidirNuit");
                if(chaufN.isChecked()){
                    chaufN.setChecked(false);
                }
                if(RefJ.isChecked()){
                    RefJ.setChecked(false);
                }
                if(chaufJ.isChecked()){
                    chaufJ.setChecked(false);
                }
            }
        });

        new ConnectBT().execute();
    }

    private void sendSignal ( String number ) {
        if ( m_btSocket != null ) {
            try {
                m_btSocket.getOutputStream().write(number.toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void Disconnect () {
        if ( m_btSocket!=null ) {
            try {
                m_btSocket.close();
            } catch(IOException e) {
                msg("Error");
            }
        }

        finish();
    }

    private void msg (String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


    /**
     * Use the UI Thread to perform tasks in background
     */
    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected  void onPreExecute () {
            progress = ProgressDialog.show(Simulation.this, "Connecting...", "Please Wait!!!");
        }



        @Override
        protected Void doInBackground (Void... devices) {
            try {
                if ( m_btSocket==null || !isbtConnected ) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice device = myBluetooth.getRemoteDevice(address);
                    m_btSocket = device.createRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    m_btSocket.connect();
                    // m_inputStream = m_btSocket.getInputStream();


                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected");
                isbtConnected = true;

            }

            progress.dismiss();
        }
    }
}
