package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class SequenceNM {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input = reader.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        if (n == m) {
            System.out.println(n);
            return;
        }
        if (m <= n) {
            return;
        }
        Item firstItem = new Item(n, null);
        Deque<Item> queue = new ArrayDeque<>();
        queue.offer(firstItem);
        while (!queue.isEmpty()) {
            Item currentItem = queue.poll();
            int currentNum = currentItem.getValue();
            Item item = new Item(currentNum + 1, currentItem);
            Item item1 = new Item(currentNum + 2, currentItem);
            Item item2 = new Item(currentNum * 2, currentItem);
            if (currentNum + 1 == m || currentNum + 2 == m || currentNum * 2 == m) {
                if (currentNum + 1 == m) {
                    printSolution(item);
                } else if (currentNum + 2 == m) {
                    printSolution(item1);
                } else {
                    printSolution(item2);
                }
                break;
            }
            queue.offer(item);
            queue.offer(item1);
            queue.offer(item2);
        }
    }

    private static void printSolution(Item item) {
        Deque<Integer> stack = new ArrayDeque<>();
        while (item != null) {
            stack.push(item.getValue());
            item = item.getPrev();
        }
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
            if (stack.size() > 0) {
                System.out.print(" -> ");
            }
        }
    }
}

class Item {
    private int value;
    private Item prev;

    Item(int value, Item prev) {
        this.value = value;
        this.prev = prev;
    }

    public int getValue() {
        return value;
    }

    public Item getPrev() {
        return prev;
    }
}
