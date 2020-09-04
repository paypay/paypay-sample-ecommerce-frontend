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

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
