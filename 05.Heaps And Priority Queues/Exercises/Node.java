package Exercises;

public class Node implements Comparable<Node> {

    private int row;
    private int col;
    private int f;
    private int g;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        this.g = 0;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getF() {
        return this.f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }

    @Override
    public boolean equals(Object obj) {
        Node other = (Node)obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + ((Integer)this.row).hashCode();
        hash = 31 * hash + ((Integer)this.col).hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return this.row + " " + this.col;
    }
}
