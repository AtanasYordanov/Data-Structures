import org.junit.Assert;
import org.junit.Test;

public class LinkedListTests {

    @Test
    public void addFirstShouldIncreaseCount() {
        LinkedList<Integer> list = new LinkedList<>();

        list.addFirst(1);

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void addLastShouldIncreaseCount() {
        LinkedList<Integer> list = new LinkedList<>();

        list.addLast(1);

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void addFirstShouldAddElement() {
        LinkedList<Integer> list = new LinkedList<>();

        list.addFirst(1);

        for (Integer item : list) {
            Assert.assertEquals(Integer.valueOf(1), item);
        }
    }

    @Test
    public void AddLastShouldAddElement() {
        LinkedList<Integer> list = new LinkedList<>();

        list.addLast(1);

        for (Integer item : list) {
            Assert.assertEquals(Integer.valueOf(1), item);
        }
    }

    @Test
    public void addFirstMultipleElementsShouldAddElement() {
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            list.addFirst(i);
        }

        int expected = 99;
        for (Integer item : list) {
            Assert.assertEquals(expected--, list.size());
        }
    }

    @Test
    public void addLastMultipleElementsShouldAddElement() {
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            list.addLast(i);
        }

        int expected = 0;
        for (Integer item : list) {
            Assert.assertEquals(expected++, list.size());
        }
    }

    @Test
    public void removeFirstSingleElementShouldDecreaseCount() {
        LinkedList<Integer> list = new LinkedList<>();

        list.addFirst(1);
        list.addFirst(2);
        list.removeFirst();

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void RemoveLastSingleElementShouldDecreaseCount() {
        LinkedList<Integer> list = new LinkedList<>();

        list.addFirst(1);
        list.addFirst(2);
        list.removeLast();

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void RemoveFirstMultipleElementsShouldRemove() {
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            list.addLast(i);
        }

        for (Integer i = 0; i < 100; i++) {
            Assert.assertEquals(i, list.removeFirst());
        }

        Assert.assertEquals(0, list.size());
    }

    @Test
    public void RemoveLastMultipleElementsShouldRemove() {
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            list.addFirst(i);
        }

        for (Integer i = 0; i < 100; i++) {
            Assert.assertEquals(i, list.removeLast());
        }

        Assert.assertEquals(0, list.size());
    }

}
