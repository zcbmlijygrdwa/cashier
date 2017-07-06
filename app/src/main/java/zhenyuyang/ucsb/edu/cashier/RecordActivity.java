package zhenyuyang.ucsb.edu.cashier;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private Button button_submit_new_item;

    private BarcodeCallback callback;

    private EditText editText_newID;
    private EditText editText_newName;
    private EditText editText_newPriceIn;
    private EditText editText_newPriceStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        activity = this;


        editText_newID = (EditText)findViewById(R.id.editText_newID);
        editText_newName = (EditText)findViewById(R.id.editText_newName);
        editText_newPriceIn = (EditText)findViewById(R.id.editText_newPriceIn);
        editText_newPriceStandard = (EditText)findViewById(R.id.editText_newPriceStandard);

        editText_newPriceIn.setRawInputType(Configuration.KEYBOARD_12KEY);
        editText_newPriceStandard.setRawInputType(Configuration.KEYBOARD_12KEY);

        button_submit_new_item = (Button) findViewById(R.id.button_submit_new_item);

        button_submit_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String itemID = editText_newID.getText().toString();
                String itemName = editText_newName.getText().toString();
                String newPriceIn = editText_newPriceIn.getText().toString();
                String newPriceStandard = editText_newPriceStandard.getText().toString();

                if((!itemID.isEmpty()&&!itemName.isEmpty()&&!newPriceIn.isEmpty()&&!newPriceStandard.isEmpty()&&
                        (isNumber(newPriceIn))&&(isNumber(newPriceStandard)))&&
                    (itemID.trim().length()>0)&&(itemName.trim().length()>0)&&(newPriceIn.trim().length()>0)&&(newPriceStandard.trim().length()>0)){
                    String query = itemID + "," + itemName + "," + newPriceIn + "," + newPriceStandard;
                    ItemManager.getInstance().createItem(query + "\n", getApplicationContext());
                    beepManager.playBeepSoundAndVibrate();
                    restoreUI();
                }
                else {

                    Toast.makeText(getApplicationContext(),"Please fill out all fields correctly.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        callback = new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

                if(result.getText() == null || result.getText().equals(lastText)) {
                    // Prevent duplicate scans
                    return;
                }
                beepManager.playBeepSoundAndVibrate();
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

        restoreUI();
    }
    @Override
    public void onServerResponded(String response) {

//        if(!response.equals("-1")) {
//            textView_response.setText(response);
//        }
//        else{
//           textView_response.setText("A new item creation failed: Duplicated item");
//        }
////        beepManager.playBeepSoundAndVibrate();
//        button_submit_new_item.setEnabled(true);
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

    public void restoreUI(){
        editText_newID.setText("");
        editText_newName.setText("");
        editText_newPriceIn.setText("");
        editText_newPriceStandard.setText("");
    }

    boolean isNumber(String s){
        if(s.matches("\\d+(?:\\.\\d+)?"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

