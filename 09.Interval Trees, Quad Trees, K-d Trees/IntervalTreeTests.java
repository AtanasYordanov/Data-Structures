
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class IntervalTreeTests {

    @Test
    public void initialTree_ShouldBeEmpty() {
        // Arrange
        IntervalTree tree = new IntervalTree();

        // Act
        List<Interval> actual = new ArrayList<Interval>();
        tree.eachInOrder(actual::add);
        Interval[] result = new Interval[actual.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = actual.get(i);
        }

        // Assert
        Interval[] expected = new Interval[]{};
        Assert.assertArrayEquals(expected, result);
    }


    @Test
    public void insert_Multiple() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        Interval[] expected = new Interval[]
                {
                        new Interval(0, 1),
                        new Interval(3, 41),
                        new Interval(10, 15),
                        new Interval(20, 36),
                        new Interval(25, 30),
                        new Interval(29, 99),
                        new Interval(60, 72),
                };

        // Act
        List<Interval> actual = new ArrayList<Interval>();
        tree.eachInOrder(actual::add);
        Interval[] result = new Interval[actual.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = actual.get(i);
        }

        // Assert
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void searchAny_Hit_Root_Low() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        // Act
        // Assert
        Assert.assertEquals(new Interval(20, 36), tree.searchAny(10, 25));
    }

    @Test
    public void searchAny_Hit_Root_High() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        // Act
        // Assert
        Assert.assertEquals(new Interval(20, 36), tree.searchAny(30, 40));
    }

    @Test
    public void searchAny_Hit_Root_Wrap() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        // Act
        // Assert
        Assert.assertEquals(new Interval(20, 36), tree.searchAny(15, 40));
    }

    @Test
    public void searchAny_Hit_Leaf_High() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        // Act
        // Assert
        Assert.assertEquals(new Interval(0, 1), tree.searchAny(0, 2));
    }

    @Test
    public void searchAny_Hit_Leaf_Center() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        // Act
        // Assert
        Assert.assertEquals(new Interval(0, 1), tree.searchAny(0, 1));
    }

    @Test
    public void searchAny_Hit_Leaf_Wrap() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        // Act
        // Assert
        Assert.assertEquals(new Interval(0, 1), tree.searchAny(-1, 2));
    }

    @Test
    public void searchAny_Miss_TouchingIntervals() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(1, 2);
        tree.insert(2, 4);
        tree.insert(3, 5);

        // Act
        // Assert
        Assert.assertNull(tree.searchAny(5, 8));
    }

    @Test
    public void searchAny_Miss_NonTouchingIntervals() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(1, 2);
        tree.insert(2, 4);
        tree.insert(3, 5);

        // Act
        // Assert
        Assert.assertNull(tree.searchAny(12, 17));
    }

    @Test
    public void searchAll_Miss_Left() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        // Act
        Iterable<Interval> intervals = tree.searchAll(-2, -1);
        List<Interval> found = new ArrayList<>();
        for (Interval interval : intervals) {
            found.add(interval);
        }

        Interval[] result = new Interval[found.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = found.get(i);
        }

        Interval[] expected = new Interval[]{};

        // Assert
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void searchAll_Miss_Right() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        // Act
        Iterable<Interval> intervals = tree.searchAll(120, 140);
        List<Interval> found = new ArrayList<>();
        for (Interval interval : intervals) {
            found.add(interval);
        }

        Interval[] result = new Interval[found.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = found.get(i);
        }

        Interval[] expected = new Interval[]{};

        // Assert
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void searchAll_Hit_Single() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(1, 2);
        tree.insert(2, 4);
        tree.insert(3, 5);

        Interval[] expected = new Interval[]
                {
                        new Interval(3, 5)
                };

        // Act
        Iterable<Interval> intervals = tree.searchAll(4, 6);
        List<Interval> found = new ArrayList<>();
        for (Interval interval : intervals) {
            System.out.println(interval);
            found.add(interval);
        }

        Interval[] result = new Interval[found.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = found.get(i);
        }

        // Assert
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void searchAll_Hit_Two() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(1, 2);
        tree.insert(2, 4);
        tree.insert(3, 5);

        Interval[] expected = new Interval[]
                {
                        new Interval(2, 4),
                        new Interval(3, 5),
                };

        // Act
        Iterable<Interval> intervals = tree.searchAll(3, 6);
        List<Interval> found = new ArrayList<>();
        for (Interval interval : intervals) {
            found.add(interval);
        }

        Interval[] result = new Interval[found.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = found.get(i);
        }

        // Assert
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void searchAll_Hit_Multiple() {
        // Arrange
        IntervalTree tree = new IntervalTree();
        tree.insert(20, 36);
        tree.insert(3, 41);
        tree.insert(29, 99);
        tree.insert(0, 1);
        tree.insert(10, 15);
        tree.insert(25, 30);
        tree.insert(60, 72);

        Interval[] expected = new Interval[]
                {
                        new Interval(3, 41),
                        new Interval(10, 15),
                        new Interval(20, 36),
                        new Interval(25, 30),
                        new Interval(29, 99),
                };

        // Act
        Iterable<Interval> intervals = tree.searchAll(10, 50);
        List<Interval> found = new ArrayList<>();
        for (Interval interval : intervals) {
            found.add(interval);
        }

        Interval[] result = new Interval[found.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = found.get(i);
        }

        for (Interval interval : result) {
            System.out.println(interval);
        }

        // Assert
        Assert.assertArrayEquals(expected, result);
    }
}
