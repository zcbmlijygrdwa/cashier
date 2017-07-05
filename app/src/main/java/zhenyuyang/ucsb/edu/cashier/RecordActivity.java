package zhenyuyang.ucsb.edu.cashier;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;

import java.util.List;

public class RecordActivity extends AppCompatActivity implements Client.onServerRespondedListener{
    private BarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastText;
    private Context context;
    private RecordActivity activity;
    private Client myClient;

    private TextView textView_response;
    private Button button_submit_new_item;

    private BarcodeCallback callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        activity = this;
        textView_response = (TextView)findViewById(R.id.textView_response);

        button_submit_new_item = (Button) findViewById(R.id.button_submit_new_item);

        button_submit_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //query bar code from the server
//                String itemID = ((EditText)findViewById(R.id.editText_newID)).getText().toString();
//                String itemName = ((EditText)findViewById(R.id.editText_newName)).getText().toString();
//                String itemPrice = ((EditText)findViewById(R.id.editText_newPrice)).getText().toString();
//                String query  = "add:"+itemID+","+itemName+","+itemPrice;
//                myClient = new Client(SettingsManager.getInstance().getServerAddress(), SettingsManager.getInstance().getServerPort(),query);
//                myClient.setOnServerRespondedListener(activity);
//                myClient.execute();
//                button_submit_new_item.setEnabled(false);
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
                ((EditText)findViewById(R.id.editText_newID)).setText(result.getText());

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
        }
        else{
           textView_response.setText("A new item creation failed: Duplicated item");
        }
        beepManager.playBeepSoundAndVibrate();
        button_submit_new_item.setEnabled(true);
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

