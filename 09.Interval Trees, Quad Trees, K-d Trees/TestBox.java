public class TestBox implements Boundable, Comparable<TestBox> {

    public static final int WIDTH = 10;

    public static final int HEIGHT = 10;

    public static int ID_COUNTER;

    private int id;

    private Rectangle bounds;

    public TestBox(int x, int y) {
        this(x, y, WIDTH, HEIGHT);
    }

    public TestBox(int x, int y, int width, int height) {
        this.id = ID_COUNTER;
        ID_COUNTER++;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    @Override
    public int compareTo(TestBox o) {
        return Integer.compare(this.id, o.getId());
    }
}