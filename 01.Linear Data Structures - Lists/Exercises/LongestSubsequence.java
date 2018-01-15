package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LongestSubsequence {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> numbers = Arrays.stream(reader.readLine().split(" "))
                .map(Integer::valueOf).collect(Collectors.toList());

        int maxCount = 1;
        int value = numbers.get(0);
        int currentCount = 1;

        for (int i = 1; i < numbers.size(); i++) {
            int currentNumber = numbers.get(i);
            int prevNumber = numbers.get(i - 1);
            if (currentNumber == prevNumber) {
                currentCount++;
                if (currentCount > maxCount){
                    maxCount = currentCount;
                    value = currentNumber;
                }
            } else {
                currentCount = 1;
            }
        }
        for (int i = 0; i < maxCount; i++) {
            System.out.print(value + " ");
        }
    }
}
