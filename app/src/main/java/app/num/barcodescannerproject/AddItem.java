package app.num.barcodescannerproject;

/**
 * Created by Yummy on 4/30/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AddItem extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editTextName;
    private EditText editTextAmount;
    private EditText editTextRFID;

    private String id;
    private String name;
    private String amount;
    private String rfid;


    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Intent intent = getIntent();

        id = intent.getStringExtra(Config.ITEM_ID);
        name = intent.getStringExtra(Config.ITEM_NAME);
        amount = intent.getStringExtra(Config.ITEM_AMOUNT);
        rfid = intent.getStringExtra(Config.ITEM_RFID);

        //Initializing views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextRFID = (EditText) findViewById(R.id.editTextRfid);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        editTextName.setText(name);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
    }


    //Adding an employee
    private void addItem(){

        final String name = editTextName.getText().toString().trim();
        final String amount = editTextAmount.getText().toString().trim();
        final String rfid = editTextRFID.getText().toString().trim();

        class AddBeer extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddItem.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AddItem.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_ITEM_NAME,name);
                params.put(Config.KEY_ITEM_AMOUNT,amount);
                params.put(Config.KEY_ITEM_RFID,rfid);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddBeer add = new AddBeer();
        add.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addItem();
        }
    }
}
