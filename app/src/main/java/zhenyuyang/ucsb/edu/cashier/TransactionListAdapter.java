package zhenyuyang.ucsb.edu.cashier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Zhenyu on 2017-07-04.
 */

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
    private final Context context;
    private final Transaction[] transactions;

    public TransactionListAdapter(Context context, Transaction[] transactions) {
        super(context, -1, transactions);
        this.context = context;
        this.transactions = transactions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.transaction_cell_view, parent, false);
        TextView textView_item_name = (TextView) rowView.findViewById(R.id.textView_item_name);
        TextView textView_item_ID = (TextView) rowView.findViewById(R.id.textView_item_ID);
        TextView textView_transaction_price = (TextView) rowView.findViewById(R.id.textView_transaction_price);
        TextView textView_sell_quantity = (TextView) rowView.findViewById(R.id.textView_sell_quantity);
        TextView textView_transaction_time = (TextView) rowView.findViewById(R.id.textView_transaction_time);

        Item item = ItemManager.getInstance().findItemById(transactions[position].getItemID(),context);

        textView_item_name.setText(item.getName());
        textView_item_ID.setText(""+transactions[position].getItemID());
        textView_transaction_price.setText(""+transactions[position].getPriceSell());
        textView_sell_quantity.setText(""+transactions[position].getQuantity());
        textView_transaction_time.setText(""+transactions[position].getTime());
        // change the icon for Windows and iPhone
//        String s = values[position];
//        if (s.startsWith("iPhone")) {
//            imageView.setImageResource(R.drawable.no);
//        } else {
//            imageView.setImageResource(R.drawable.ok);
//        }

        return rowView;
    }
}
