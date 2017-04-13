package app.num.barcodescannerproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<Items> {

    Context context;
    private ArrayList<Items> item_array;

    public ItemsAdapter(Context context, int textViewResourceId, ArrayList<Items> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.item_array = items;
    }

    @Override
    public View getView(int amount, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.inventory, null);
        }
        Items o = item_array.get(amount);
        if (o != null) {
            TextView amo = (TextView) v.findViewById(R.id.amount);
            TextView name = (TextView) v.findViewById(R.id.name);

            amo.setText(String.valueOf(o.getAmount()));
            name.setText(String.valueOf(o.getName()));
        }
        return v;
    }
}
