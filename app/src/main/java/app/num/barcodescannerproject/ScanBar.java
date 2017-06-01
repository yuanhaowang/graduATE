package app.num.barcodescannerproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.NumberPicker;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.zxing.Result;
import com.opencsv.CSVWriter;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanBar extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private String product_code;
    private String product_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanbar);

        mScannerView = new ZXingScannerView(this);   //Initializes scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); //Handler for scan results.
        mScannerView.startCamera();         //Start camera

    }

    public void BarcodeScanner(View view){

        mScannerView = new ZXingScannerView(this);   //Initializes scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); //Handler for scan results.
        mScannerView.startCamera();         //Start camera

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScanBar.this);
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        //Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.

        product_code = rawResult.getText();

        // If you would like to resume scanning, call this method below:

        RetrieveFeedTask job = new RetrieveFeedTask();
        job.execute(product_code);
        if (product_name.equals("")){
            builder.setTitle("Error");
            builder.setMessage("Product not found");
            AlertDialog alert1 = builder.create();
            alert1.show();
            //addResult("");
        }
        else
        {
            addResult(product_name);
        }
        mScannerView.resumeCameraPreview(this);
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        public AlertDialog.Builder builder = new AlertDialog.Builder(ScanBar.this);

        protected String doInBackground(String... product_code) {
            try {
                OutpanAPI api = new OutpanAPI("fdb77d24bdd184b80e7377a1bef3e5e3");
                OutpanObject obj = api.getProductName(product_code[0]);
                Log.e("handler", obj.name);
                return obj.name;
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String name) {
            /*if (name.equals("")){
                builder.setTitle("Error");
                builder.setMessage("Product not found");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            /*else {
                builder.setTitle("Scan Result");
                builder.setMessage(name);
                AlertDialog alert1 = builder.create();
                alert1.show();
            }*/

            product_name = name;
        }
    }
    public void addResult(String name) {

        Intent intent = new Intent(this,AddItem.class);
        //intent.putExtra("isAdded", true);
        intent.putExtra(Config.ITEM_NAME, name);
        startActivity(intent);
    }

}
