////////////////////////////////////////////////////////////////////////////////
//
// Title: Performance Analysis
// Course: C.S.400: Programming III, Spring, 2018
// Due Date: 03/19/18
//
// Author: Sebastian Piedra
// Email: piedra@wisc.edu
// Lecturer's Name: Debra Deppeler
// 
////////////////////////////////////////////////////////////////////////////////
//
// No Outside help was used and there are no known bugs
//
////////////////////////////////////////////////////////////////////////////////

import java.util.LinkedList;

public class HashTable<K, V> implements HashTableADT<K, V> {
	
	TableObject<K, V>[] table;
	double maxLoadFactor;
	double loadFactor;
	int numItems;
	private int initialCapacity;
	
	/**
	 * initializes table to default loadFactor and initial capacity
	 */
	public HashTable() {
		this(31, 0.75);
	}
	
	/**
	 * @param initialCapacity initial Table Size
	 * @param loadFactor maximum loadfactor accepted for this HashTable object
	 */
	public HashTable(int initialCapacity, double loadFactor) {
		table = new TableObject[initialCapacity];
		maxLoadFactor = loadFactor;
		this.initialCapacity = initialCapacity;
	}
	
	/**
	 * inner class to store the key-value pair
	 * @param <K> key of the object
	 * @param <V> value of the object
	 */
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
    

    /**
     * creates a valid, deterministic hashkey from a starting key of the object
     * @param key starting key of the object
     * @param tableSize table size of the table for which a new key should be returned
     * @return valid hashKey ready to use
     */
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
    
    /**
     * once the maxLoadFactor is reaches, a bigger table is created
     * then, rehashing needs to occur 
     */
    private void resizeTable() {
    	int nextSize;
    	if (Integer.MAX_VALUE/2 < table.length)
    		nextSize = Integer.MAX_VALUE - 7;
    	else
    		nextSize = removeCommonMultiples(table.length * 2);
    	
    	TableObject<K,V>[] newTable = new TableObject[nextSize];
    	for (int i = 0; i < table.length; i++) {
    		if (table[i] != null)
    			newTable[hashFunction(table[i].key, nextSize)] = new TableObject<>(table[i].key, table[i].value);
    	}
    	
    	table = newTable;
    }
    
    /**
     * although this DOES NOT generates prime numbers
     * it is a cost efficient way to improve the tableSize by making sure is not a multiple of the 
     * first primes, for small numbers, there is a higher chance of the result being a prime number 
     * @param start starting table size
     * @return final table size 
     */
    private int removeCommonMultiples(int start) {
    	while (++start % 2 == 0|| start % 3 == 0 ||  start % 5 == 0 || start % 7 == 0 || 
    			start % 11 == 0 || start % 13 == 0 || start % 17 == 0 || start % 19 == 0);
    	return start;
    }
}
