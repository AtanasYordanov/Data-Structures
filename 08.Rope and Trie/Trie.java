import java.util.*;

public class Trie<Value> {
    private Node root;

    public Value getValue(String key) {
        Node result = this.getNode(this.root, key, 0);
        if (result == null || !result.isTerminal()) {
            throw new IllegalArgumentException();
        }
        return result.getValue();
    }

    public boolean contains(String key) {
        Node node = this.getNode(this.root, key, 0);
        return node != null && node.isTerminal();
    }

    public void insert(String key, Value value) {
        this.root = this.insert(this.root, key, value, 0);
    }

    public Iterable<String> getByPrefix(String prefix) {
        Deque<String> result = new LinkedList<>();
        Node node = this.getNode(this.root, prefix, 0);

        this.collect(node, prefix, result);
        return reverseCollection(result);
    }

    private void collect(Node node, String prefix, Deque<String> result) {
        if (node == null) {
            return;
        }

        if (node.getValue() != null && node.isTerminal()) {
            result.addFirst(prefix);
        }

        for (Character c : node.getNext().keySet()) {
            this.collect(node.getNext().get(c), prefix + c, result);
        }
    }

    private Node insert(Node node, String key, Value value, int v) {
        if (node == null){
            node = new Node();
        }
        if (v == key.length()){
            node.setValue(value);
            node.setTerminal(true);
            return node;
        }
        char x = key.charAt(v);
        node.next.put(x, this.insert(node.next.get(x), key, value, v + 1));
        return node;
    }

    private Node getNode(Node node, String key, int v) {
        if (node == null) {
            return null;
        }

        if (v == key.length()) {
            return node;
        }

        char c = key.charAt(v);
        return this.getNode(node.getNext().get(c), key, v + 1);
    }

    private Iterable<String> reverseCollection(Deque<String> collection) {
        ArrayList<String> result = new ArrayList<>();

       Iterator<String> iterator = collection.descendingIterator();
       while (iterator.hasNext()) {
           result.add(iterator.next());
       }

        return result;
    }

    private class Node {
        private Value value;
        private boolean isTerminal;
        private Map<Character, Node> next;

        public Node() {
            this.next = new LinkedHashMap<>();
        }

        public Value getValue() {
            return this.value;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public boolean isTerminal() {
            return isTerminal;
        }

        public void setTerminal(boolean terminal) {
            this.isTerminal = terminal;
        }

        public Map<Character, Node> getNext() {
            return this.next;
        }
    }
}
