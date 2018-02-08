package tests;

public class Product implements Comparable<Product> {
    private String title;
    private double price;

    public Product(double price, String title)
    {
        this.price = price;
        this.title = title;
    }

    public int compareTo(Product other)
    {
    	return Double.compare(this.price, other.price);
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
