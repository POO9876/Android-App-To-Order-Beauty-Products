package com.ellenabeautyproducts.ellenabeautycustomers;

/**
 * Created by KELVIN on 28/05/2017.
 */
public class ListItem {
    private String head;
    private String customer;
    private String imageUrl;
    private String date;
    private String quantity;

    public ListItem(String head, String customer, String imageUrl, String date, String quantity) {
        this.head = head;
        this.customer = customer;
        this.imageUrl = imageUrl;
        this.date = date;
        this.quantity = quantity;
    }

    public String getHead() {
        return head;
    }

    public String getCustomer() {
        return customer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }

    public String getQuantity() {
        return quantity;
    }
}
