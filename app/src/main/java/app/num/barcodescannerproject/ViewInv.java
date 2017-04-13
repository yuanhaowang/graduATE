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

import java.util.ArrayList;

public class ViewInv extends ActionBarActivity {
    private static final String DEBUG_TAG = "HttpExample";
    ArrayList<Items> itemses = new ArrayList<Items>();
    ListView listview;
    Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewinv);
        listview = (ListView) findViewById(R.id.listview);
        /*btnDownload = (Button) findViewById(R.id.btnDownload);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            btnDownload.setEnabled(true);
        } else {
            btnDownload.setEnabled(false);
        }
        Log.e("handler", networkInfo.toString());*/
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1CI3P_quY3dnsWOk3-ECbGNZWLT_hh-aJGvM84CNj_DU");
    }

    /*public void buttonClickHandler(View view) {
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1CI3P_quY3dnsWOk3-ECbGNZWLT_hh-aJGvM84CNj_DU");

    }*/

    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                int amount = columns.getJSONObject(0).getInt("v");
                String name = columns.getJSONObject(1).getString("v");
                Items items = new Items(amount, name);
                itemses.add(items);
            }

            final ItemsAdapter adapter = new ItemsAdapter(this, R.layout.viewinv, itemses);
            listview.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}




