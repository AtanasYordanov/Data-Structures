import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        ShoppingCenter shop = new ShoppingCenter();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String input = reader.readLine();
            String command = input.split(" ")[0];
            String[] tokens;
            List<Product> result;
            switch (command) {
                case "AddProduct":
                    tokens = input.substring(11).split(";");
                    sb.append(shop.addProduct(tokens[0], Double.parseDouble(tokens[1]), tokens[2]))
                            .append(System.lineSeparator());
                    break;
                case "DeleteProducts":
                    tokens = input.substring(15).split(";");
                    if (tokens.length == 1) {
                        sb.append(shop.deleteProducts(tokens[0])).append(System.lineSeparator());
                    } else {
                        sb.append(shop.deleteProducts(tokens[0], tokens[1])).append(System.lineSeparator());
                    }
                    break;
                case "FindProductsByName":
                    result = shop.findProductsByName(input.substring(19));
                    getResult(result, sb);
                    break;
                case "FindProductsByProducer":
                    result = shop.findProductsByProducer(input.substring(23));
                    getResult(result, sb);
                    break;
                case "FindProductsByPriceRange":
                    tokens = input.substring(25).split(";");
                    double from = Double.parseDouble(tokens[0]);
                    double to = Double.parseDouble(tokens[1]);
                    result = shop.findProductsByPriceRange(from, to);
                    getResult(result, sb);
                    break;
            }
        }
        System.out.println(sb);
    }

    private static void getResult(List<Product> result, StringBuilder sb) {
        if (result == null || result.isEmpty()) {
            sb.append("No products found").append(System.lineSeparator());
        } else {
            result.sort(Comparator.naturalOrder());
            for (Product product : result) {
                sb.append("{").append(product.getName()).append(";")
                        .append(product.getProducer()).append(";")
                        .append(String.format("%.2f", product.getPrice()))
                        .append("}").append(System.lineSeparator());
            }
        }
    }
}
