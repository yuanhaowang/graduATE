package app.num.barcodescannerproject;

public class Items {
    private int amount;
    private String name;

    public Items(int amount, String name)
    {
        this.setAmount(amount);
        this.setName(name);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int Amount) {
        this.amount = Amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
