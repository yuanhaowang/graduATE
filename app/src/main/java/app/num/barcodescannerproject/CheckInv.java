package app.num.barcodescannerproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CheckInv extends ActionBarActivity {
    ArrayList<Beer> beers = new ArrayList<Beer>();
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkinv);
        listview = (ListView) findViewById(R.id.listview);
        readBeerInv();
    }

    private void readBeerInv() {
        InputStream is = getResources().openRawResource(R.raw.inventory);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            while( (line = reader.readLine()) != null)
            {
                //Split by ','
                String[] tokens = line.split(",");

                //Read the data
                Beer beer = new Beer();
                beer.setName(tokens[0]);
                beer.setAmount(Integer.parseInt(tokens[1]));
                beers.add(beer);

                Log.d("MyActivity", "Just created:" + beer);

                final ItemArrayAdapter adapter = new ItemArrayAdapter(this, R.layout.checkinv, beers);
                listview.setAdapter(adapter);

            }
        } catch (IOException e) {
            Log.wtf("MyAactivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }
}