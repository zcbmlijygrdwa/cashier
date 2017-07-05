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
    private float transactionPrice;
    private float transactionValue;

    public Transaction(String itemID,int quantity,float transactionPrice ){
        this.transactionNumber = UUID.randomUUID().toString();
        this.itemID = itemID;
        this.time = getTimaStamp();
        this.quantity = quantity;
        this.transactionPrice = transactionPrice;
        this.transactionValue = quantity*transactionPrice;
    }

    public Transaction(String transactionNumber,String itemID,int quantity,float transactionPrice,String time){
        this.transactionNumber = transactionNumber;
        this.itemID = itemID;
        this.time = time;
        this.quantity = quantity;
        this.transactionPrice = transactionPrice;
        this.transactionValue = quantity*transactionPrice;
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

    public String getItemID(){
        return itemID;
    }

    public float getTransactionPrice() {
    return transactionPrice;
    }

    public String getTime(){
        return time;
    }

    public String getFormatedTime(){
        String[] splits = time.split(" ");
        String date = splits[0];

        String[] splits_date = date.split("\\.");
        String year = splits_date[0];
        String month = splits_date[1];
        String day = splits_date[2];


        String timeClock = splits[1];
        String[] splits_time_clock = timeClock.split(":");
        String hour = splits_time_clock[0];
        String minute = splits_time_clock[1];
        String second = splits_time_clock[2];


        return year+"年"+month+"月"+day+"日  "+hour+"时"+minute+"分"+second+"秒";
    }

    public int getQuantity(){
        return quantity;
    }

    public String toTransactionString(){
        return transactionNumber+","+itemID+","+quantity+","+transactionPrice+","+time+"\n";
    }

    public String toTransactionStringWithoutNewLine(){
        return transactionNumber+","+itemID+","+quantity+","+transactionPrice+","+time;
    }

    public float getTransactionValue(){
        return transactionValue;
    }

    public String toString(){
        return "transactionNumber = "+transactionNumber+",itemID = "+itemID+",quantity = "+quantity+",transactionPrice = "+transactionPrice+",time = "+time;
    }
}
