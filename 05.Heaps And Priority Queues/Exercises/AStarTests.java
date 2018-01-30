package Exercises;

import org.junit.Assert;
import org.junit.Test;

public class AStarTests {

    @Test
    public void getHCost_Straight_Horizontal() {
        // Arrange
         Node current = new Node(0, 0);
         Node goal = new Node(0, 5);

        // Act
        int h =  AStar.getH(current, goal);

        // Assert
        Assert.assertEquals(5, h);
    }

    @Test
    public void getHCost_Straight_Vertical() {
        // Arrange
         Node current = new Node(0, 0);
         Node goal = new Node(5, 0);

        // Act
        int h =  AStar.getH(current, goal);

        // Assert
        Assert.assertEquals(5, h);
    }

    @Test
    public void getHCost_Diagonal() {
        // Arrange
         Node current = new Node(0, 0);
         Node goal = new Node(5, 5);

        int h =  AStar.getH(current, goal);

        Assert.assertEquals(10, h);
    }

    @Test
    public void aStarSearch_StraightHorizontal() {
        // Arrange
        char[][] map =
                {
                        {'P', '-', '-', '*'},
                        {'-', '-', '-', '-'},
                        {'-', '-', '-', '-'},
                        {'-', '-', '-', '-'},
                };

        // Act
         AStar aStar = new AStar(map);
        Iterable<Node> path = aStar.getPath(new Node(0, 0), new Node(0, 3));
         Node[] result = new Node[4];
        int index = 0;
        for ( Node node : path) {
            result[index++] = node;
        }

        // Assert
         Node[] expected = new Node[4];
        expected[0] = (new Node(0, 0));
        expected[1] = (new Node(0, 1));
        expected[2] = (new Node(0, 2));
        expected[3] = (new Node(0, 3));

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void aStarSearch_StraightVertical() {
        // Arrange
        char[][] map =
                {
                        {'P', '-', '-', '-'},
                        {'-', '-', '-', '-'},
                        {'-', '-', '-', '-'},
                        {'*', '-', '-', '-'},
                };

        // Act
         AStar aStar = new AStar(map);
        Iterable<Node> path = aStar.getPath(new Node(0, 0), new Node(3, 0));

         Node[] result = new Node[4];
        int index = 0;
        for ( Node node : path) {
            result[index++] = node;
        }

        // Assert
         Node[] expected = new Node[4];
        expected[0] = new Node(0, 0);
        expected[1] = new Node(1, 0);
        expected[2] = new Node(2, 0);
        expected[3] = new Node(3, 0);

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void aStarSearch_TestFirstMap() {
        // Arrange
        char[][] map =
                {
                        {'-', '-', '-', '-', '-'},
                        {'-', '-', '*', '-', '-'},
                        {'-', 'W', 'W', 'W', '-'},
                        {'-', '-', '-', '-', '-'},
                        {'-', '-', '-', 'P', '-'},
                        {'-', '-', '-', '-', '-'}
                };

        // Act
         AStar aStar = new AStar(map);
        Iterable<Node> path = aStar.getPath(new Node(4, 3), new Node(1, 2));
         Node[] result = new Node[7];
        int index = 0;
        for ( Node node : path) {
            result[index++] = node;
        }

        // Assert
         Node[] expected = new Node[7];

        expected[0] = new Node(4, 3);
        expected[1] = new Node(3, 3);
        expected[2] = new Node(3, 4);
        expected[3] = new Node(2, 4);
        expected[4] = new Node(1, 4);
        expected[5] = new Node(1, 3);
        expected[6] = new Node(1, 2);

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void aStarSearch_TestSecondMap() {
        // Arrange
        char[][] map =
                {
                        {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                        {'-', '-', '-', 'W', '*', '-', '-', '-', '-', '-', '-'},
                        {'-', '-', '-', 'W', 'W', 'W', 'W', 'W', '-', '-', '-'},
                        {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                        {'-', '-', '-', '-', '-', '-', '-', 'P', '-', '-', '-'},
                        {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'}
                };

         AStar aStar = new AStar(map);

        Iterable<Node> path = aStar.getPath(new Node(4, 7), new Node(1, 4));
         Node[] result = new Node[9];
        int index = 0;
        for ( Node node : path) {
            result[index++] = node;
        }

         Node[] expected = new Node[9];
        expected[0] = new Node(4, 7);
        expected[1] = new Node(3, 7);
        expected[2] = new Node(3, 8);
        expected[3] = new Node(2, 8);
        expected[4] = new Node(1, 8);
        expected[5] = new Node(1, 7);
        expected[6] = new Node(1, 6);
        expected[7] = new Node(1, 5);
        expected[8] = new Node(1, 4);

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void aStarSearch_TestThirdMap() {
        // Arrange
        char[][] map =
                {
                        {'W', 'W', 'W', 'W', 'W', 'W'},
                        {'-', '-', '-', '-', '-', '-'},
                        {'P', 'W', 'W', 'W', 'W', '-'},
                        {'-', '-', 'W', 'W', 'W', '*'},
                        {'-', '-', 'W', 'W', 'W', '-'},
                        {'-', '-', '-', '-', '-', '-'}
                };

        // Act
         AStar aStar = new AStar(map);
        Iterable<Node> path = aStar.getPath(new Node(2, 0), new Node(3, 5));
         Node[] result = new Node[9];
        int index = 0;
        for ( Node node : path) {
            result[index++] = node;
        }

        // Assert
         Node[] expected = new Node[]{
                new Node(2, 0),
                new Node(1, 0),
                new Node(1, 1),
                new Node(1, 2),
                new Node(1, 3),
                new Node(1, 4),
                new Node(1, 5),
                new Node(2, 5),
                new Node(3, 5),
        };

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void aStarSearch_TestFourthMap() {
        // Arrange
        char[][] map =
            {
                    { 'P', '-', '-', 'W', '-' },
                    { '-', 'W', '-', 'W', '-' },
                    { '-', '-', 'W', 'W', '-' },
                    { '-', 'W', '-', 'W', '*' },
                    { '-', '-', '-', '-', '-' }

            };

        // Act
         AStar aStar = new AStar(map);
        Iterable<Node> path = aStar.getPath(new Node(0, 0), new Node(3, 4));
         Node[] result = new Node[10];
        int index = 0;
        for ( Node node : path) {
            result[index++] = node;
        }

        // Assert
         Node[] expected = new Node[]{
            new Node(0, 0),
            new Node(1, 0),
            new Node(2, 0),
            new Node(3, 0),
            new Node(4, 0),
            new Node(4, 1),
            new Node(4, 2),
            new Node(4, 3),
            new Node(4, 4),
            new Node(3, 4),
        };

        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void aStarSearch_TestNoPath() {
        // Arrange
        char[][] map =
            {
                    { 'P', 'W', '-' },
                    { 'W', 'W', '-' },
                    { '-', '-', 'W' },
                    { '-', 'W', '-' },
                    { '-', '-', '*' }

            };

        // Act
         AStar aStar = new AStar(map);
        Iterable<Node> path = aStar.getPath(new Node(0, 0), new Node(3, 4));
        Node next = path.iterator().next();
        Assert.assertEquals(null, next);
    }
}
