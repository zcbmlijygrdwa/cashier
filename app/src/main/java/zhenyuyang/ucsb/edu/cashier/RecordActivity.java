package zhenyuyang.ucsb.edu.cashier;

import android.content.Context;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        activity = this;

        button_submit_new_item = (Button) findViewById(R.id.button_submit_new_item);

        button_submit_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String itemID = ((EditText)findViewById(R.id.editText_newID)).getText().toString();
                String itemName = ((EditText)findViewById(R.id.editText_newName)).getText().toString();
                String newPriceIn = ((EditText)findViewById(R.id.editText_newPriceIn)).getText().toString();
                String newPriceStandard = ((EditText)findViewById(R.id.editText_newPriceStandard)).getText().toString();

                if((!itemID.isEmpty()&&!itemName.isEmpty()&&!newPriceIn.isEmpty()&&!newPriceStandard.isEmpty()&&
                        (android.text.TextUtils.isDigitsOnly(newPriceIn))&&(android.text.TextUtils.isDigitsOnly(newPriceStandard)))&&
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
                beepManager.playBeepSoundAndVibrate();
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
        ((EditText)findViewById(R.id.editText_newID)).setText("");
        ((EditText)findViewById(R.id.editText_newName)).setText("");
        ((EditText)findViewById(R.id.editText_newPriceIn)).setText("");
        ((EditText)findViewById(R.id.editText_newPriceStandard)).setText("");
    }
}

