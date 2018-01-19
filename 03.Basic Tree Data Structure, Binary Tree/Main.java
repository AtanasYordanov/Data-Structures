import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        Map<Integer, Tree<Integer>> treeMap = new HashMap<>();
        Tree<Integer> tree = new Tree<>(0);
        for (int i = 0; i < n - 1; i++) {
            int[] intArr = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            treeMap.putIfAbsent(intArr[0], new Tree<>(intArr[0]));
            treeMap.putIfAbsent(intArr[1], new Tree<>(intArr[1]));
            treeMap.get(intArr[0]).addChild(treeMap.get(intArr[1]));
        }
        for (Tree<Integer> tr : treeMap.values()) {
            if (tr.getParent() == null) {
                tree = tr;
                break;
            }
        }
        //Problem 1
        //System.out.println("Root node: " + tree.getValue());

        //Problem 2
        //printTree(tree, 0);

        //Problem 3
        //printLeafNodes(tree);

        //Problem 4
        //printMiddleNodes(tree);

        //Problem 5
        //printDeepestNode(tree);

        //Problem 6
        //printLongestPath(tree);

        //Problem 7
        //int targetSum = Integer.parseInt(reader.readLine());
        //printSums(tree, targetSum);

        //Problem 8
        //int targetSum = Integer.parseInt(reader.readLine());
        //printSubtrees(tree, targetSum);
    }

    private static void printMiddleNodes(Tree<Integer> tree) {
        List<Integer> output = new ArrayList<>();
        getAllChildren(tree, output);
        System.out.printf("Middle nodes: %s", output.stream()
                .sorted(Comparator.naturalOrder())
                .map(Object::toString)
                .collect(Collectors.joining(" ")));
    }

    private static void printSubtrees(Tree<Integer> tree, int neededSum) {
        List<Tree<Integer>> roots = new ArrayList<>();
        System.out.printf("Subtrees of sum %d:%n", neededSum);
        DFS(tree, neededSum, roots);
        for (Tree<Integer> root : roots) {
            printChildren(root);
            System.out.println();
        }
    }

    private static void getAllChildren(Tree<Integer> tree, List<Integer> output) {
        if (tree.getParent() != null && !tree.getChildren().isEmpty()) {
            output.add(tree.getValue());
        }
        if (!tree.getChildren().isEmpty()) {
            for (Tree<Integer> child : tree.getChildren()) {
                getAllChildren(child, output);
            }
        }
    }

    private static void printChildren(Tree<Integer> root) {
        System.out.print(root.getValue() + " ");
        for (Tree<Integer> child : root.getChildren()) {
            printChildren(child);
        }
    }

    private static int DFS(Tree<Integer> tree, long neededSum, List<Tree<Integer>> roots) {
        if (tree == null) {
            return 0;
        }
        int current = tree.getValue();
        for (Tree<Integer> child : tree.getChildren()) {
            current += DFS(child, neededSum, roots);
        }
        if (current == neededSum) {
            roots.add(tree);
        }
        return current;
    }

    private static void printSums(Tree<Integer> tree, long neededSum) {
        long sum = 0;
        System.out.printf("Paths of sum %d:%n", neededSum);
        checkEverySum(tree, sum, neededSum);
    }

    private static void checkEverySum(Tree<Integer> tree, long sum, long neededSum) {
        sum += tree.getValue();
        if (!tree.getChildren().isEmpty()) {
            for (Tree<Integer> child : tree.getChildren()) {
                checkEverySum(child, sum, neededSum);
            }
        }
        if (sum == neededSum) {
            Deque<Integer> stack = new ArrayDeque<>();
            while (tree != null) {
                stack.push(tree.getValue());
                tree = tree.getParent();
            }
            while (!stack.isEmpty()) {
                System.out.print(stack.pop() + " ");
            }
            System.out.println();
        }
    }

    private static void printLongestPath(Tree<Integer> tree) {
        int level = 0;
        int[] maxDepthAndNode = {0, tree.getValue()};
        Tree[] deepestNode = new Tree[]{tree};
        getDeepestNode6(tree, level, maxDepthAndNode, deepestNode);
        Deque<String> path = new ArrayDeque<>();
        path.push(deepestNode[0].getValue().toString());
        while (deepestNode[0].getParent() != null) {
            deepestNode[0] = deepestNode[0].getParent();
            path.push(deepestNode[0].getValue().toString());
        }
        System.out.printf("Longest path: %s%n", path.stream().collect(Collectors.joining(" ")));
    }

    private static void getDeepestNode6(Tree<Integer> tree, int level, int[] maxDepthAndNode, Tree[] deepestNode) {
        if (!tree.getChildren().isEmpty()) {
            for (Tree<Integer> child : tree.getChildren()) {
                getDeepestNode6(child, level + 1, maxDepthAndNode, deepestNode);
            }
        } else {
            if (level > maxDepthAndNode[0]) {
                maxDepthAndNode[0] = level;
                maxDepthAndNode[1] = tree.getValue();
                deepestNode[0] = tree;
            }
        }
    }

    private static void printDeepestNode(Tree<Integer> tree) {
        int level = 0;
        int[] maxDepthAndNode = {0, tree.getValue()};
        getDeepestNode(tree, level, maxDepthAndNode);
        System.out.printf("Deepest node: %d%n", maxDepthAndNode[1]);
    }

    private static void getDeepestNode(Tree<Integer> tree, int level, int[] maxDepthAndNode) {
        if (!tree.getChildren().isEmpty()) {
            for (Tree<Integer> child : tree.getChildren()) {
                getDeepestNode(child, level + 1, maxDepthAndNode);
            }
        } else if (level > maxDepthAndNode[0]) {
            maxDepthAndNode[0] = level;
            maxDepthAndNode[1] = tree.getValue();
        }
    }

    private static void printLeafNodes(Tree<Integer> tree) {
        TreeSet<Integer> values = new TreeSet<>();
        for (Tree<Integer> child : tree.getChildren()) {
            getLeafNodes(child, values);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Leaf nodes: ");
        for (Integer value : values) {
            sb.append(value).append(" ");
        }
        System.out.println(sb);
    }

    private static void getLeafNodes(Tree<Integer> child, TreeSet<Integer> values) {
        if (child.getChildren().isEmpty()) {
            values.add(child.getValue());
        } else {
            for (Tree<Integer> ch : child.getChildren()) {
                getLeafNodes(ch, values);
            }
        }
    }

    private static void printTree(Tree<Integer> tree, int padding) {
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.println(tree.getValue());
        for (Tree<Integer> child : tree.getChildren()) {
            printTree(child, padding + 2);
        }
    }
}
