package Exercises.main;

import java.util.*;

public class Hierarchy<T> implements IHierarchy<T> {

    private Map<T, Tree> nodes;
    private Tree root;
    private int count;

    public Hierarchy(T element){
        this.root = new Tree(element, null);
        this.nodes = new LinkedHashMap<>();
        this.nodes.put(element, root);
        this.count++;
    }

    public void add(T parent, T child){
        Tree parentNode = nodes.getOrDefault(parent, null);
        if (parentNode == null || nodes.containsKey(child)){
            throw new IllegalArgumentException();
        }
        Tree newTree = new Tree(child,parentNode);
        parentNode.children.add(newTree);
        this.nodes.putIfAbsent(child, newTree);
        this.count++;
    }

    public int getCount() {
        return this.count;
    }

    public void remove(T element){
        if (this.root.value == element){
            throw new IllegalStateException();
        }
        Tree targetNode = nodes.getOrDefault(element, null);
        if (targetNode == null){
            throw new IllegalArgumentException();
        }
        Tree parentNode = targetNode.parent;
        List<Tree> children = targetNode.children;
        for (Tree child : children) {
            child.parent = parentNode;
        }
        parentNode.children.remove(targetNode);
        parentNode.children.addAll(children);
        this.nodes.remove(element);
        this.count--;
    }

    public boolean contains(T element){
      return this.nodes.containsKey(element);
    }

    public T getParent(T element){
        if (!this.nodes.containsKey(element)){
            throw new IllegalArgumentException();
        }
        Tree parentNode = this.nodes.get(element).parent;
        return parentNode == null? null : parentNode.value;
    }

    public Iterable<T> getChildren(T element){
        if (!this.nodes.containsKey(element)){
            throw new IllegalArgumentException();
        }
        List<Tree> children = nodes.get(element).children;
        List<T> values = new ArrayList<>();
        for (Tree child : children) {
            values.add(child.value);
        }
        return values;
    }

    public Iterable<T> getCommonElements(IHierarchy<T> other){
        List<T> commonElements = new ArrayList<>();
        for (T element : this.nodes.keySet()) {
            if (other.contains(element)){
                commonElements.add(element);
            }
        }
        return commonElements;
    }

    @Override
    public Iterator<T> iterator() {
        List<T> output = new ArrayList<>();
       Deque<Tree> queue = new ArrayDeque<>();
       queue.offer(root);
       while (!queue.isEmpty()){
           Tree currentNode = queue.poll();
           output.add(currentNode.value);
           for (Tree child : currentNode.children) {
               queue.offer(child);
           }
       }
       return output.iterator();
    }

    private class Tree{
        private T value;
        private Tree parent;
        private List<Tree> children;

        public Tree(T value, Tree parent) {
            this.value = value;
            this.parent = parent;
            this.children = new ArrayList<>();
        }
    }
}
