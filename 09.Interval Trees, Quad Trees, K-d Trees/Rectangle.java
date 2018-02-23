public class Rectangle {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int width;
    private int height;
    private int midX;
    private int midY;

    public Rectangle(int x1, int y1, int width, int height) {
        this.setX1(x1);
        this.setY1(y1);
        this.setX2(x1 + width);
        this.setY2(y1 + height);

        this.width = width;
        this.height = height;
    }

    public int getX1() {
        return this.x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return this.y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return this.x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return this.y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getMidX() { return this.x1 + this.width / 2; }

    public int getMidY() { return this.y1 + this.height / 2; }

    public boolean intersects(Rectangle other) {
        return this.getX1() <= other.getX2() &&
                other.getX1() <= this.getX2() &&
                this.getY1() <= other.getY2() &&
                other.getY1() <= this.getY2();
    }

    public boolean isInside(Rectangle other) {
        return this.getX2() <= other.getX2() &&
                this.getX1() >= other.getX1() &&
                this.getY1() >= other.getY1() &&
                this.getY2() <= other.getY2();
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) .. (%d, %d)",
                this.getX1(), this.getY1(), this.getX2(), this.getY2());
    }
}