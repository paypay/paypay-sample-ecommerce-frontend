package jp.ne.paypay.sample.model;

public class Item {
    private int id;
    private int price;
    private String title;
    private String image;

    public Item(int id, int price, String title, String image) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public Item setId(int id) {
        this.id = id;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Item setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Item setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Item setImage(String image) {
        this.image = image;
        return this;
    }


}
