package app.num.barcodescannerproject;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;



public class ListBeers extends AppCompatActivity {

    //global variables
    private static DataBaseHelper dbHelper;
    private static BeerController beerC;
    private static ListView beerList;
    ItemArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_beer);

        //instance variables
        beerList = (ListView) findViewById(R.id.beerList);
        dbHelper = new DataBaseHelper(this);
        beerC = new BeerController();

        //click in item of list view
        beerList.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        //obtain an Id to selected
                        int realId = obtainSelectedId(position);

                        //change the activity, and send parameters, true if is update
                        //and false if isn't
                        Intent i = new Intent (ListBeers.this, BeerActivity.class);
                        i.putExtra("isEdit", true);
                        i.putExtra("realId", realId);
                        startActivity(i);
                    }
                }
        );

        //if the user press long in a item selected, the system delete this row
        beerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                //obtain an Id to selected
                int realId = obtainSelectedId(pos);

                //delete the item selected
                dbHelper.executeSql(beerC.delete(realId));

                //refresh the listview
                refreshListView();
                return true;
            }
        });
    }

    /**
     * This method refresh list view
     */
    private void refreshListView(){
        ArrayList<Beer> beerResults = new ArrayList<Beer>();

        //obtain the cursor of get all
        Cursor getBeers = dbHelper.rawQuery(beerC.getAll());

        if (getBeers != null ) {
            //Move cursor to first row
            if  (getBeers.moveToFirst()) {
                do {
                    String firstName = getBeers.getString(getBeers.getColumnIndex(beerC.getcBeername()));
                    String amount = getBeers.getString(getBeers.getColumnIndex(beerC.getcQuantity()));
                    String rfid = getBeers.getString(getBeers.getColumnIndex(beerC.getcIdRFID()));
                    Log.d("Name:", firstName);
                    Log.d("amount:", amount);
                    Log.d("rfid:", rfid);
                    Beer beer = new Beer();
                    beer.setName(firstName);
                    beer.setAmount(Integer.parseInt(amount));
                    beer.setRFID(Integer.parseInt(rfid));
                    beerResults.add(beer);
                }while (getBeers.moveToNext()); //Move to next row
            }
        }

        adapter = new ItemArrayAdapter(this, R.layout.activity_list_beer, beerResults);
        beerList.setAdapter(adapter);
    }


    /**
     * Obtain the id of selected item
     * if position is equals to listViewPosition
     * obtain the respective Id
     * @param position the int position of list view
     * @return id of real id
     */
    private int obtainSelectedId(int position){
        int toStop=0;
        int returnId=0;
        Cursor getMovies = dbHelper.rawQuery(beerC.getAll());
        if (getMovies != null ) {
            //Move cursor to first row
            if  (getMovies.moveToFirst()) {
                do {
                    if(position==toStop) {
                        //Get version from Cursor
                         returnId = getMovies.getInt(getMovies.getColumnIndex(beerC.getcIdBeer()));
                    }
                    toStop++;
                }while (getMovies.moveToNext()); //Move to next row
            }
        }
        return returnId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_beers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.gotocreatebeer) {
            Intent intent = new Intent(this,BeerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
