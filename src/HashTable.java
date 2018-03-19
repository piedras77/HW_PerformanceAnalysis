import java.util.LinkedList;

public class HashTable<K, V> implements HashTableADT<K, V> {
	
	TableObject<K, V>[] table;
	double maxLoadFactor;
	double loadFactor;
	int numItems;
	private int initialCapacity;
	
	public HashTable() {
		this(31, 0.75);
	}
	
	public HashTable(int initialCapacity, double loadFactor) {
		table = new TableObject[initialCapacity];
		maxLoadFactor = loadFactor;
		this.initialCapacity = initialCapacity;
	}
	
	private class TableObject<K, V> {
		K key;
		V value;
		
		public TableObject(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}	

    @Override
    public V put(K key, V value) {
    	int finalKey = hashFunction(key, table.length);    	
    	V oldValue = table[finalKey] == table[finalKey] ? null : table[finalKey].value;
    	table[finalKey] = new TableObject<>(key, value);
    	loadFactor = ++numItems/table.length;
    	if (loadFactor >= maxLoadFactor)
    		resizeTable();
    	
    	return oldValue;
    }

    @Override
    public void clear() {
    	loadFactor = 0;
    	numItems = 0;
    	table = new TableObject[initialCapacity];
    }

    @Override
    public V get(K key) {
    	int finalKey = hashFunction(key, table.length);
        return table[finalKey] == null ? null : table[finalKey].value;
    }

    @Override
    public boolean isEmpty() {
        return numItems == 0 ? true : false;
    }

    @Override
    public V remove(K key) {
    	int finalKey = hashFunction(key, table.length);
    	V oldValue = table[finalKey] == null ? null : table[finalKey].value;
    	table[finalKey] = null;
    	loadFactor = --numItems/table.length;
        return oldValue;
    }

    @Override
    public int size() {
        return numItems;
    }
    

    private int hashFunction(K key, int tableSize) {
    	String strKey = key + "";
    	int[] weights = {1, 2, 3, 4, 5, 6, 7};
    	int index = 0;
    	for (int i = 0; i < strKey.length(); i++) {
    		index += ((int) strKey.charAt(i)) * weights[i%7];
    		if (index > Integer.MAX_VALUE/2)
    			break;
    	}
    	
    	return index%table.length;
    }
    
    private void resizeTable() {
    	int nextPrime;
    	if (Integer.MAX_VALUE/2 < table.length)
    		nextPrime = Integer.MAX_VALUE - 7;
    	else
    		nextPrime = table.length * 2 + 1; //TODO: add prime lists
    	
    	TableObject<K,V>[] newTable = new TableObject[nextPrime];
    	for (int i = 0; i < table.length; i++) {
    		if (table[i] != null)
    			newTable[hashFunction(table[i].key, nextPrime)] = new TableObject<>(table[i].key, table[i].value);
    	}
    	
    	table = newTable;
    }
}
