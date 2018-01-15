package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CountOfOccurences {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] numbers = Arrays.stream(reader.readLine().split(" "))
                .sorted().mapToInt(Integer::valueOf).toArray();
        Set<Integer> uniqueNums = new HashSet<>();

        for (int number : numbers) {
            if (!uniqueNums.contains(number)) {
                int count = 0;
                uniqueNums.add(number);
                for (int i : numbers) {
                    if (number == i) {
                        count++;
                    }
                }
                System.out.printf("%d -> %d times%n", number, count);
            }
        }
    }
}
