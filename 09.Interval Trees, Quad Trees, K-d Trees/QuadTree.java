import java.util.ArrayList;
import java.util.List;

public class QuadTree<T extends Boundable> {

    public static final int DEFAULT_MAX_DEPTH = 5;

    private int maxDepth;

    private Node<T> root;

    private Rectangle bounds;

    private int count;

    public QuadTree(int width, int height) {
        this(width, height, DEFAULT_MAX_DEPTH);
    }

    public QuadTree(int width, int height, int maxDepth) {
        this.root = new Node<T>(0, 0, width, height);
        this.bounds = this.root.getBounds();
        this.maxDepth = maxDepth;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    private void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public int getCount() {
        return this.count;
    }

    private void setCount(int count) {
        this.count = count;
    }

    public boolean insert(T item) {
        if(!item.getBounds().isInside(this.bounds)) {
            return false;
        }
        int depth = 1;
        Node<T> currentNode = this.root;
        while (currentNode.getChildren() != null) {
            int quadrant = getQuadrant(currentNode, item.getBounds());
            if(quadrant == -1) {
                break;
            }
            currentNode = currentNode.getChildren()[quadrant];
            depth++;
        }
        currentNode.getItems().add(item);
        this.split(currentNode, depth);
        this.count++;

        return true;
    }

    private void split(Node<T> node, int depth) {
        if (!(node.shouldSplit() || depth < this.maxDepth)) {
            return;
        }

        int leftWidth = node.getBounds().getWidth() / 2;
        int rightWidth = node.getBounds().getWidth() - leftWidth;
        int topHeight = node.getBounds().getHeight() / 2;
        int bottomHeight = node.getBounds().getHeight() - topHeight;

        node.setChildren(new Node[4]);
        node.getChildren()[0] = new Node<>(node.getBounds().getMidX(), node.getBounds().getY1(), rightWidth, topHeight);
        node.getChildren()[1] = new Node<>(node.getBounds().getX1(), node.getBounds().getY1(), leftWidth, topHeight);
        node.getChildren()[2] = new Node<>(node.getBounds().getX1(), node.getBounds().getMidY(), leftWidth, bottomHeight);
        node.getChildren()[3] = new Node<>(node.getBounds().getMidX(), node.getBounds().getMidY(), rightWidth, bottomHeight);

        for (int i = 0; i < node.getItems().size(); i++) {
            T item = node.getItems().get(i);
            int quadrant = getQuadrant(node, item.getBounds());
            if (quadrant != -1) {
                node.getItems().remove(item);
                node.getChildren()[quadrant].getItems().add(item);
                i--;
            }
        }

        for (Node<T> child : node.getChildren()) {
            this.split(child, depth + 1);
        }
    }

    private int getQuadrant(Node<T> node, Rectangle bounds) {

        if (node.getChildren() == null) {
            return  -1;
        }

        for (int quadrant = 0; quadrant < 4; quadrant++) {
            if (bounds.isInside(node.getChildren()[quadrant].getBounds())) {
                return quadrant;
            }
        }
        return -1;
    }

    public List<T> report(Rectangle bounds) {
        List<T> collisionCandidates = new ArrayList<>();
        this.getCollisionCandidates(this.root, bounds, collisionCandidates);
        return collisionCandidates;
    }

    private void getCollisionCandidates(Node<T> node, Rectangle bounds, List<T> collisionCandidates) {
        int quadrant = this.getQuadrant(node, bounds);
        if(quadrant == -1) {
            this.getSubtreeContents(node, bounds, collisionCandidates);
        } else {
            if (node.getChildren() != null) {
                getCollisionCandidates(node.getChildren()[quadrant], bounds, collisionCandidates);
            }
            collisionCandidates.addAll(node.getItems());
        }
    }

    private void getSubtreeContents(Node<T> node, Rectangle bounds, List<T> collisionCandidates) {
        if (node.getChildren() != null) {
            for (Node<T> child : node.getChildren()) {
                if (child.getBounds().intersects(bounds)) {
                    this.getSubtreeContents(child, bounds, collisionCandidates);
                }
            }
        }
        collisionCandidates.addAll(node.getItems());
    }
}