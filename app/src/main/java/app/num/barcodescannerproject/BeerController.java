package app.num.barcodescannerproject;

public class BeerController {

    // Create the variables statics and names of entity
    public static final String C_TABLE_NAME = "BEERS";
    public static final String C_ID_BEER = "idBeer";
    public static final String C_BEERNAME = "beerName";
    public static final String C_QUANTITY = "quantity";
    public static final String C_ID_RFID = "idRFID";

    //the getters and setters
    public String getcTableName() {
        return C_TABLE_NAME;
    }

    public String getcIdBeer() {
        return C_ID_BEER;
    }

    public String getcBeername() {
        return C_BEERNAME;
    }

    public String getcQuantity() {
        return C_QUANTITY;
    }

    public String getcIdRFID() {
        return C_ID_RFID;
    }


    /**
     * Insert a beer
     * @param beerName the beer name
     * @param quantity the quantity of stock
     * @param idRFID the RFID tag
     * @return a string to execute query
     */
    public String insert(String beerName, int quantity, int idRFID) {
        return "INSERT INTO " + C_TABLE_NAME + "(" + C_BEERNAME + " , " + C_QUANTITY + " , " + C_ID_RFID + ") " +
                "VALUES ('" + beerName + "'," + quantity + "," + idRFID + ");";
    }

    /**
     * Delete a beer
     * @param idBeer the id of the movie to delete this
     * @return a string to execute query
     */
    public String delete(int idBeer) {
        return "DELETE FROM " + C_TABLE_NAME + " WHERE " + C_ID_BEER + " = " + idBeer + ";";
    }

    /**
     * Update a beer
     * @param beerName the name of the beer
     * @param quantity the quantity of stock
     * @param idRFID the RFID
     * @param idBeer  the id of the Beer to update this
     * @return a string to execute query
     */
    public String update(String beerName, int quantity, int idRFID, int idBeer) {
        return "UPDATE " + C_TABLE_NAME +
                " SET " + C_BEERNAME + " = '" + beerName + "' , " +
                C_QUANTITY + " = " + quantity + " , " +
                C_ID_RFID + " = " + idRFID +
                " WHERE "+ C_ID_BEER + " = " + idBeer + ";";
    }

    /**
     * Get all Beers
     *
     * @return a string to raw query
     */
    public String getAll() {
        return "SELECT * FROM " + C_TABLE_NAME;
    }

    /**
     * Get beer by RFID
     * @param idRFID set idRFID to update
     * @return a string to raw query
     */
    public String getById(int idRFID) {
        return "SELECT * FROM " + C_TABLE_NAME + " WHERE " + C_ID_RFID + " = " + idRFID;
    }


}
