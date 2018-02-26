import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HashTable<TKey extends Comparable, TValue> implements Iterable<KeyValue<TKey, TValue>> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private int maxElements;
    private LinkedList<KeyValue<TKey, TValue>>[] hashTable;
    private int size;
    private int capacity;

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int capacity) {
        this.capacity = capacity;
        this.maxElements = (int) (capacity * LOAD_FACTOR);
        this.hashTable = new LinkedList[capacity];
    }

    public void add(TKey key, TValue value) {
        this.growIfNeeded();
        int hash = Math.abs(key.hashCode()) % this.hashTable.length;
        if (this.hashTable[hash] == null) {
            this.hashTable[hash] = new LinkedList<>();
        }
        for (KeyValue<TKey, TValue> keyValue : hashTable[hash]) {
            if (keyValue.getKey().equals(key)) {
                throw new IllegalArgumentException("Key already exists: " + key);
            }
        }
        KeyValue<TKey, TValue> kvp = new KeyValue<>(key, value);
        this.hashTable[hash].add(kvp);
        this.size++;

    }

    private void growIfNeeded() {

        if (this.size() + 1 > this.capacity() * LOAD_FACTOR) {
            this.grow();
        }
    }

    private void grow() {
        HashTable<TKey, TValue> newTable = new HashTable<>(this.capacity * 2);
        this.capacity *= 2;

        for (KeyValue<TKey, TValue> keyValue : this) {
            newTable.add(keyValue.getKey(), keyValue.getValue());
        }
        this.hashTable = newTable.hashTable;
        this.setSize(newTable.size());
    }

    public int size() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int capacity() {
        return this.capacity;
    }

    public boolean addOrReplace(TKey key, TValue value) {
        this.growIfNeeded();
        int hash = Math.abs(key.hashCode()) % this.hashTable.length;

        if (this.hashTable[hash] == null) {
            this.hashTable[hash] = new LinkedList<>();
        }

        List<KeyValue<TKey, TValue>> crntList = this.hashTable[hash];

        for (KeyValue<TKey, TValue> keyValuePair : crntList) {
            if (keyValuePair.getKey().equals(key)) {
                keyValuePair.setValue(value);
                return false;
            }
        }

        KeyValue newKeyValue = new KeyValue<>(key, value);
        crntList.add(newKeyValue);
        this.size++;
        return true;
    }

    public TValue get(TKey key) {
        KeyValue<TKey, TValue> outputKeyValuePair = this.find(key);
        if (outputKeyValuePair == null) {
            throw new IllegalArgumentException("Key don't exists: " + key);
        }
        return outputKeyValuePair.getValue();
    }


    public KeyValue<TKey, TValue> find(TKey key) {
        int hash = Math.abs(key.hashCode()) % this.hashTable.length;

        if (this.hashTable[hash] != null) {
            List<KeyValue<TKey, TValue>> crntList = this.hashTable[hash];

            for (KeyValue<TKey, TValue> keyValuePair : crntList) {
                if (keyValuePair.getKey().equals(key)) {
                    return keyValuePair;
                }
            }
        }
        return null;
    }

    public boolean tryGetValue(TKey key, TValue value) {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(TKey key) {
        return this.find(key) != null;
    }

    public boolean remove(TKey key) {
        int hash = Math.abs(key.hashCode()) % this.hashTable.length;

        if (this.hashTable[hash] != null) {
            List<KeyValue<TKey, TValue>> crntList = this.hashTable[hash];

            for (KeyValue<TKey, TValue> keyValuePair : crntList) {
                if (keyValuePair.getKey().equals(key)) {
                    crntList.remove(keyValuePair);
                    this.size--;
                    return true;
                }
            }
        }
        return false;
    }

    public void clear() {
        this.capacity = DEFAULT_CAPACITY;
        this.setSize(0);
        this.hashTable = new LinkedList[DEFAULT_CAPACITY];
    }

    public Iterable<TKey> keys() {
        LinkedList<TKey> elements = new LinkedList<>();
        for (List<KeyValue<TKey, TValue>> keyValueList : this.hashTable) {
            if (keyValueList != null) {
                for (KeyValue<TKey, TValue> keyValuePair : keyValueList) {
                    elements.add(keyValuePair.getKey());
                }
            }
        }
        return elements;
    }

    public Iterable<KeyValue<TKey, TValue>> sortedPairs() {
        LinkedList<KeyValue<TKey, TValue>> elements = new LinkedList<>();
        for (List<KeyValue<TKey, TValue>> keyValueList : this.hashTable) {
            if (keyValueList != null) {
                for (KeyValue<TKey, TValue> keyValuePair : keyValueList) {
                    elements.add(keyValuePair);
                }
            }
        }
        elements.sort(Comparator.comparing(KeyValue::getKey));
        return elements;
    }

    public Iterable<TValue> values() {
        LinkedList<TValue> elements = new LinkedList<>();
        for (List<KeyValue<TKey, TValue>> keyValueList : this.hashTable) {
            if (keyValueList != null) {
                for (KeyValue<TKey, TValue> keyValuePair : keyValueList) {
                    elements.add(keyValuePair.getValue());
                }
            }
        }
        return elements;
    }

    @Override
    public Iterator<KeyValue<TKey, TValue>> iterator() {
        LinkedList<KeyValue<TKey, TValue>> elements = new LinkedList<>();
        for (List<KeyValue<TKey, TValue>> keyValueList : this.hashTable) {
            if (keyValueList != null) {
                elements.addAll(keyValueList);
            }
        }
        return elements.iterator();
    }

    public int getMaxElements() {
        return maxElements;
    }

    public void setMaxElements(int maxElements) {
        this.maxElements = maxElements;
    }

    public LinkedList<KeyValue<TKey, TValue>>[] getHashTable() {
        return hashTable;
    }

    public void setHashTable(LinkedList<KeyValue<TKey, TValue>>[] hashTable) {
        this.hashTable = hashTable;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
