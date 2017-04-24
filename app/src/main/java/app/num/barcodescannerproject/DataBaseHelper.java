package app.num.barcodescannerproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseHelper {
    private static SQLiteDatabase sampleDB = null;

    /**
     * The constructor
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        createDataBase(context);
    }

    /**
     * This method create the database
     *
     * @param context set the context to create database
     */
    public void createDataBase(Context context) {
        sampleDB = context.openOrCreateDatabase("Beers.db", Context.MODE_PRIVATE, null);


        //Table Categories
        /*sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + "CATEGORY" +
                "( idCategory INTEGER PRIMARY KEY AUTOINCREMENT," +
                " categoryName TEXT," +
                " code INTEGER);");*/

        //Table Beers
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + "BEERS" +
                " (idBeer INTEGER PRIMARY KEY AUTOINCREMENT," +
                " beerName TEXT," +
                " quantity INTEGER," +
                " idRFID INTEGER);");
    }

    /**
     * This method execute an sql query
     *
     * @param sqlQuery set the query
     */
    public void executeSql(String sqlQuery) {
        sampleDB.execSQL(sqlQuery);
    }

    /**
     * This method execute a consult, query and convert to cursor
     *
     * @param sqlQuery set the query
     * @return Cursor
     */
    public Cursor rawQuery(String sqlQuery) {
        return sampleDB.rawQuery(sqlQuery, null);
    }
}
