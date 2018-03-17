import java.util.NoSuchElementException;

/**
 * Created by sripradha on 19/02/18.
 * This is an interface for the HashTable implementation
 *
 * Please implement this interface in HashTable class
 */

public interface HashTableADT<K, V> {
    /**
     *
     * @param key : The key that goes into the hashtable
     * @param value: The Value associated with the key
     * @return value of the key added to the hashtable,
     *      throws NullPointerException if key is null
     */
    V put(K key, V value) throws NullPointerException;

    /**
     * @param key: The key for which the value is returned
     * @return The value associated with the key,
     *          else throws NoSuch Element Exception
     */
    V get(K key) throws NoSuchElementException;

    /**
     * Clear the hashtable of all its contents
     */
    void clear();

    /**
     * Checks if the hashtable is empty
     * @return true : if Empty, else False
     */
    boolean isEmpty();

    /**
     *
     * @param key: Key of the entry to be removed
     * @return value: Value of the key-value pair removed,
     *          null if key did not have a mapping
     * @throws NullPointerException if key is null
     */
    V remove(K key) throws NullPointerException;

    /**
     *
     * @return: The total number of entries in the hashtable
     */
    int size();
}
