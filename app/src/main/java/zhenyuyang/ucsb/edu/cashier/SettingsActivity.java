package zhenyuyang.ucsb.edu.cashier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private EditText editText_serverAddress;
    private EditText editText_serverPort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editText_serverAddress = (EditText)findViewById(R.id.editText_serverAddress);
        editText_serverPort = (EditText)findViewById(R.id.editText_serverPort);

        editText_serverAddress.setText(SettingsManager.getInstance().getServerAddress());
        editText_serverPort.setText(SettingsManager.getInstance().getServerPort()+"");

        findViewById(R.id.button_clear_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemManager.getInstance().deleteData(getApplicationContext());
                TransactionManager.getInstance().deleteData(getApplicationContext());
            }
        });

        findViewById(R.id.button_backup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Item> items = ItemManager.getInstance().getAllItems(getApplicationContext());
                String output = "";
                for (int i = 0; i < items.size(); i++) {
                    output += items.get(i).toString() + ";";
                }

                List<Transaction> transactions = TransactionManager.getInstance().getAllTransactions(getApplicationContext());
                String output2 = "";
                for (int i = 0; i < transactions.size(); i++) {
                    output2 += transactions.get(i).toTransactionStringWithoutNewLine() + ";";
                }
                String[] sub_splits = output2.split(";");

                String query = "backupt@" + output2;
                Client myClient = new Client(SettingsManager.getInstance().getServerAddress(), SettingsManager.getInstance().getServerPort(), query);
                myClient.execute();
                myClient.setOnServerRespondedListener(new Client.onServerRespondedListener() {
                    @Override
                    public void onServerResponded(String response) {
                        if (response.equals("3")) {
                            Toast.makeText(getApplicationContext(), "Transactions Backup Finished", Toast.LENGTH_SHORT).show();


                            List<Item> items = ItemManager.getInstance().getAllItems(getApplicationContext());
                            String output = "";
                            for (int i = 0; i < items.size(); i++) {
                                output += items.get(i).toString() + ";";
                            }
                            String query = "backupi@" + output;
                            Client myClient = new Client(SettingsManager.getInstance().getServerAddress(), SettingsManager.getInstance().getServerPort(), query);
                            myClient.execute();
                            myClient.setOnServerRespondedListener(new Client.onServerRespondedListener() {
                                @Override
                                public void onServerResponded(String response) {
                                    if (response.equals("4")) {
                                        Toast.makeText(getApplicationContext(), "Item Backup Finished", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        findViewById(R.id.button_finish_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsManager.getInstance().setServerAddress(editText_serverAddress.getText().toString());
                SettingsManager.getInstance().setServerPort(Integer.parseInt(editText_serverPort.getText().toString()));
                finish();
            }
        });






    }
}
