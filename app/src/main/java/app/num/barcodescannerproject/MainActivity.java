package app.num.barcodescannerproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private String product_code;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);}

    }

    public void BarcodeScanner(View view){


        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.

        product_code = rawResult.getText();

        // If you would like to resume scanning, call this method below:
       // mScannerView.resumeCameraPreview(this);

        RetrieveFeedTask job = new RetrieveFeedTask();
        job.execute(product_code);

        displayResult();

    }

    public void displayResult() {


    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        public AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        protected String doInBackground(String... product_code) {
            try {
                OutpanAPI api = new OutpanAPI("fdb77d24bdd184b80e7377a1bef3e5e3");
                OutpanObject obj = api.getProduct(String.valueOf(product_code));
                System.out.println(obj.gtin); // --> 0796435419035
                System.out.println(obj.outpan_url); // --> http://www.outpan.com/view_product.php?barcode=0796435419035
                System.out.println(obj.name); // --> Optoma ML750 Palm-sized Projection Powerhouse
                Log.e("handler", obj.name);
                return obj.name;
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String name) {
            if (name == ""){
                builder.setTitle("Error");
                builder.setMessage("Product not found");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                builder.setTitle("Scan Result");
                builder.setMessage(name);
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        }
    }

}
