import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CountSymbols {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        HashTable<Character, Integer> symbols = new HashTable<>();

        String text = reader.readLine();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (symbols.containsKey(currentChar)) {
                symbols.addOrReplace(currentChar, symbols.get(currentChar) + 1);
            } else {
                symbols.add(currentChar, 1);
            }
        }
        for (KeyValue<Character, Integer> pair : symbols.sortedPairs()) {
            System.out.printf("%s: %d time/s%n", pair.getKey(), pair.getValue());
        }
    }
}
