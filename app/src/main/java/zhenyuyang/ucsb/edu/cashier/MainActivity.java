package zhenyuyang.ucsb.edu.cashier;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    //    private static List<Item> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.button_scanActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_recordActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_itemGalleryActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ItemGalleryActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_transactionGalleryActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionGalleryActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_settingsActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        MainActivityPermissionsDispatcher.getPermissionWithCheck(this);

        //test zone
//        ItemManager.getInstance().deleteData(getApplicationContext());
//        TransactionManager.getInstance().deleteData(getApplicationContext());


        ItemManager.getInstance().printAllItems(this);
        TransactionManager.getInstance().printAllTransactions(this);

//        List<Item> items = ItemManager.getInstance().getAllItems(this);
//        String output = "";
//        for (int i = 0; i < items.size(); i++) {
//            output += items.get(i).toString() + ";";
//        }
//
//        List<Transaction> transactions = TransactionManager.getInstance().getAllTransactions(this);
//        String output2 = "";
//        for (int i = 0; i < transactions.size(); i++) {
//            output2 += transactions.get(i).toTransactionStringWithoutNewLine() + ";";
//        }
//        String[] sub_splits = output2.split(";");
//
//        String query = "backupt@" + output2;
//        Client myClient = new Client(SettingsManager.getInstance().getServerAddress(), SettingsManager.getInstance().getServerPort(), query);
//        myClient.execute();
//        myClient.setOnServerRespondedListener(new Client.onServerRespondedListener() {
//            @Override
//            public void onServerResponded(String response) {
//                if (response.equals("3")) {
//                    Toast.makeText(getApplicationContext(), "Transactions Backup Finished", Toast.LENGTH_SHORT).show();
//
//
//                    List<Item> items = ItemManager.getInstance().getAllItems(getApplicationContext());
//                    String output = "";
//                    for (int i = 0; i < items.size(); i++) {
//                        output += items.get(i).toString() + ";";
//                    }
//                    String query = "backupi@" + output;
//                    Client myClient = new Client(SettingsManager.getInstance().getServerAddress(), SettingsManager.getInstance().getServerPort(), query);
//                    myClient.execute();
//                    myClient.setOnServerRespondedListener(new Client.onServerRespondedListener() {
//                        @Override
//                        public void onServerResponded(String response) {
//                            if (response.equals("4")) {
//                                Toast.makeText(getApplicationContext(), "Item Backup Finished", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//            }
//        });


        //end of test zone
    }

    @NeedsPermission(android.Manifest.permission.CAMERA)
    void getPermission() {
        Toast.makeText(this, "getPermission", Toast.LENGTH_SHORT).show();
    }

    @OnPermissionDenied(android.Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        Toast.makeText(this, "R.string.permission_camera_denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);

    }
}
