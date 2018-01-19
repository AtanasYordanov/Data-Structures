import java.util.*;
import java.util.function.Consumer;

public class Tree<T> {

    private T value;
    private Tree<T> parent;
    private List<Tree<T>> children;

    public Tree(T value, Tree<T>... children) {
        this.value = value;
        this.children = new ArrayList<>();
        this.children.addAll(Arrays.asList(children));
    }

    public void addChild(Tree<T> child) {
        this.children.add(child);
        child.setParent(this);
    }

    public String print(int indent, StringBuilder builder) {
        builder.append(new String(new char[indent * 2]).replace("\0", " "))
                .append(this.value)
                .append("\n");
        for (Tree<T> child : this.children) {
            child.print(indent + 1, builder);
        }
        return builder.toString();
    }

    public void each(Consumer<T> consumer) {
        consumer.accept(this.value);
        for (Tree<T> child : this.children) {
            child.each(consumer);
        }
    }

    public Iterable<T> orderDFS() {
        List<T> output = new ArrayList<>();
        this.DFS(output);
        return output;
    }

    private void DFS(List<T> output){
        for (Tree<T> child : this.children) {
            child.DFS(output);
        }
        output.add(this.value);
    }

    public Iterable<T> orderBFS() {
        Deque<Tree<T>> queue = new ArrayDeque<>();
        List<T> output = new ArrayList<>();
        queue.offer(this);
        while (!queue.isEmpty()){
            Tree<T> current = queue.poll();
            for (Tree<T> child : current.children) {
                queue.offer(child);
            }
            output.add(current.value);
        }
        return output;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public Tree<T> getParent() {
        return parent;
    }

    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }
}