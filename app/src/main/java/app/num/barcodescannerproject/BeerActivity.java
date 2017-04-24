package app.num.barcodescannerproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class BeerActivity extends AppCompatActivity {

    //global variables
    EditText txtBeerName;
    EditText txtQuantity;
    EditText txtRFID;
    //Spinner spinnerCategories;
    Button btnSaveBeer;

    private static boolean isUpdate;
    private static int realId;
    private static boolean isAdded;
    private String name;
    private static int realCategoryId=0;

    DataBaseHelper dbHelper;
    BeerController beerC;
    CategoryController categoryC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_beer);

        //obtain the param of Intent of last activity
        isUpdate = getIntent().getBooleanExtra("isEdit", false);
        realId = getIntent().getIntExtra("realId", 0);

        isAdded = getIntent().getBooleanExtra("isAdded", false);
        name = getIntent().getStringExtra("name");

        //create the classes instances
        dbHelper = new DataBaseHelper(this);
        beerC = new BeerController();
        categoryC =  new CategoryController();

        //initialized the ui components
        setUIComponents();
        //addItemsOnSpinnerCategories();


        //if is update charge the data, in the form
        if(isUpdate)
        {
            Cursor getBeers = dbHelper.rawQuery(beerC.getById(realId));

            if (getBeers != null ) {
                //Move cursor to first row
                if  (getBeers.moveToFirst()) {
                    do {
                        //Get version from Cursor
                        txtBeerName.setText(
                                getBeers.getString(
                                        getBeers.getColumnIndex(
                                                beerC.getcBeername())));

                        txtQuantity.setText(
                                getBeers.getString(
                                        getBeers.getColumnIndex(
                                                beerC.getcQuantity())));

                        txtRFID.setText(
                                getBeers.getString(
                                        getBeers.getColumnIndex(
                                                beerC.getcIdRFID())));

                        //TODO spinner to update
                        /*spinnerCategories.setSelection(
                                getBeers.getInt(
                                        getBeers.getColumnIndex(
                                                beerC.getcIdRFID()))-1);*/

                    }while (getBeers.moveToNext()); //Move to next row
                }
            }
        }

        if (isAdded)
        {
            txtBeerName.setText(name);
        }
    }



    /**
     * Set the Gui components of layout
     */
    private void setUIComponents(){
        txtBeerName = (EditText)findViewById(R.id.txtBeerName);
        txtQuantity = (EditText)findViewById(R.id.txtQuantity);
        txtRFID = (EditText)findViewById(R.id.txtRFID);
        btnSaveBeer = (Button)findViewById(R.id.btnSaveBeer);
        //spinnerCategories = (Spinner)findViewById(R.id.spinnerCategories);

        //set of button click
        btnSaveBeer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //if is create
                if (!isUpdate) {
                    dbHelper.executeSql(beerC.insert(
                            txtBeerName.getText().toString(),
                            Integer.parseInt(txtQuantity.getText().toString()),
                            Integer.parseInt(txtRFID.getText().toString())));
                }//if is update
                else {
                    dbHelper.executeSql(beerC.update(txtBeerName.getText().toString(),
                            Integer.parseInt(txtQuantity.getText().toString()),
                            Integer.parseInt(txtRFID.getText().toString()),
                            realId));
                }

                //go to the list activity
                Intent i = new Intent(BeerActivity.this, ListBeers.class);
                startActivity(i);
            }
        });

        /*spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                realCategoryId =  obtainCategorySelectedId(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });*/
    }

    /**
     * Obtain the id of selected item
     * if position is equals to listViewPosition
     * obtain the respective Id
     * @param position the int position of list view
     * @return id of real id
     */
    private int obtainCategorySelectedId(int position){
        int toStop=0;
        int returnId=0;
        Cursor getCategories = dbHelper.rawQuery(categoryC.getAll());
        if (getCategories != null ) {
            //Move cursor to first row
            if  (getCategories.moveToFirst()) {
                do {
                    if(position==toStop) {
                        //Get version from Cursor
                        returnId = getCategories.getInt(getCategories.getColumnIndex(categoryC.getcIdCategory()));
                    }
                    toStop++;
                }while (getCategories.moveToNext()); //Move to next row
            }
        }
        return returnId;
    }


    /**
     * add items into spinner dynamically
     */
    /*private void addItemsOnSpinnerCategories(){
        ArrayList<String> categoryResults = new ArrayList<String>();

        //obtain the cursor of get all
        Cursor getCategories = dbHelper.rawQuery(categoryC.getAll());

        if (getCategories != null ) {
            //Move cursor to first row
            if  (getCategories.moveToFirst()) {
                do {
                    //Get version from Cursor
                    String firstName = getCategories.getString(getCategories.getColumnIndex(categoryC.getcCategoryname()));
                    //Add the version to Arraylist 'results'
                    categoryResults.add(firstName);
                }while (getCategories.moveToNext()); //Move to next row
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, categoryResults);
        //spinnerCategories.setAdapter(adapter);
    }*/
}
