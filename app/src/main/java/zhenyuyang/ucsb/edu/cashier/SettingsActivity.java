package zhenyuyang.ucsb.edu.cashier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
