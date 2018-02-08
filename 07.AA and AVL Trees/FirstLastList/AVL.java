package FirstLastList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AVL<T extends Comparable<T>> {

    private Node<T> root;

    public Node<T> getRoot() {
        return this.root;
    }

    public boolean contains(T item) {
        Node<T> node = this.search(this.root, item);
        return node != null;
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }
        this.eachInOrder(node.left, action);
        action.accept(node.value);
        this.eachInOrder(node.right, action);
    }

    public void insert(T item) {
        this.root = this.insert(this.root, item);
    }

    private Node<T> insert(Node<T> node, T item) {
        if (node == null) {
            return new Node<>(item);
        }
        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            node.left = this.insert(node.left, item);
        } else if (cmp > 0) {
            node.right = this.insert(node.right, item);
        } else {
            Node<T> newNode = new Node<>(item);
            Node<T> current = node;
            while (current.getDown() != null) {
                current = current.getDown();
            }
            current.setDown(newNode);
        }
        node = this.balance(node);
        this.updateHeight(node);
        return node;
    }

    private Node<T> search(Node<T> node, T item) {
        if (node == null) {
            return null;
        }
        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            return search(node.left, item);
        } else if (cmp > 0) {
            return search(node.right, item);
        }
        return node;
    }

    public void delete(T item) {
        this.root = delete(this.root, item);
    }

    private Node<T> delete(Node<T> node, T item) {
        if (node == null) {
            return null;
        }
        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            node.left = delete(node.left, item);
        } else if (cmp > 0) {
            node.right = delete(node.right, item);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node<T> minNode = this.findSmallestNode(node.right);
                minNode.right = this.deleteMin(node.right);
                minNode.left = node.left;
                node = minNode;
            }
        }
        node = this.balance(node);
        this.updateHeight(node);
        return node;
    }

    private Node<T> findSmallestNode(Node<T> element) {
        if (element == null) {
            return null;
        }
        if (element.left == null) {
            return element;
        }
        return this.findSmallestNode(element.left);
    }

    public void deleteMin() {
        if (this.root == null) {
            return;
        }
        this.root = this.deleteMin(this.root);
    }

    public Node<T> deleteMin(Node<T> current) {
        if (current.getLeft() == null) {
            return current.right;
        }
        current.left = deleteMin(current.left);
        current = this.balance(current);
        this.updateHeight(current);
        return current;
    }

    public void deleteMax() {
        if (this.root == null) {
            return;
        }
        this.root = deleteMax(this.root);
    }

    public Node<T> deleteMax(Node<T> current) {
        if (current.getRight() == null) {
            return current.left;
        }
        current.right = deleteMin(current.right);
        current = this.balance(current);
        this.updateHeight(current);
        return current;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());
        leftNode.setRight(node);
        this.updateHeight(node);
        return leftNode;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRight();
        node.setRight(rightNode.getLeft());
        rightNode.setLeft(node);
        this.updateHeight(node);
        return rightNode;
    }

    private void updateHeight(Node<T> node) {
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);
    }

    private int getHeight(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    private Node<T> balance(Node<T> node) {
        int balance = getHeight(node.getLeft()) - getHeight(node.getRight());
        if (balance > 1) {
            int childBalance = getHeight(node.getLeft().getLeft()) - getHeight(node.getLeft().getRight());
            if (childBalance < 0) {
                node.setLeft(this.rotateLeft(node.getLeft()));
            }
            return this.rotateRight(node);
        } else if (balance < -1) {
            int childBalance = getHeight(node.getRight().getLeft()) - getHeight(node.getRight().getRight());
            if (childBalance > 0) {
                node.setRight(this.rotateRight(node.getRight()));
            }
            return this.rotateLeft(node);
        }
        return node;
    }

    public List<T> min(int count) {
        List<T> minList = new ArrayList<>();
        this.minRecursion(this.root, minList, count);
        return minList;
    }

    private void minRecursion(Node<T> node, List<T> minList, int count) {
        if (node == null) {
            return;
        }
        this.minRecursion(node.getLeft(), minList, count);
        Node<T> current = node;
        while (current != null && minList.size() < count) {
            minList.add(current.getValue());
            current = current.getDown();
        }
        this.minRecursion(node.getRight(), minList, count);
    }

    public List<T> max(int count) {
        List<T> maxList = new ArrayList<>();
        this.maxRecursion(this.root, maxList, count);
        return maxList;
    }

    private void maxRecursion(Node<T> node, List<T> maxList, int count) {
        if (node == null) {
            return;
        }
        this.maxRecursion(node.getRight(), maxList, count);
        Node<T> current = node;
        while (current != null && maxList.size() < count) {
            maxList.add(current.getValue());
            current = current.getDown();
        }
        this.maxRecursion(node.getLeft(), maxList, count);
    }
}
