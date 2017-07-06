package zhenyuyang.ucsb.edu.cashier;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;

import java.util.List;

import pl.polak.clicknumberpicker.ClickNumberPickerListener;
import pl.polak.clicknumberpicker.ClickNumberPickerView;
import pl.polak.clicknumberpicker.PickerClickType;


public class ScanActivity extends AppCompatActivity implements Client.onServerRespondedListener{
    private BarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastText;
    private Context context;
    private ScanActivity activity;
    private Client myClient;
    private int sellQuantity = 0;
    private float totalPrice = 0;

    private TextView textView_pick_number;
    private EditText textView_response;
    private TextView textView_response_item_price_in;
    private TextView textView_response_item_price_standard;
    private TextView textView_item_name;
    private TextView textView_response_total_price;
    private ClickNumberPickerView clickNumberPickerView_sell_price;
    private ClickNumberPickerView clickNumberPickerView_sell_quantity;

    private boolean ifNewDetection = true;


    private NumberPicker np;
    private BarcodeCallback callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        activity = this;
        textView_response = (EditText)findViewById(R.id.textView_response);
        textView_response_item_price_in = (TextView)findViewById(R.id.textView_response_item_price_in);
        textView_response_item_price_standard = (TextView)findViewById(R.id.textView_response_item_price_standard);
        textView_item_name = (TextView)findViewById(R.id.textView_item_name);
        textView_response_total_price = (TextView)findViewById(R.id.textView_response_total_price);
        //textView_pick_number = (TextView)findViewById(R.id.textView_pick_number);



        clickNumberPickerView_sell_quantity = (ClickNumberPickerView)findViewById(R.id.clickNumberPickerView_sell_quantity);

        clickNumberPickerView_sell_price = (ClickNumberPickerView)findViewById(R.id.clickNumberPickerView_sell_price);


//        //Populate NumberPicker values from minimum and maximum value range
//        //Set the minimum value of NumberPicker
//        np.setMinValue(1);
//        //Specify the maximum value/number of NumberPicker
//        np.setMaxValue(15);
//
//        np.setValue(5);
//
//        //Gets whether the selector wheel wraps when reaching the min/max value.
//        np.setWrapSelectorWheel(true);
//
//        //Set a value change listener for NumberPicker
//        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
//                //Display the newly selected number from picker
//                textView_pick_number.setText("Quantity : " + newVal);
//                sellQuantity = newVal;
//            }
//        });


        findViewById(R.id.button_manual_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Item item = ItemManager.getInstance().findItemById(textView_response.getText().toString(),getApplicationContext());
                if(item!=null) {
                    TransactionManager.getInstance().createTransaction(item.getID(), (int) clickNumberPickerView_sell_quantity.getValue(), clickNumberPickerView_sell_price.getValue(), getApplicationContext());

                    beepManager.playBeepSoundAndVibrate();
                    Toast.makeText(getApplicationContext(),(int) clickNumberPickerView_sell_quantity.getValue()+" of "+item.getName()+" checked out!", Toast.LENGTH_SHORT).show();
                    totalPrice+=(int) clickNumberPickerView_sell_quantity.getValue()*clickNumberPickerView_sell_price.getValue();
                    textView_response_total_price.setText(totalPrice+"");
                    restoreUI();
                    ifNewDetection = true;
                }
                else{
                    Toast.makeText(getApplicationContext(),"Check-out failed, cannot find the item in database", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.button_refresh_barcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = ItemManager.getInstance().findItemById(textView_response.getText().toString(),getApplicationContext());
                if(item!=null) {
                textView_response_item_price_in.setText(item.getPriceIn()+"");
                textView_response_item_price_standard.setText(item.getPriceStandard()+"");
                clickNumberPickerView_sell_price.setPickerValue(item.getPriceStandard());
                textView_item_name.setText(item.getName()+"");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Check-out failed, cannot find the item in database", Toast.LENGTH_SHORT).show();
                }
            }
        });



        callback = new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                beepManager.playBeepSoundAndVibrate();
                if(result.getText() == null || (!ifNewDetection&&result.getText().equals(lastText))) {
                    // Prevent duplicate scans
                    return;
                }
                ifNewDetection = false;
                lastText = result.getText();
                ((TextView)findViewById(R.id.textView_response)).setText(result.getText());


                Item item = ItemManager.getInstance().findItemById(textView_response.getText().toString(),getApplicationContext());
                if(item!=null) {
                    textView_response_item_price_in.setText(item.getPriceIn()+"");
                    textView_response_item_price_standard.setText(item.getPriceStandard()+"");
                    clickNumberPickerView_sell_price.setPickerValue(item.getPriceStandard());
                    clickNumberPickerView_sell_quantity.setPickerValue(1.0f);
                    textView_item_name.setText(item.getName()+"");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Check-out failed, cannot find the item in database", Toast.LENGTH_SHORT).show();
                }
//                //query bar code from the server
//                //String query  = "fetch:"+lastText;
//                String query  = "sell:"+lastText+","+sellQuantity;
//                myClient = new Client(SettingsManager.getInstance().getServerAddress(), SettingsManager.getInstance().getServerPort(),query);
//                myClient.setOnServerRespondedListener(activity);
//                myClient.execute();
            }
            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        };


        barcodeView = (BarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);
        beepManager = new BeepManager(this);
        restoreUI();

        //fakeDetection();
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

    public void fakeDetection(){
        textView_response.setText("11226");
        Item item = ItemManager.getInstance().findItemById("11226",this);
        textView_response_item_price_in.setText(item.getPriceIn()+"");
        textView_response_item_price_standard.setText(item.getPriceStandard()+"");
        clickNumberPickerView_sell_price.setPickerValue(item.getPriceStandard());
        textView_item_name.setText(item.getName()+"");
    }

    public void restoreUI(){
        textView_response.setText("???????");
        textView_response_item_price_in.setText("Price In");
        textView_response_item_price_standard.setText("Price Standard");
        clickNumberPickerView_sell_price.setPickerValue(0.0f);
        clickNumberPickerView_sell_quantity.setPickerValue(1.0f);
        textView_item_name.setText("???????");
    }
}
