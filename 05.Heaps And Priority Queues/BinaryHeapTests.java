import org.junit.Assert;
import org.junit.Test;

public class BinaryHeapTests {

    @Test
    public void insert_Single_TestCount() {
        // Arrange
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        // Act
        heap.insert(3);

        // Assert
        Assert.assertEquals(1, heap.size());
    }

    @Test
    public void insert_Single_TestPeek() {
        // Arrange
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        // Act
        heap.insert(3);

        // Assert
        Assert.assertEquals(Integer.valueOf(3), heap.peek());
    }

    @Test
    public void insert_Multiple_TestCount() {
        // Arrange
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        // Act
        // Assert
        heap.insert(3);
        Assert.assertEquals(1, heap.size());

        heap.insert(5);
        Assert.assertEquals(2, heap.size());

        heap.insert(6);
        Assert.assertEquals(3, heap.size());
    }

    @Test
    public void insert_Multiple_TestPeek() {
        // Arrange
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        // Act
        // Assert
        heap.insert(3);
        Assert.assertEquals(Integer.valueOf(3), heap.peek());

        heap.insert(5);
        Assert.assertEquals(Integer.valueOf(5), heap.peek());

        heap.insert(2);
        Assert.assertEquals(Integer.valueOf(5), heap.peek());

        heap.insert(1);
        Assert.assertEquals(Integer.valueOf(5), heap.peek());

        heap.insert(7);
        Assert.assertEquals(Integer.valueOf(7), heap.peek());

        heap.insert(8);
        Assert.assertEquals(Integer.valueOf(8), heap.peek());
    }

    @Test
    public void pull_Single_TestCount() {
        // Arrange
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        // Act
        heap.insert(3);
        heap.insert(5);

        heap.pull();

        // Assert
        Assert.assertEquals(1, heap.size());
    }

    @Test
    public void pull_Single_TestElement() {
        // Arrange
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        // Act
        heap.insert(3);

        // Assert
        Assert.assertEquals(Integer.valueOf(3), heap.pull());
    }

    @Test
    public void pull_Multiple_TestCount() {
        // Arrange
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        // Act
        heap.insert(5);
        heap.insert(3);
        heap.insert(1);

        // Assert
        Assert.assertEquals(Integer.valueOf(5), heap.pull());
        Assert.assertEquals(Integer.valueOf(3), heap.pull());
        Assert.assertEquals(Integer.valueOf(1), heap.pull());
        Assert.assertEquals(0, heap.size());
    }

    @Test
    public void pull_Multiple_TestElements() {
        // Arrange
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        // Act
        heap.insert(3);
        heap.insert(5);
        heap.insert(6);
        heap.insert(7);

        // Assert
        Assert.assertEquals(Integer.valueOf(7), heap.pull());
        Assert.assertEquals(Integer.valueOf(6), heap.pull());
        Assert.assertEquals(Integer.valueOf(5), heap.pull());
        Assert.assertEquals(Integer.valueOf(3), heap.pull());
    }

    @Test(expected = IllegalArgumentException.class)
    public void Pull_EmptyHeap() {
        // Arrange
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        // Act
        heap.pull();
    }
}
