

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class KdTreeTests {

    @Test
    public void initialize_ShouldBeEmpty() {
        KdTree tree = new KdTree();

        List<Point2D> actual = new ArrayList<Point2D>();
        tree.eachInOrder(actual::add);
        Point2D[] result = new Point2D[actual.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = actual.get(i);
        }

        Point2D[] expected = new Point2D[]{};
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void insert_SinglePoint_TestRoot() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(5, 5));

        Point2D expected = tree.getRoot().getPoint2D();

        Assert.assertEquals(expected, new Point2D(5, 5));
    }

    @Test
    public void insert_SinglePoint_TestAllElements() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(5, 5));

        List<Point2D> actual = new ArrayList<Point2D>();
        tree.eachInOrder(actual::add);
        Point2D[] result = new Point2D[actual.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = actual.get(i);
        }

        Point2D[] expected = new Point2D[]
                {
                        new Point2D(5, 5),
                };

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void insert_MultiplePoints_TestContains() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(5, 5));
        tree.insert(new Point2D(3, 2));
        tree.insert(new Point2D(2, 6));
        tree.insert(new Point2D(8, 8));
        tree.insert(new Point2D(8, 9));

        Assert.assertTrue(tree.contains(new Point2D(5, 5)));
        Assert.assertTrue(tree.contains(new Point2D(3, 2)));
        Assert.assertTrue(tree.contains(new Point2D(2, 6)));
        Assert.assertTrue(tree.contains(new Point2D(8, 8)));
        Assert.assertTrue(tree.contains(new Point2D(8, 9)));
    }

    @Test
    public void insert_MultiplePoints_TestInOrder() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(5, 5));
        tree.insert(new Point2D(3, 2));
        tree.insert(new Point2D(2, 6));
        tree.insert(new Point2D(8, 8));
        tree.insert(new Point2D(8, 9));

        List<Point2D> actual = new ArrayList<Point2D>();
        tree.eachInOrder(actual::add);
        Point2D[] result = new Point2D[actual.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = actual.get(i);
        }

        Point2D[] expected = new Point2D[]
                {
                        new Point2D(3, 2),
                        new Point2D(2, 6),
                        new Point2D(5, 5),
                        new Point2D(8, 8),
                        new Point2D(8, 9),
                };

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void insert_Many_RandomSet() {
        Random rnd = new Random();

        KdTree tree = new KdTree();
        Set<Point2D> set = new HashSet<Point2D>();

        for (int i = 0; i < 10000; i++) {
            double x = rnd.nextDouble();
            double y = rnd.nextDouble();
            Point2D point = new Point2D(x, y);
            tree.insert(point);
            set.add(point);
        }

        for (Point2D point : set) {
            Assert.assertTrue(tree.contains(point));
        }
    }
}
