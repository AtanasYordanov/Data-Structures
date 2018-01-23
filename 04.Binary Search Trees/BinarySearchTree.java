import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> {
    private Node root;
    private int nodesCount;

    public BinarySearchTree() {
    }

    private BinarySearchTree(Node root) {
        this.preOrderCopy(root);
    }

    private void preOrderCopy(Node node) {
        if (node == null) {
            return;
        }
        this.insert(node.value);
        this.preOrderCopy(node.left);
        this.preOrderCopy(node.right);
    }

    public Node getRoot() {
        return this.root;
    }

    public int getNodesCount() {
        return this.nodesCount;
    }

    public int size() {
        return this.size(this.root);
    }

    public int size(Node node) {
        return node.childrenCount;
    }

    public void insert(T value) {
        if (this.root == null) {
            this.root = new Node(value);
            return;
        }
        Node parent = null;
        Node current = this.root;
        while (current != null) {
            parent = current;
            parent.childrenCount++;
            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                return;
            }
        }
        this.nodesCount++;
        Node newNode = new Node(value);
        if (value.compareTo(parent.value) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public boolean contains(T value) {
        Node current = this.root;
        while (current != null) {
            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                break;
            }
        }
        return current != null;
    }

    public BinarySearchTree<T> search(T item) {
        Node current = this.root;
        while (current != null) {
            if (item.compareTo(current.value) < 0) {
                current = current.left;
            } else if (item.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                break;
            }
        }
        return new BinarySearchTree<>(current);
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }
        this.eachInOrder(node.left, consumer);
        consumer.accept(node.value);
        this.eachInOrder(node.right, consumer);
    }

    public Iterable<T> range(T from, T to) {
        Deque<T> queue = new LinkedList<>();
        this.range(this.root, queue, from, to);
        return queue;
    }

    private void range(Node node, Deque<T> queue, T startRange, T endRange) {
        if (node == null) {
            return;
        }
        int compareStart = startRange.compareTo(node.value);
        int compareEnd = endRange.compareTo(node.value);
        if (compareStart < 0) {
            this.range(node.left, queue, startRange, endRange);
        }
        if (compareStart <= 0 && compareEnd >= 0) {
            queue.addLast(node.value);
        }
        if (compareEnd > 0) {
            this.range(node.right, queue, startRange, endRange);
        }
    }

    private T minValue(Node root) {
        T minValue = root.value;
        while (root.left != null) {
            minValue = root.left.value;
            root = root.left;
        }
        return minValue;
    }

    public void deleteMin() {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }
        Node min = this.root;
        Node parent = null;
        while (min.left != null) {
            parent = min;
            parent.childrenCount--;
            min = min.left;
        }
        if (parent == null) {
            this.root = this.root.right;
        } else {
            parent.left = min.right;
        }
        this.nodesCount--;
    }

    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }
        Node max = this.root;
        Node parent = null;
        while (max.right != null) {
            parent = max;
            parent.childrenCount--;
            max = max.right;
        }
        if (parent == null) {
            this.root = this.root.left;
        } else {
            parent.right = max.left;
        }
        this.nodesCount--;
    }

    public T ceil(T element) {
        if (this.root == null) {
            return null;
        }
        T closestValue = this.root.value;
        Node current = this.root;
        while (current != null) {
            if (current.value.compareTo(element) > 0
                    && current.value.compareTo(closestValue) < 0) {
                closestValue = current.value;
            } else if (closestValue.compareTo(element) < 0) {
                closestValue = current.value;
            }
            if (current.value.compareTo(element) > 0) {
                current = current.left;
            } else if (current.value.compareTo(element) < 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        if (closestValue.compareTo(element) < 0) {
            return null;
        }
        return closestValue;
    }

    public T floor(T element) {
        if (this.root == null) {
            return null;
        }
        T closestValue = this.root.value;
        Node current = this.root;
        while (current != null) {
            if (current.value.compareTo(element) < 0
                    && current.value.compareTo(closestValue) > 0) {
                closestValue = current.value;
            } else if (closestValue.compareTo(element) > 0) {
                closestValue = current.value;
            }
            if (current.value.compareTo(element) > 0) {
                current = current.left;
            } else if (current.value.compareTo(element) < 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        if (closestValue.compareTo(element) > 0) {
            return null;
        }
        return closestValue;
    }

    public void delete(T key) {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }
        if (!this.contains(key)) {
            return;
        }
        this.nodesCount--;
        Node current = this.root;
        Node parent = null;
        while (current != null) {
            if (current.value.compareTo(key) > 0) {
                parent = current;
                current = current.left;
            } else if (current.value.compareTo(key) < 0) {
                parent = current;
                current = current.right;
            } else {
                break;
            }
        }
        deleteElement(current, parent);
    }

    private void deleteElement(Node current, Node parent) {
        if (parent == null) {
            if (current.left == null && current.right == null) {
                this.root = current;
            } else if (current.left == null) {
                this.root = this.root.right;
            } else if (current.right == null) {
                this.root = this.root.left;
            } else {
                Node substitute = current.right;
                Node substituteParent = null;
                current.childrenCount--;
                while (substitute.left != null) {
                    substituteParent = substitute;
                    substitute = substitute.left;
                    substituteParent.childrenCount--;
                }
                this.root.value = substitute.value;
                if (substituteParent == null) {
                    this.root.right = null;
                } else {
                    substituteParent.left = null;
                }
            }
            return;
        }
        if (current.left == null && current.right == null) {
            if (parent.right == current) {
                parent.right = null;
            } else if (parent.left == current) {
                parent.left = null;
            }
        } else if (current.left == null) {
            current.value = current.right.value;
            current.right = null;
        } else if (current.right == null) {
            current.value = current.left.value;
            current.left = null;
        } else {
            Node substitute = current.right;
            Node substituteParent = null;
            current.childrenCount--;
            while (substitute.left != null) {
                substituteParent = substitute;
                substitute = substitute.left;
                substituteParent.childrenCount--;
            }
            current.value = substitute.value;
            if (substituteParent == null) {
                current.right = null;
            } else {
                substituteParent.left = null;
            }
        }
    }

    public int rank(T item) {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }
        int[] count = {0};
        rankDFS(this.root, item, count);
        return count[0];
    }

    private void rankDFS(Node node, T item, int[] count) {
        if (node == null) {
            return;
        }
        if (node.value.compareTo(item) < 0) {
            count[0]++;
            rankDFS(node.left, item, count);
            rankDFS(node.right, item, count);
        } else {
            rankDFS(node.left, item, count);
        }
    }

    public T select(int n) {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }
        return selectDFS(this.root, n, 0);
    }

    private T selectDFS(Node node, int n, int prevSmallerElements) {
        int leftChildren = node.left == null ? 0 : node.left.childrenCount;
        int smallerChildrenCount = prevSmallerElements + leftChildren;
        if (smallerChildrenCount > n) {
            if (node.left == null) {
                return null;
            }
            return selectDFS(node.left, n, 0);
        } else if (smallerChildrenCount < n) {
            if (node.right == null) {
                return null;
            }
            return selectDFS(node.right, n, smallerChildrenCount + 1);
        } else {
            return node.value;
        }
    }

    class Node {
        private T value;
        private Node left;
        private Node right;

        private int childrenCount;

        public Node(T value) {
            this.value = value;
            this.childrenCount = 1;
        }

        public T getValue() {
            return value;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }
        @Override
        public String toString() {
            return this.value + "";
        }
    }
}

