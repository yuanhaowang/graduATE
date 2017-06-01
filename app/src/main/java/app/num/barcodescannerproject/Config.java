package app.num.barcodescannerproject;

/**
 * Created by Yummy on 4/30/2017.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://192.168.1.16/Inventory/addItem.php";
    public static final String URL_GET_ALL = "http://192.168.1.16/Inventory/getAllItems.php";
    public static final String URL_GET_ITEM = "http://192.168.1.16/Inventory/getItem.php?id=";
    public static final String URL_UPDATE_ITEM = "http://192.168.1.16/Inventory/updateItem.php";
    public static final String URL_DELETE_ITEM = "http://192.168.1.16/Inventory/deleteItem.php?id=";
    public static final String URL_REMOVE_ITEM = "http://192.168.1.16/Inventory/removeItem.php?id=";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_ITEM_ID = "id";
    public static final String KEY_ITEM_NAME = "name";
    public static final String KEY_ITEM_AMOUNT = "amount";
    public static final String KEY_ITEM_RFID = "rfid";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_AMOUNT = "amount";
    public static final String TAG_RFID = "rfid";

    //employee id to pass with intent
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_NAME = "item_name";
    public static final String ITEM_AMOUNT = "item_amount";
    public static final String ITEM_RFID = "item_rfid";
}

