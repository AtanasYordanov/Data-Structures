public class Heap {
    public static <E extends Comparable<E>> void sort(E[] array) {
        for (int i = array.length / 2; i >= 0; i--) {
            heapifyDown(array, i, array.length);
        }
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, 0, i);
            heapifyDown(array, 0, i);
        }
    }

    private static <E extends Comparable<E>> void heapifyDown(E[] array, int index, int length) {

        while (index < length / 2) {
            int childIndex = 2 * index + 1;
            if(childIndex + 1 < length && isGreater(array, childIndex + 1, childIndex)) {
                childIndex++;
            }
            if(isGreater(array, index, childIndex)) {
                break;
            }
            E temp = array[childIndex];
            array[childIndex] = array[index];
            array[index] = temp;

            index = childIndex;
        }
    }

    private static <E extends Comparable<E>> boolean isGreater(E[] array, int a, int b) {
        return array[a].compareTo(array[b]) > 0;
    }

    private static <E> void swap(E[] array, int a, int b) {
        E temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}
