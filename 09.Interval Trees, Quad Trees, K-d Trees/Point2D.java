

public class Point2D implements Comparable<Point2D> {

    private double X;
    private double Y;

    public Point2D(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public double getX() {
        return this.X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return this.Y;
    }

    public void setY(double y) {
        Y = y;
    }

    @Override
    public int compareTo(Point2D that) {
        if (this.Y < that.Y) return -1;
        if (this.Y > that.Y) return +1;
        if (this.X < that.X) return -1;
        if (this.X > that.X) return +1;

        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        Point2D that = (Point2D)obj;
        return this.X == that.X && this.Y == that.Y;
    }

    @Override
    public int hashCode() {
        int hashX = Double.valueOf(this.X).hashCode();
        int hashY = Double.valueOf(this.Y).hashCode();
        return 31 * hashX + hashY;
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", this.X, this.Y);
    }
}
