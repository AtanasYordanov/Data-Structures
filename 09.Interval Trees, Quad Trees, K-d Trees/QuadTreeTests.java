import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QuadTreeTests {

    private static final int OriginX = 0;
    private static final int OriginY = 0;
    private static final int WorldWidth = 200;
    private static final int WorldHeight = 200;

    @Test
    public void TestMethod1() {
        QuadTree<TestBox> quadTree = new QuadTree<>(WorldWidth, WorldHeight);

        TestBox[] items = new TestBox[]
                {
                        new TestBox(50, 50),
                        new TestBox(50, 50),
                        new TestBox(50, 50)
                };

        for (TestBox testBox : items) {
            quadTree.insert(testBox);
        }

        Assert.assertEquals(items.length, quadTree.getCount());
    }

    private Rectangle GetSubquadrantBounds(int... quadrants) {
        int x = OriginX;
        int y = OriginY;
        int width = WorldWidth;
        int height = WorldHeight;

        for (int i = 0; i < quadrants.length; i++) {
            width /= 2;
            height /= 2;

            int quadrant = quadrants[i];
            if (quadrant == 1) {
                x += width;
            } else if (quadrant == 3) {
                y += height;
            } else if (quadrant == 4) {
                x += width;
                y += height;
            }
        }

        Rectangle bounds = new Rectangle(x, y, width, height);

        return bounds;
    }

    @Test
    public void TestMethod2() {
        QuadTree<TestBox> quadTree = new QuadTree<>(WorldWidth, WorldHeight);

        TestBox[] items = new TestBox[]
                {
                        new TestBox(10, 0),
                        new TestBox(110, 0),
                        new TestBox(10, 110),
                        new TestBox(50, 0)
                };

        for (TestBox testBox : items) {
            quadTree.insert(testBox);
        }

        Rectangle fourthQuadrant = GetSubquadrantBounds(4);

        List<TestBox> elements = quadTree.report(fourthQuadrant);
        Assert.assertEquals(0, elements.size());
    }

    @Test
    public void TestMethod3() {
        QuadTree<TestBox> quadTree = new QuadTree<>(WorldWidth, WorldHeight);

        List<List<TestBox>> items = new ArrayList<>();

        List<TestBox> t1 = new ArrayList<>();
        t1.add(new TestBox(110, 0));

        List<TestBox> t2 = new ArrayList<>();
        t2.add(new TestBox(10, 0));

        List<TestBox> t3 = new ArrayList<>();
        t3.add(new TestBox(10, 0));
        t3.add(new TestBox(10, 110));

        items.add(t1);
        items.add(t2);
        items.add(t3);

        for (List<TestBox> item : items) {
            for (TestBox testBox : item) {
                quadTree.insert(testBox);
            }
        }

        Rectangle firstQuadrant = GetSubquadrantBounds(1);
        List<TestBox> elementsFirst = quadTree.report(firstQuadrant);
        int index = 0;
        for (TestBox testBox : items.get(0)) {
            Assert.assertEquals(testBox, elementsFirst.get(index++));
        }

        Rectangle secondQuadrant = GetSubquadrantBounds(2);
        List<TestBox> elementsSecond = quadTree.report(secondQuadrant);
        index = 0;
        for (TestBox testBox : items.get(1)) {
            Assert.assertEquals(testBox, elementsSecond.get(index++));
        }

        Rectangle thirdQuadrant = GetSubquadrantBounds(3);
        List<TestBox> elementsThird = quadTree.report(thirdQuadrant);
        index = 0;
        for (TestBox testBox : items.get(2)) {
            Assert.assertEquals(testBox, elementsThird.get(index++));
        }
    }

    @Test
    public void TestMethod4() {
        QuadTree<TestBox> quadTree = new QuadTree<>(WorldWidth, WorldHeight);

        List<TestBox> items = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            items.add(new TestBox(2, 2, 1, 1));
        }

        for (TestBox testBox : items) {
            quadTree.insert(testBox);
        }
    }
}
