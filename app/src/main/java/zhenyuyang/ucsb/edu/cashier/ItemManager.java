package zhenyuyang.ucsb.edu.cashier;

import android.*;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;

/**
 * Created by Zhenyu on 2017-07-03.
 */

public class ItemManager {
    private static ItemManager instance = null;
    private static List<Item> items;


    public static ItemManager getInstance(){
        if(instance==null){
            instance = new ItemManager();
        }
        return instance;
    }

    public ItemManager(){
        items = new ArrayList<Item>();
    }


    public void updateItemList(Context context){
        items.clear();
        String line = "";
        try {
            InputStream inputStream = context.openFileInput("itemdata.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    line = receiveString;
                    Log.i("ItemManager", "line = "+line);
                    String[] splits = line.split(",");
                    Item item = new Item(splits[0],splits[1],Float.parseFloat(splits[2]),Float.parseFloat(splits[3]));
                    items.add(item);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void createItem(String itemString, Context context){
        String[] splits = itemString.split(",");
        Item tempItem = findItemById(splits[0],context);
        if(tempItem!=null){
            //System.out.println("A new item creation failed: Duplicated item");
            Toast.makeText(context,"A new item creation failed: Duplicated item", Toast.LENGTH_SHORT).show();
        }
        else{
            String FILENAME = "";
            BufferedWriter bw = null;
            FileWriter fw = null;

            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("itemdata.txt", Context.MODE_APPEND));
                outputStreamWriter.append(itemString);
                outputStreamWriter.flush();
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context,"New item: "+splits[1]+" created!", Toast.LENGTH_SHORT).show();
            updateItemList(context);
        }
    }

    public Item findItemById(String id, Context context){
        updateItemList(context);
        for(int i = 0; i<items.size();i++){
            if(items.get(i).getID().equals(id)){
                System.out.println ("Found data: "+items.get(i).toString());
                return items.get(i);
                //return items.get(i).toString();
            }
        }
        System.out.println ("Nothing found.");
        return null;
    }

    public void printAllItems(Context context){
        updateItemList(context);
        for(int i = 0; i<items.size();i++){
            System.out.println ("items["+i+"]: "+items.get(i).toString());
            //return items.get(i).toString();
        }
    }

    public List<Item> getAllItems(Context context){
        updateItemList(context);;
        return items;
    }

    public void deleteData(Context context){
        //to delete File
        context.deleteFile("itemdata.txt");
    }
}
