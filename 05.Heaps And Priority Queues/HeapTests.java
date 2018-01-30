import org.junit.Assert;
import org.junit.Test;

public class HeapTests {

    @Test
    public void sort_SingleElement() {
        // Arrange
        Integer[] arr = new Integer[] { 5 };

        // Act
        Heap.sort(arr);

        // Assert
        Integer[] exp = new Integer[] { 5 };
        Assert.assertArrayEquals(exp, arr);
    }

    @Test
    public void sort_TwoElements() {
        // Arrange
        Integer[] arr = new Integer[] { 5, 1 };

        // Act
        Heap.sort(arr);

        // Assert
        Integer[] exp = new Integer[] { 1, 5 };
        Assert.assertArrayEquals(exp, arr);
    }

    @Test
    public void sort_MultipleElements() {
        // Arrange
        Integer[] arr = new Integer[1000];
        int element = arr.length - 1;
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = element--;
        }

        // Act
        Heap.sort(arr);

        // Assert
        Integer[] exp = new Integer[1000];
        for (int i = 0; i < exp.length; i++)
        {
            exp[i] = i;
        }

        Assert.assertArrayEquals(exp, arr);
    }

    @Test
    public void sort_NegativeElements() {
        // Arrange
        Integer[] arr = new Integer[] { 5, 1, -2 };

        // Act
        Heap.sort(arr);

        // Assert
        Integer[] exp = new Integer[] { -2, 1, 5 };
        Assert.assertArrayEquals(exp, arr);
    }


    @Test
    public void sort_EmptyArray() {
        // Arrange
        Integer[] arr = new Integer[] { };

        // Act
        Heap.sort(arr);

        // Assert
        Integer[] exp = new Integer[] { };
        Assert.assertArrayEquals(exp, arr);
    }
}
