package zhenyuyang.ucsb.edu.cashier;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Zhenyu on 2017-07-02.
 */

public class Transaction {
    private String transactionNumber;
    private Item item;
    private String time;
    private int quantity;
    private float priceSell;

    public Transaction(Item item,int quantity,float priceSell ){
        transactionNumber = UUID.randomUUID().toString();
        this.item = item;
        time = getTimaStamp();
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
}
