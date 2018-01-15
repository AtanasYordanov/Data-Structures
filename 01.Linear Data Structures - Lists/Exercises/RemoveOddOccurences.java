package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveOddOccurences {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> numbers = Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt).collect(Collectors.toList());

        for (Integer num : numbers) {
            int count = 0;
            for (Integer number : numbers) {
                if (num.equals(number)) {
                    count++;
                }
            }
            if (count % 2 == 0) {
                System.out.print(num + " ");
            }
        }
    }
}
