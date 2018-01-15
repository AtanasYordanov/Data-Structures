package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SumAndAverage {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> numbers = Arrays.stream(reader.readLine().split(" "))
                .map(Integer::valueOf).collect(Collectors.toList());

        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }
        double average = 1.0 * sum / numbers.size();
        System.out.printf("Sum=%d; Average=%.2f%n", sum, average);
    }
}
