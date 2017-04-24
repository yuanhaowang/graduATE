package app.num.barcodescannerproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemArrayAdapter extends ArrayAdapter<Beer> {

    Context context;
    private ArrayList<Beer> item_array;

    public ItemArrayAdapter(Context context, int textViewResourceId, ArrayList<Beer> beers) {
        super(context, textViewResourceId, beers);
        this.context = context;
        this.item_array = beers;
    }

    @Override
    public View getView(int amount, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.activity_list_beer_item, null);
        }
        Beer o = item_array.get(amount);
        if (o != null) {
            TextView amo = (TextView) v.findViewById(R.id.amount);
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView rfid = (TextView) v.findViewById(R.id.rfid);

            name.setText(String.valueOf(o.getName()));
            rfid.setText(String.valueOf(o.getRFID()));
            amo.setText(String.valueOf(o.getAmount()));
        }
        return v;
    }
}