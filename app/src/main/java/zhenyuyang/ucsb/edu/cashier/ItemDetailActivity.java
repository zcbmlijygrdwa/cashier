package zhenyuyang.ucsb.edu.cashier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView textView_ID;
    private EditText editText_Name;
    private EditText editText_PriceIn;
    private EditText editText_PriceStandard;
    private Button button_ok;
    private Button button_item_delete;

    private Item item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        textView_ID = (TextView)findViewById(R.id.textView_ID);
        editText_Name = (EditText)findViewById(R.id.editText_Name);
        editText_PriceIn = (EditText)findViewById(R.id.editText_PriceIn);
        editText_PriceStandard = (EditText)findViewById(R.id.editText_PriceStandard);

        button_ok = (Button)findViewById(R.id.button_ok);
        button_item_delete = (Button)findViewById(R.id.button_item_delete);

        String itemID = getIntent().getStringExtra("item_ID");
        item = ItemManager.getInstance().findItemById(itemID,this);

        textView_ID.setText(item.getID());
        editText_Name.setText(item.getName());
        editText_PriceIn.setText(item.getPriceIn()+"");
        editText_PriceStandard.setText(item.getPriceStandard()+"");


        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item tempI = new Item(item.getID(),
                        editText_Name.getText().toString(),
                        Float.parseFloat(editText_PriceIn.getText().toString()),
                        (int)Float.parseFloat(editText_PriceStandard.getText().toString()));
                ItemManager.getInstance().setItem(tempI,getApplicationContext());
                finish();
            }
        });

        button_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemManager.getInstance().removeItem(item,getApplicationContext());
                finish();
            }
        });


    }
}
