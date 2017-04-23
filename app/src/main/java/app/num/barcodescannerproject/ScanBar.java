package app.num.barcodescannerproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        }
        else
        {
            addResult();
        }
        mScannerView.resumeCameraPreview(this);
    }

    public void addResult() {

        Context context = ScanBar.this;
        final List<String> newInv = new ArrayList<String>();

        final NumberPicker picker = new NumberPicker(context);
        picker.setMinValue(1);
        picker.setMaxValue(50);

        final FrameLayout layout = new FrameLayout(context);
        layout.addView(picker, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        new AlertDialog.Builder(context)
                .setTitle(product_name)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newInv.add(product_name);
                        newInv.add(String.valueOf(i));
                        update(newInv);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
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

    public void update(List<String> newBeer) {

        String[] simpleArray = new String[ newBeer.size() ];
        newBeer.toArray( simpleArray );

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "inventory.csv");
        if(file.exists()){
            try {
                //PrintWriter fw = new PrintWriter(file);
                PrintWriter fw = new PrintWriter(new FileWriter(file, true));
                Log.d("handler", "printing to file");
                for (int i = 0; i < simpleArray.length; i++) {
                    fw.write(simpleArray[i]);
                    fw.write(",");
                }
                fw.write("\n");
                fw.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            path.mkdirs();
            PrintWriter fw = null;
            try {
                fw = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Log.d("handler", "making file");
            for (int i = 0; i < simpleArray.length; i++) {
                fw.write(simpleArray[i]);
                fw.write(",");
            }
            fw.write("\n");
            fw.flush();
        }
    }
}
