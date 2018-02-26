import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Phonebook {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        HashTable<String, String> phonebook = new HashTable<>();
        String input;
        while (!(input = reader.readLine()).equals("search")) {
            String[] tokens = input.split("-");
            String name = tokens[0];
            String phone = tokens[1];
            phonebook.addOrReplace(name, phone);
        }
        while (true) {
            input = reader.readLine();
            if (phonebook.containsKey(input)) {
                System.out.printf("%s -> %s%n", input, phonebook.get(input));
            } else {
                System.out.printf("Contact %s does not exist.%n", input);
            }
        }
    }
}
