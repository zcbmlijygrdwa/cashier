package zhenyuyang.ucsb.edu.cashier;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Zhenyu on 2017-07-02.
 */

public class Transaction {
    private String transactionNumber;
    private String itemID;
    private String time;
    private int quantity;
    private float priceSell;

    public Transaction(String itemID,int quantity,float priceSell ){
        transactionNumber = UUID.randomUUID().toString();
        this.itemID = itemID;
        time = getTimaStamp();
        this.quantity = quantity;
        this.priceSell = priceSell;
    }

    public Transaction(String transactionNumber,String itemID,int quantity,float priceSell,String time){
        this.transactionNumber = transactionNumber;
        this.itemID = itemID;
        this.time = time;
        this.quantity = quantity;
        this.priceSell = priceSell;
    }

    public String getTimaStamp(){
        //add timeStamp
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
        String timeStamp = sdf.format(cal.getTime());
//        System.out.println( "timeStamp = "+timeStamp );
        return timeStamp;
    }

    public String getTransactionNumber(){
        return transactionNumber;
    }

    public String toTransactionString(){
        return transactionNumber+","+itemID+","+quantity+","+priceSell+","+time;
    }

    public String toString(){
        return "transactionNumber = "+transactionNumber+",itemID = "+itemID+",quantity = "+quantity+",priceSell = "+priceSell+",time = "+time;
    }
}
