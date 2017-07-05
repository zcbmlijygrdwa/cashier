package zhenyuyang.ucsb.edu.cashier;

import android.content.Context;
import android.util.FloatProperty;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;

/**
 * Created by Zhenyu on 2017-07-04.
 */

public class TransactionManager {
    private static TransactionManager instance = null;
    private static List<Transaction> transactions;


    public static TransactionManager getInstance(){
        if(instance==null){
            instance = new TransactionManager();
        }
        return instance;
    }

    public TransactionManager(){
        transactions = new ArrayList<Transaction>();
    }


    public void updateTransactionList(Context context){
        transactions.clear();
        String line = "";
        try {
            InputStream inputStream = context.openFileInput("transactiondata.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    line = receiveString;
                    String[] splits = line.split(",");
                    //Transaction(Item item,int quantity,float priceSell )
//
//                    int quantity = Integer.parseInt(splits[1]);
//                    float priceSell = Float.parseFloat(splits[2]);
//                    Transaction transaction = new Transaction(splits[0],quantity,priceSell);
                    Transaction transaction = new Transaction(splits[0],splits[1],Integer.parseInt(splits[2]),Float.parseFloat(splits[3]),splits[4]);
                    transactions.add(transaction);
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
    public void createTransaction(String itemIDString,int quantity,float priceSell , Context context){
//        String[] splits = itemString.split(",");
//        Transaction tempItem = findTransactionById(splits[0],context);
//        if(tempItem!=null){
//            System.out.println("A new transaction creation failed: Duplicated transaction");
//        }
//        else{
        Transaction transaction= new Transaction(itemIDString,quantity,priceSell);
        String itemString = transaction.toTransactionString();
            String FILENAME = "";
            BufferedWriter bw = null;
            FileWriter fw = null;

            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("transactiondata.txt", Context.MODE_APPEND));
                outputStreamWriter.append(itemString);
                outputStreamWriter.flush();
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateTransactionList(context);
//        }
    }

    public Transaction findTransactionById(String id, Context context){
        updateTransactionList(context);
        for(int i = 0; i<transactions.size();i++){
            if(transactions.get(i).getTransactionNumber().equals(id)){
                System.out.println ("Found data: "+transactions.get(i).toString());
                return transactions.get(i);
                //return items.get(i).toString();
            }
        }
        System.out.println ("Nothing found.");
        return null;
    }

    public void printAllTransactions(Context context){
        updateTransactionList(context);
        for(int i = 0; i<transactions.size();i++){
            System.out.println ("items["+i+"]: "+transactions.get(i).toString());
            //return items.get(i).toString();
        }
    }

    public List<Transaction> getAllTransactions(Context context){
        updateTransactionList(context);;
        return transactions;
    }

    public void deleteData(Context context){
        //to delete File
        context.deleteFile("transactiondata.txt");
    }
}

