package app.num.barcodescannerproject;

/**
 * Created by Yummy on 4/16/2017.
 */

class Beer {
    private String name;
    private int amount;

    public Beer(){

    }

    public Beer(String name, int amount) {
        setName(name);
        setAmount(amount);
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

}
