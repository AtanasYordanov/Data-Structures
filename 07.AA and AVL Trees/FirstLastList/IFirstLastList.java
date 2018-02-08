package FirstLastList;

public interface IFirstLastList<T extends Comparable<T>> {

    void add(T element);

    int getCount();

    Iterable<T> first(int count);

    Iterable<T> last(int count);

    Iterable<T> min(int count);

    Iterable<T> max(int count);

    void clear();

    int removeAll(T element);
}