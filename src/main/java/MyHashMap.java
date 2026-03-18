import java.util.ArrayList;
import java.util.LinkedList;

public class MyHashMap<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Entry<K, V>>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        buckets = new LinkedList[10];
        size = 0;
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public V put(K key, V value) {
        int index = getBucketIndex(key);

        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }

        for (Entry<K, V> entry : buckets[index]) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }

        buckets[index].add(new Entry<>(key, value));
        size++;
        return null;
    }

    public V get(K key) {
        int index = getBucketIndex(key);

        if (buckets[index] == null) {
            return null;
        }

        for (Entry<K, V> entry : buckets[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    public V remove(K key) {
        int index = getBucketIndex(key);

        if (buckets[index] == null) {
            return null;
        }

        for (int i = 0; i < buckets[index].size(); i++) {
            Entry<K, V> entry = buckets[index].get(i);
            if (entry.key.equals(key)) {
                V removedValue = entry.value;
                buckets[index].remove(i);
                size--;
                return removedValue;
            }
        }

        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public ArrayList<K> keys() {
        ArrayList<K> keyList = new ArrayList<>();

        for (LinkedList<Entry<K, V>> bucket : buckets) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    keyList.add(entry.key);
                }
            }
        }

        return keyList;
    }

    public ArrayList<V> values() {
        ArrayList<V> valueList = new ArrayList<>();

        for (LinkedList<Entry<K, V>> bucket : buckets) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    valueList.add(entry.value);
                }
            }
        }

        return valueList;
    }

    public ArrayList<String> entrySet() {
        ArrayList<String> entries = new ArrayList<>();

        for (LinkedList<Entry<K, V>> bucket : buckets) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    entries.add(entry.key + " = " + entry.value);
                }
            }
        }

        return entries;
    }

    public void printMap() {
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    System.out.println(entry.key + " -> " + entry.value);
                }
            }
        }
    }
}