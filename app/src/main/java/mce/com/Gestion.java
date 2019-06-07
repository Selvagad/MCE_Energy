package mce.com;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Gestion extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btnDis;
    ImageButton btn_back;
    SwitchCompat lum1,lum2,lum3,lum4,lum5,shu1,shu2,shu3,shu4,shu5,shu6,heat1,heat2,heat3,heat4,heat5,vmc1,vmc2;
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

        //Intent newint = getIntent();
        address = this.getIntent().getStringExtra(DeviceList.EXTRA_ADDRESS);
        setContentView(R.layout.activity_gestion);



        //Create btConnect Task to establish BT Connection and setup BT Socket
        new ConnectBT().execute();

        lum1 = findViewById(R.id.switch_lum_cuisine);
        lum2= findViewById(R.id.switch_lum_salon);
        lum3= findViewById(R.id.switch_lum_ch1);
        lum4= findViewById(R.id.switch_lum_ch2);
        lum5= findViewById(R.id.switch_lum_sdb);
        shu1= findViewById(R.id.switch_volet_cuisine1);
        shu2= findViewById(R.id.switch_volet_cuisine2);
        shu3= findViewById(R.id.switch_volet_salon1);
        shu4= findViewById(R.id.switch_volet_salon2);
        shu5= findViewById(R.id.switch_volet_ch1);
        shu6= findViewById(R.id.switch_volet_ch2);
        heat1= findViewById(R.id.switch_chauf_cuisine);
        heat2= findViewById(R.id.switch_chauf_salon);
        heat3= findViewById(R.id.switch_chauf_ch1);
        heat4= findViewById(R.id.switch_chauf_ch2);
        heat5= findViewById(R.id.switch_chauf_sdb);
        vmc1= findViewById(R.id.switch_vmc_couloir);
        vmc2= findViewById(R.id.switch_vmc_serre);
        btn_back = findViewById(R.id.btn_back_gestion);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("STOP");
                Disconnect();
            }
        });


        lum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetLum1");
            }
        });

        lum2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetLum2");
            }
        });

        lum3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetLum3");
            }
        });

        lum4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetLum4");
            }
        });

        lum5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetLum5");
            }
        });

        shu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetShu1");
            }
        });

        shu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetShu2");
            }
        });

        shu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetShu3");
            }
        });

        shu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetShu4");
            }
        });
        shu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetShu5");
            }
        });

        shu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetShu6");
            }
        });

        heat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetHeat1");
            }
        });

        heat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetHeat2");
            }
        });

        heat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetHeat3");
            }
        });

        heat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetHeat4");
            }
        });

        heat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetHeat5");
            }
        });

        vmc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetVmc1");
            }
        });

        vmc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("SetVmc2");
            }
        });


//        try {
//            m_inputStream = m_btSocket.getInputStream();
//        }catch (IOException e){
//            msg("InputStream not available");
//        }

//        mConnectedThread = new ConnectedThread(m_btSocket); //verifier si le m_btSocket est bien set
//        mConnectedThread.start();





        /**
         * Handler for the Connection thread
         */
//        bluetoothInHandler = new HandlerConnect();
//        bluetoothInHandler.handleMessage(recDataString,textView);

//            public void handleMessage(android.os.Message msg) {
//                    String readMessage = (String) msg.obj;                             // msg.arg1 = bytes from connect thread
//                    recDataString.append(readMessage);
//                        textView.setText("Data Received = " + recDataString);
//                        recDataString.delete(0, recDataString.length());                    //clear all string data
//                }
//        };
    }

//    static class HandlerConnect extends Handler{
//        public void handleMessage(StringBuilder recDataVie, TextView txtview) {
//
//            android.os.Message msg = this.obtainMessage();
//            String readMessage = (String) msg.obj.toString();                             // msg.arg1 = bytes from connect thread
//            recDataVie.append(readMessage);
//            txtview.setText("Data Received = " + recDataVie.toString());
//            recDataVie.delete(0, recDataVie.length());                    //clear all string data
//
//        }
//
//    }


//    //create new class for connect thread
//    private class ConnectedThread extends Thread {
//        private final InputStream m_InStream;
//        private final OutputStream m_OutStream;
//
//        /**
//         * Constructor of the connect thread
//         */
//
//        public ConnectedThread(BluetoothSocket socket) {
//            InputStream tmpIn = null;
//            OutputStream tmpOut = null;
//
//            try {
//                //Create I/O streams for connection
//                tmpIn = socket.getInputStream();
//                tmpOut = socket.getOutputStream();
//            } catch (IOException e) { }
//
//            m_InStream = tmpIn;
//            m_OutStream = tmpOut;
//        }
//
//        public void run() {
//            byte[] buffer = new byte[256];
//            int bytes;
//
//            // Keep looping to listen for received messages
//            while (true) {
//                try {
//                    bytes = m_InStream.read(buffer);            //read bytes from input buffer
//                    String readMessage = new String(buffer, 0, bytes);
//                    // Send the obtained bytes to the UI Activity via handler
//                    bluetoothInHandler.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
//                } catch (IOException e) {
//                    break;
//                }
//            }
//        }
//        //write method
//        public void write(String input) {
//            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
//            try {
//                m_OutStream.write(msgBuffer);                //write bytes over BT connection via outstream
//            } catch (IOException e) {
//                //if you cannot write, close the application
//                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
//                finish();
//
//            }
//        }
//    }


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
            progress = ProgressDialog.show(Gestion.this, "Connecting...", "Please Wait!!!");
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