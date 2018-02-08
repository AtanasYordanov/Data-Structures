package FirstLastList;

public class Main {
    public static void main(String[] args) {
        AVL<Integer> tree = new AVL<Integer>();
        tree.insert(4);
        tree.insert(8);
        tree.insert(6);
        tree.insert(34);
        tree.insert(1);
        tree.insert(1);
        tree.insert(123);
        tree.insert(11);
        tree.eachInOrder(System.out::println);
    }
}
