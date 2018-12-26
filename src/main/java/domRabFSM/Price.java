package domRabFSM;

public class Price {
    private double minPrice;
    private double price;
    private double koef;

    public Price(double minPrice, double price, double koef){
        this.setMinPrice(minPrice);
        this.setPrice(price);
        this.setKoef(koef);

    }

    public Price(double price){
        this.setPrice(price);
    }
    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getKoef() {
        return koef;
    }

    public void setKoef(double koef) {
        this.koef = koef;
    }
}
