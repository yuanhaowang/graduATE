package app.num.barcodescannerproject;

/**
 * Created by Yummy on 4/16/2017.
 */

class Beer {
    private String name;
    private int amount;
    private int rfid;

    public Beer(){

    }

    public Beer(String name, int amount, int rfid) {
        setName(name);
        setAmount(amount);
        setRFID(rfid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getRFID() {
        return rfid;
    }

    public void setRFID(int rfid) {
        this.rfid = rfid;
    }

}
