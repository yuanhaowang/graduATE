package app.num.barcodescannerproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.google.zxing.oned.MultiFormatUPCEANReader;

import com.opencsv.CSVWriter;

import static java.lang.String.valueOf;


public class AddBar extends Activity {

    private ImageView barcodeImageView;
    private Button pickBarcodeButton;
    private TextView barcodeResultTextView;
    public String product_name = "";


    protected static final int PICK_IMAGE_REQUEST = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ImageView img = (ImageView) findViewById(R.id.barcode_image);
        img.setImageResource(R.drawable.beer);
        initializeViews();
    }
    /**
     * Initializing views
     */
    private void initializeViews() {
        barcodeImageView = (ImageView) findViewById(R.id.barcode_image);
        pickBarcodeButton = (Button) findViewById(R.id.pick_barcode_btn);
        barcodeResultTextView = (TextView) findViewById(R.id.barcode_result_txtview);

        pickBarcodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST){

            if (resultCode == RESULT_OK) {

                if (data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        new ScanTask(bitmap).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @author dipenp
     * AsyncTask to scan barcode in background.
     */
    class ScanTask extends AsyncTask<String, String, String> {

        private Bitmap bitmap;
        private String result = "";

        public ScanTask(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            barcodeImageView.setImageBitmap(bitmap);
            barcodeResultTextView.setText("Processing...");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                /*Result[] results = decode(bitmap);
                if (null != results) {
                    for (int i = 0; i < results.length; i++) {
                        result = result +" ("+(i+1)+") "+ results[i].getText() + "\n";
                    }
                }*/
                result = decode(bitmap);
            } catch (Exception e) {
                result = "Exception";
                Log.d("handler", "Bug2");
                e.printStackTrace();
            }
            return result;
        }

        //@Override
        protected void onPostExecute(String res) {
            if(res != "")
            {
                new getname().execute(res);
            }
            else
            {
                barcodeResultTextView.setText("Product Not Found");
            }

        }
    }

    /**
     * @param imageBitmap : Bitmap image
     * @return Array of barcode result
     */
    //public Result[] decode(Bitmap imageBitmap) {
     public String decode(Bitmap imageBitmap) {

        //MultiFormatReader reader = null;
        Map<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

		/* There are different format to create barcode and qrcode,
		 * while extracting we are adding all the possible format for better result.
		 */
        hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        //hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.UPC_EAN_EXTENSION);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        //reader = new MultiFormatReader();
        //reader.setHints(hints);

         MultiFormatUPCEANReader reader = new MultiFormatUPCEANReader(hints);

        int width = imageBitmap.getWidth();
        int height = imageBitmap.getHeight();
        int[] pixels = new int[width * height];
        imageBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        //MultipleBarcodeReader multiReader = new GenericMultipleBarcodeReader(reader);

        //Result[] theResults = null;
        Result theResult = null;
         String code = "";
        try {
			/* decode multiple is used so that if multiple barcode is present in single image we can get result for all of them.
			 */
            //theResult = multiReader.decodeMultiple(bitmap, hints);
            theResult = reader.decode(bitmap, hints);
            code = theResult.getText();
        }
        catch (NotFoundException e1) {
            code = "";
        } catch (FormatException e) {
            e.printStackTrace();
            Log.d("handler", "Bug1");
        }
         return code;
    }

    class getname extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... code) {
            try {
                OutpanAPI api = new OutpanAPI("fdb77d24bdd184b80e7377a1bef3e5e3");
                OutpanObject obj = api.getProductName(code[0]);
                Log.e("handler", obj.valid);
                product_name = obj.name;
                return obj.valid;
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String name)
        {
            if(name != "false") {
                barcodeResultTextView.setText(product_name);
                addResult(product_name);
            }
        }

    }

    public void addResult(String name) {

        Intent intent = new Intent(this,AddItem.class);
        //intent.putExtra("isAdded", true);
        intent.putExtra(Config.ITEM_NAME, name);
        startActivity(intent);
    }

}

