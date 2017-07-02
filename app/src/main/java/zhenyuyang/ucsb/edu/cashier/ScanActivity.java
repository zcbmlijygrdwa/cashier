package zhenyuyang.ucsb.edu.cashier;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;

import java.util.List;

public class ScanActivity extends AppCompatActivity implements Client.onServerRespondedListener{
    private BarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastText;
    private Context context;
    private ScanActivity activity;
    private Client myClient;
    private int sellQuantity = 0;

    private TextView textView_pick_number;
    private TextView textView_response;
    private NumberPicker np;
    private BarcodeCallback callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        activity = this;
        textView_response = (TextView)findViewById(R.id.textView_response);
        textView_pick_number = (TextView)findViewById(R.id.textView_pick_number);
        np = (NumberPicker) findViewById(R.id.np);

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(15);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                textView_pick_number.setText("Quantity : " + newVal);
                sellQuantity = newVal;
            }
        });


        findViewById(R.id.button_manual_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myClient = new Client(SettingsManager.getInstance().getServerAddress(), SettingsManager.getInstance().getServerPort(),((EditText)findViewById(R.id.editText)).getText().toString());
                myClient.setOnServerRespondedListener(activity);
                myClient.execute();
            }
        });

        callback = new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if(result.getText() == null || result.getText().equals(lastText)) {
                    // Prevent duplicate scans
                    return;
                }
                lastText = result.getText();
                ((TextView)findViewById(R.id.code_info)).setText(result.getText());

                //query bar code from the server
                //String query  = "fetch:"+lastText;
                String query  = "sell:"+lastText+","+sellQuantity;
                myClient = new Client(SettingsManager.getInstance().getServerAddress(), SettingsManager.getInstance().getServerPort(),query);
                myClient.setOnServerRespondedListener(activity);
                myClient.execute();
            }
            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        };


        barcodeView = (BarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);
        beepManager = new BeepManager(this);
    }

    @Override
    public void onServerResponded(String response) {

        if(!response.equals("-1")) {
            textView_response.setText(response);
            beepManager.playBeepSoundAndVibrate();
        }
        else{
            textView_response.setText("Invalid request.");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
