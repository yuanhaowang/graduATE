package app.num.barcodescannerproject;


import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class CheckInv extends AppCompatActivity {
    ArrayList<Beer> beers = new ArrayList<Beer>();
    ListView listview;
    ItemArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkinv);
        listview = (ListView) findViewById(R.id.listview);
        readBeerInv();
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub

                beers.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(CheckInv.this, "Item Deleted", Toast.LENGTH_LONG).show();
                return true;
            }

        });
    }

    private void readBeerInv() {

        File path = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "inventory.csv");

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("handler", "file note found");
        }
        BufferedReader reader;
        String line = "";
        reader = new BufferedReader(new InputStreamReader(fileInputStream));
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                Log.d("handler", String.valueOf(line));
                //Split by ','
                String[] tokens = line.split(",");

                //Read the data
                Beer beer = new Beer();
                beer.setName(tokens[0]);
                beer.setAmount(Integer.parseInt(tokens[1]));
                beers.add(beer);

                adapter = new ItemArrayAdapter(this, R.layout.checkinv, beers);
                listview.setAdapter(adapter);

            }
        } catch (IOException e) {
            Log.wtf("MyAactivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }
}