package zhenyuyang.ucsb.edu.cashier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class TransactionGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_gallery);

        TransactionListAdapter adapter = new TransactionListAdapter(this,  (TransactionManager.getInstance().getAllTransactions(this).toArray(new Transaction[TransactionManager.getInstance().getAllTransactions(this).size()])));
        ((ListView)findViewById(R.id.listView_transaction_gallery)).setAdapter(adapter);
    }
}
