package Exercises;

import java.util.Scanner;

public class Main {

    private static char[][] map =
            {
                    {'-', '-', '-', '-', '-', '-', '-', '-'},
                    {'-', '-', '*', '-', '-', '-', '-', '-'},
                    {'W', 'W', 'W', 'W', 'W', '-', '-', '-'},
                    {'-', '-', '-', '-', 'W', 'W', '-', '-'},
                    {'-', '-', '-', 'P', 'W', '-', '-', '-'},
                    {'-', '-', '-', '-', '-', '-', '-', '-'}
            };

    public static void main(String[] args) {
        // map = readMap();

        Node start = findGoal('P');
        Node goal = findGoal('*');

        AStar aStar = new AStar(map);
        Iterable<Node> path = aStar.getPath(start, goal);

        for (Node node : path) {
            int row = node.getRow();
            int col = node.getCol();
            map[row][col] = map[row][col] == '-' ? '@' : map[row][col];
        }

        printMap();
    }

    private static char[][] readMap() {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());

        char[][] map = new char[n][n];
        for (int i = 0; i < map.length; i++) {
            char[] line = in.nextLine().toCharArray();
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = line[j];
            }
        }

        return map;
    }

    static Node findGoal(char goal) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == goal) {
                    return new Node(row, col);
                }
            }
        }

        throw new IllegalArgumentException("Object not present on map");
    }

    static void printMap() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                System.out.print(map[row][col] + " ");
            }
            System.out.println();
        }
    }
}