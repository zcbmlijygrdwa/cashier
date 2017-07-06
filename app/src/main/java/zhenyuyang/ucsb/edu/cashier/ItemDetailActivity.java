package zhenyuyang.ucsb.edu.cashier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView textView_ID;
    private EditText editText_Name;
    private EditText editText_PriceIn;
    private EditText editText_PriceStandard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        textView_ID = (TextView)findViewById(R.id.textView_ID);
        editText_Name = (EditText)findViewById(R.id.editText_Name);
        editText_PriceIn = (EditText)findViewById(R.id.editText_PriceIn);
        editText_PriceStandard = (EditText)findViewById(R.id.editText_PriceStandard);

        String itemID = getIntent().getStringExtra("item_ID");
        Item item = ItemManager.getInstance().findItemById(itemID,this);

        textView_ID.setText(item.getID());
        editText_Name.setText(item.getName());
        editText_PriceIn.setText(item.getPriceIn()+"");
        editText_PriceStandard.setText(item.getPriceStandard()+"");

    }
}
