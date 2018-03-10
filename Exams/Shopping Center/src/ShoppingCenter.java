import java.util.*;

public class ShoppingCenter {
    private Map<String, List<Product>> productsByName;
    private Map<String, List<Product>> productsByProducer;
    private Map<String, List<Product>> productsByNameAndProducer;
    private TreeMap<Double, List<Product>> productsByPrice;

    public ShoppingCenter() {
        this.productsByName = new HashMap<>();
        this.productsByProducer = new HashMap<>();
        this.productsByNameAndProducer = new HashMap<>();
        this.productsByPrice = new TreeMap<>();
    }

    public String addProduct(String name, double price, String producer) {
        Product product = new Product(name, price, producer);

        this.productsByName.putIfAbsent(name, new ArrayList<>());
        this.productsByName.get(name).add(product);

        this.productsByProducer.putIfAbsent(producer, new ArrayList<>());
        this.productsByProducer.get(producer).add(product);

        String key = name.concat(producer);
        this.productsByNameAndProducer.putIfAbsent(key, new ArrayList<>());
        this.productsByNameAndProducer.get(key).add(product);

        this.productsByPrice.putIfAbsent(price, new ArrayList<>());
        this.productsByPrice.get(price).add(product);

        return "Product added";
    }

    public String deleteProducts(String producer) {
        String result;
        if (this.productsByProducer.containsKey(producer)) {
            int productsCount = this.productsByProducer.get(producer).size();
            if (productsCount == 0) {
                return "No products found";
            } else {
                result = String.format("%d products deleted", productsCount);
            }
            List<Product> productsToRemove = this.productsByProducer.get(producer);
            for (Product product : productsToRemove) {
                this.productsByName.get(product.getName()).remove(product);
                this.productsByNameAndProducer.get(product.getName() + product.getProducer()).remove(product);
                this.productsByPrice.get(product.getPrice()).remove(product);
            }
            this.productsByProducer.remove(producer);
            return result;
        } else {
            return "No products found";
        }
    }

    public String deleteProducts(String name, String producer) {
        String key = name.concat(producer);
        String result;
        if (this.productsByNameAndProducer.containsKey(key)) {
            int productsCount = this.productsByNameAndProducer.get(key).size();
            if (productsCount == 0) {
                return "No products found";
            } else {
                result = String.format("%d products deleted", productsCount);
            }
            List<Product> productsToRemove = this.productsByNameAndProducer.get(key);
            for (Product product : productsToRemove) {
                this.productsByName.get(product.getName()).remove(product);
                this.productsByProducer.get(product.getProducer()).remove(product);
                this.productsByPrice.get(product.getPrice()).remove(product);
            }
            this.productsByNameAndProducer.remove(key);
            return result;
        } else {
            return "No products found";
        }
    }

    public List<Product> findProductsByName(String name) {
        return this.productsByName.get(name);
    }

    public List<Product> findProductsByProducer(String producer) {
        return this.productsByProducer.get(producer);
    }

    public List<Product> findProductsByPriceRange(double from, double to) {
        List<Product> result = new ArrayList<>();
        this.productsByPrice.subMap(from, true, to, true)
                .forEach((key, value) -> result.addAll(value));
        return result;
    }
}
