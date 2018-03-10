public class Product implements Comparable<Product> {
    private String name;
    private double price;
    private String producer;

    public Product(String name, double price, String producer) {
        this.name = name;
        this.price = price;
        this.producer = producer;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getProducer() {
        return producer;
    }

    @Override
    public int compareTo(Product o) {
        int comp = this.name.compareTo(o.name);
        if (comp == 0) {
            comp = this.producer.compareTo(o.producer);
            if (comp == 0) {
                comp = Double.compare(this.price, o.price);
            }
        }
        return comp;
    }
}
