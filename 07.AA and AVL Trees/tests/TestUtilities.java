package tests;

import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class TestUtilities {
    public static <T> void assertListEquals(T[] list, Iterable<T> iterable) {
        ArrayList<T> secondList = new ArrayList<T>();
        for(T element : iterable) {
            secondList.add(element);
        }
        assertEquals(list.length, secondList.size());
        for (int i = 0; i < list.length; i++) {
            assertEquals(list[i], secondList.get(i));
        }
    }

    public static <T> void assertListEquals(T[] list, Stream<T> stream) {
        assertListEquals(list, stream::iterator);
    }

    public static <T> Stream<T> iterableToStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
