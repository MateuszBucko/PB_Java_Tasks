package model;

/**
 * Created by mateusz on 24.02.17.
 */
public class Account {

    private Integer id;
    private String name;
    private String address;
    private double amount;

    public Account(String name, String address) {
        this.name = name;
        this.address = address;
        amount = 0.0;
    }

    public Account(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
