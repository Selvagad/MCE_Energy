package mce.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class DeviceList extends AppCompatActivity {

    Button btn_pair;
    ListView deviceList;

    private BluetoothAdapter bluetooth_adapt = null;
    private Set<BluetoothDevice> paired_devices;
    public static String EXTRA_ADDRESS = "device_adress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_pair = (Button) findViewById(R.id.btn_pair);
        deviceList = (ListView) findViewById(R.id.list_device);

        /** Active BT connexion **/
        bluetooth_adapt = BluetoothAdapter.getDefaultAdapter();
        if(bluetooth_adapt == null) {
            Toast.makeText(getApplicationContext(), "Veuillez activer le bluetooth", Toast.LENGTH_LONG).show();
            finish();
        }else if(!bluetooth_adapt.isEnabled()){
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }
        pairedDeviceList();
        btn_pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pairedDeviceList();
            }
        });
    }

    /** Get Paired devices to the phone **/
    private void pairedDeviceList(){
        paired_devices = bluetooth_adapt.getBondedDevices();
        ArrayList list = new ArrayList();

        if(paired_devices.size()>0){
            for (BluetoothDevice bt : paired_devices){
                list.add(bt.getName().toString() + "\n" + bt.getAddress().toString());

            }
        }else{
            Toast.makeText(getApplicationContext(), "Pas d'appareil bluetooth trouvé", Toast.LENGTH_LONG).show();
        }

        /** Putting the content of list into device_item (TextView) of lisrow.xml **/
        deviceList.setAdapter(new ArrayAdapter<String>(this, R.layout.lisrow,R.id.device_item, list));

        // Click on an address to connect to it
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String info = deviceList.getItemAtPosition(i).toString();
                //get the address (6*2 char + 5 points)
                String address = info.substring(info.length()-17);

                Intent intent = new Intent(DeviceList.this, LedControl.class);
                intent.putExtra(EXTRA_ADDRESS, address);
                startActivity(intent);

            }
        });
    }


}
