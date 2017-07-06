package zhenyuyang.ucsb.edu.cashier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ItemGalleryActivity extends AppCompatActivity {
    private  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_gallery);


        ItemListAdapter adapter = new ItemListAdapter(this,  (ItemManager.getInstance().getAllItems(this).toArray(new Item[ItemManager.getInstance().getAllItems(this).size()])));
        listView = ((ListView)findViewById(R.id.listView_item_gallery));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item_ID = ((TextView)view.findViewById(R.id.textView_item_ID)).getText().toString();
                Intent intent = new Intent(getApplicationContext(),ItemDetailActivity.class);
                intent.putExtra("item_ID",item_ID);
                startActivity(intent);
            }
        });
    }
}
