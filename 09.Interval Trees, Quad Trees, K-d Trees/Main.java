import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Item> items = new ArrayList<>();
        Map<String, Item> byId = new LinkedHashMap<>();
        int moves = 0;

        String command;
        while (!(command = reader.readLine()).equals("end")){
            String[] tokens = command.split(" ");
            switch (tokens[0]){
                case "add":
                    addItem(items, byId, tokens);
                    break;
                case "move":
                    String id = tokens[1];
                    int x = Integer.parseInt(tokens[2]);
                    int y = Integer.parseInt(tokens[3]);
                    byId.get(id).setX1(x);
                    byId.get(id).setY1(y);
                    moves++;
                    sweep(moves, items);
                    break;
                case "tick":
                    moves++;
                    sweep(moves, items);
                    break;
                case "start":
                    insertionSort(items);
                    break;
            }
        }
    }

    private static void sweep(int moves, List<Item> items) {
        insertionSort(items);
        for (int i = 0; i < items.size(); i++) {
            Item current = items.get(i);
            for (int j = i + 1; j < items.size(); j++) {
                Item candidate = items.get(j);
                if (candidate.getX1() > current.getX2()){
                    break;
                }
                if (current.intersect(candidate)){
                    System.out.printf("{%d} {%s} collides with {%s}%n"
                            , moves, current.getId(), candidate.getId());
                }
            }
        }
    }

    private static void insertionSort(List<Item> items) {
        for (int i = 1; i < items.size(); i++) {
           int j = i;
           while (j > 0 && items.get(j - 1).getX1() > items.get(j).getX1()){
               Item temp = items.get(j);
               items.set(j, items.get(j - 1));
               items.set(j - 1, temp);
               j--;
           }
        }
    }

    private static void addItem(List<Item> items, Map<String, Item> byId, String[] tokens) {
        String id = tokens[1];
        int x = Integer.parseInt(tokens[2]);
        int y = Integer.parseInt(tokens[3]);
        Item item = new Item(id, x , y);
        items.add(item);
        byId.put(id, item);
    }
}
