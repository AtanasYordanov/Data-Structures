import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        CircularQueue<Integer> queue = new CircularQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);

        System.out.printf("Count = %d\n", queue.size());
        System.out.println(Arrays.toString(queue.toArray()));
        System.out.println("---------------------------");

        int first = queue.dequeue();
        System.out.printf("First = %d\n", first);
        System.out.printf("Count = %d\n", queue.size());
        System.out.println(Arrays.toString(queue.toArray()));
        System.out.println("---------------------------");

        queue.enqueue(-7);
        queue.enqueue(-8);
        queue.enqueue(-9);
        System.out.printf("Count = %d\n", queue.size());
        System.out.println(Arrays.toString(queue.toArray()));
        System.out.println("---------------------------");

        first = queue.dequeue();
        System.out.printf("First = %d\n", first);
        System.out.printf("Count = %d\n", queue.size());
        System.out.println(Arrays.toString(queue.toArray()));
        System.out.println("---------------------------");

        queue.enqueue(-10);
        System.out.printf("Count = %d\n", queue.size());
        System.out.println(Arrays.toString(queue.toArray()));
        System.out.println("---------------------------");

        first = queue.dequeue();
        System.out.printf("First = %d\n", first);
        System.out.printf("Count = %d\n", queue.size());
        System.out.println(Arrays.toString(queue.toArray()));
        System.out.println("---------------------------");

    }
}
