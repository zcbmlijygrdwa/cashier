package zhenyuyang.ucsb.edu.cashier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Zhenyu on 2017-07-04.
 */

public class ItemListAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private Item[] items;

    public ItemListAdapter(Context context, Item[] items) {
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    public void setItems(Item[] items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_cell_view, parent, false);
        TextView textView_item_name = (TextView) rowView.findViewById(R.id.textView_item_name);
        TextView textView_priceIn = (TextView) rowView.findViewById(R.id.textView_priceIn);
        TextView textView_priceStandard = (TextView) rowView.findViewById(R.id.textView_priceStandard);
        TextView textView_item_ID = (TextView) rowView.findViewById(R.id.textView_item_ID);


        textView_item_name.setText(items[position].getName());
        textView_priceIn.setText(""+items[position].getPriceIn());
        textView_priceStandard.setText(""+items[position].getPriceStandard());
        textView_item_ID.setText(""+items[position].getID());
        // change the icon for Windows and iPhone
//        String s = values[position];
//        if (s.startsWith("iPhone")) {
//            imageView.setImageResource(R.drawable.no);
//        } else {
//            imageView.setImageResource(R.drawable.ok);
//        }

        return rowView;
    }

    @Override
    public int getCount() {
        return items.length;
    }
}
