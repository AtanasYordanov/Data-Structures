package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortWords {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<String> words = Arrays.asList(reader.readLine().split(" "));
        words.sort(Comparator.naturalOrder());
        System.out.println(String.join(" ", words));
    }
}
