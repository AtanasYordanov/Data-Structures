package Exercises;

import java.util.*;

public class AStar {

    private char[][] map;
    private PriorityQueue<Node> queue;

    public AStar(char[][] map) {
        this.map = map;
        this.queue = new PriorityQueue<>();
    }

    public static int getH(Node current, Node goal) {
        int currentRow = current.getRow();
        int currentCol = current.getCol();
        int targetRow = goal.getRow();
        int targetCol = goal.getCol();
        return Math.abs(currentRow - targetRow) + Math.abs(currentCol - targetCol);
    }

    public Iterable<Node> getPath(Node start, Node goal) {
        Map<Node, Node> parentMap = new LinkedHashMap<>();
        parentMap.put(start, null);
        this.queue.enqueue(start);
        while (this.queue.size() > 0) {
            Node current = this.queue.dequeue();
            int currentRow = current.getRow();
            int currentCol = current.getCol();
            if (current.equals(goal)) {
                List<Node> path = new ArrayList<>();
                Node temp = goal;
                path.add(temp);
                while (!temp.equals(start)) {
                    temp = parentMap.get(temp);
                    path.add(temp);
                }
                Collections.reverse(path);
                return path;
            }
            checkNeighbour(currentRow + 1, currentCol, current, goal, parentMap);
            checkNeighbour(currentRow - 1, currentCol, current, goal, parentMap);
            checkNeighbour(currentRow, currentCol + 1, current, goal, parentMap);
            checkNeighbour(currentRow, currentCol - 1, current, goal, parentMap);
        }
        List<Node> path = new ArrayList<>();
        path.add(null);
        return path;
    }

    private void checkNeighbour(int row, int col, Node current, Node goal, Map<Node, Node> parentMap) {
        Node node = new Node(row, col);
        if (row >= 0 && col >= 0 && row < this.map.length && col < this.map[row].length
                && this.map.length > row && this.map[row][col] != 'W' && !parentMap.containsKey(node)) {
            node.setG(current.getG() + 1);
            node.setF(node.getG() + getH(node, goal));
            this.queue.enqueue(node);
            parentMap.put(node, current);
        }
    }
}
