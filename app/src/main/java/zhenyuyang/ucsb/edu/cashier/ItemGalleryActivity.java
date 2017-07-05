package zhenyuyang.ucsb.edu.cashier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ItemGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_gallery);


        ItemListAdapter adapter = new ItemListAdapter(this,  (ItemManager.getInstance().getAllItems(this).toArray(new Item[ItemManager.getInstance().getAllItems(this).size()])));
        ((ListView)findViewById(R.id.listView_item_gallery)).setAdapter(adapter);
    }
}
