import java.util.function.Consumer;

public class BinaryTree<T> {

    private T value;
    private BinaryTree<T> left;
    private BinaryTree<T> right;

    public BinaryTree(T value) {
        this(value, null, null);
    }

    public BinaryTree(T value, BinaryTree<T> child) {
        this(value, child, null);
    }

    public BinaryTree(T value, BinaryTree<T> leftChild, BinaryTree<T> rightChild) {
        this.value = value;
        this.left = leftChild;
        this.right = rightChild;
    }

    public String printIndentedPreOrder(int indent, StringBuilder builder) {
        builder.append(new String(new char[indent * 2]).replace("\0", " "))
                .append(this.value)
                .append("\n");
        if (this.left != null)
            this.left.printIndentedPreOrder(indent + 1, builder);
        if (this.right != null)
            this.right.printIndentedPreOrder(indent + 1, builder);
        return builder.toString();
    }

    public void eachInOrder(Consumer<T> consumer) {
        if (this.left != null) {
            this.left.eachInOrder(consumer);
        }
        consumer.accept(this.value);
        if (this.right != null) {
            this.right.eachInOrder(consumer);
        }
    }

    public void eachPostOrder(Consumer<T> consumer) {
        if (this.left != null) {
            this.left.eachPostOrder(consumer);
        }
        if (this.right != null) {
            this.right.eachPostOrder(consumer);
        }
        consumer.accept(this.value);
    }
}
