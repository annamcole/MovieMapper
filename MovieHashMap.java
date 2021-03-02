// --== CS400 File Header Information ==--
// Name: Elliott Weinshenker
// Email: eweinshenker@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.NoSuchElementException;


public class MovieHashMap<K, V> implements MapADT<K, V> {
  private LinkedList<Entry<K, V>>[] table; // array field to store key-value pairs
  private int capacity;
  private int _size;

  
  public MovieHashMap() {
    this.capacity = 10;
    _size = 0;
    table = new LinkedList[capacity];

    for (int i = 0; i < capacity; i++) {
      table[i] = new LinkedList<Entry<K, V>>();;
    }
  }
  
  public MovieHashMap(int capacity) {
    if (capacity < 0)
      throw new IllegalArgumentException();
    this.capacity = capacity;
    _size = 0;
    table = new LinkedList[capacity];

    for (int i = 0; i < capacity; i++) {
      table[i] = new LinkedList<Entry<K, V>>();
    }
  }


  private int getIndex(K key) {
    int hash = Math.abs(key.hashCode() % table.length);
    return hash;
  }


  /**
   * Add key and value to HashMap.
   * 
   * @param key   key to be added
   * @param value value to be added
   * @return true if successfully stored a new key-value pair, false if key provided is null or node
   *         was not added.
   */
  public boolean put(K key, V value) {
    if (key.equals(null))
      return false;

    int index = getIndex(key);
    LinkedList<Entry<K, V>> list = table[index];

    if (list.isEmpty()) {
      // first time inserting for an empty spot in array (no collision)
      list.add(new Entry<K, V>(key, value));

    } else {
      // collision - insert entry in linked list
//      for (Entry<K, V> e : list) {
//        if (e.getKey().equals(key)) {
//          return false;
//        }
//      }
      list.addFirst(new Entry<K, V>(key, value));
    }   
    _size++;
    resize();
    return true;
  }

  /**
   * Returns the value to corresponding to the provided key.
   * 
   * @param key key to retrieve value for
   * @return value value of provided key
   * @throws NoSuchElementException if key is not found in HashMap
   */
  public V get(K key) throws NoSuchElementException {
    
    int index = getIndex(key);
    LinkedList<Entry<K, V>> list = table[index];
    
    if (list.isEmpty()) {
      throw new NoSuchElementException();
    } else {
      for (Entry<K, V> e : list) {
        if (e.getKey().equals(key)) {
          return e.getValue();
        }
      }
      throw new NoSuchElementException();
    }
  }
  
  /**
   * TODO
   * 
   * @param key
   * @return
   * @throws NoSuchElementException
   */
  public List<V> getAll(K key){

    List<V> valueList = new ArrayList<V>();
    int index = getIndex(key);
    LinkedList<Entry<K, V>> list = table[index];

    if (list.isEmpty()) {
      return valueList;
    } else {
      for (Entry<K, V> e : list) {
        if (e.getKey().equals(key)) {
          valueList.add(e.getValue());
        }
      }
      return valueList;
    }
  }

  /**
   * Returns the number of key-value pair stored in the HashMap.
   * 
   * @return an int of number of pairs stored
   */
  public int size() {
    return _size;
  }

  /**
   * Searches the HashMap for a provided key.
   * 
   * @param key the key to look for
   * @return true if the key exists in the HashMap, false if key is null or doesn't exist in the
   *         HashMap
   */
  public boolean containsKey(K key) {
    if (key == null)
      return false;
    
    int index = getIndex(key);
    LinkedList<Entry<K, V>> list = table[index];

    // key is not in hashMap
    if (list.isEmpty()) 
      return false;

    // search through linked list
    else {
      for(Entry<K, V> e: list) {
        if(e.getKey().equals(key))
          return true;
      }
    }
    // couldn't find key
    return false;
  }

  /**
   * Removes a key-value pair from the HashMap.
   * 
   * @param key the key of the key-value pair to be removed
   * @return a reference to the value associated with the key is being removed, null if the key
   *         being removed does not exist
   */
  public V remove(K key) {
    //TODO: finish this method
    int index = getIndex(key);
    
    LinkedList<Entry<K,V>> list = table[index];
    
    if(list.isEmpty()) {
      return null;
    }
    else {
      for(Entry<K, V> e: list) {
        if(e.getKey().equals(key)){
          V output = e.getValue();
          list.remove(e);
          _size--; 
          
          return output; 
        }
      }
    }
    return null;
  }

  /**
   * Removes all key-value pairs from the HashMap.
   */
  public void clear() {
    for(LinkedList<Entry<K, V>> list: table) {
      list.clear();
    }
    
    _size = 0; 
  }

  private void resize() {
    double loadFactor = ((double) _size) / capacity;
    if (loadFactor >= 0.85f) {
      // create new hash table with double size + 1
      capacity *= 2;
      
      LinkedList<Entry<K, V>>[] oldTable = table;
      
      table = new LinkedList[capacity];
      _size = 0; 
      for (int i = 0; i < table.length; i++) {
        table[i] = new LinkedList<Entry<K, V>>(); 
      }
      
      for (LinkedList<Entry<K, V>> list: oldTable) {
        if(!list.isEmpty()) {
          for (Entry<K, V> e: list) {
            this.put(e.getKey(), e.getValue());   
          }
        }   
      } 
           
    }
  }

  /**
   * Returns as List view of key contained in this map.
   * 
   * @return result a List view of keys contained in this map
   */
  public Set<K> keySet() {
    Set<K> result = new HashSet<K>();
    for (LinkedList<Entry<K, V>> list: table) {
      if(!list.isEmpty()) {
        for (Entry<K, V> e: list) {
          result.add(e.getKey());   
        }
      }   
    } 
    return result;
  }

  class Entry<K, V> {
    // create entry class for data objects being put into HashTableMap
    private K key;
    private V value;

    // constructor
    public Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    // getter
    public K getKey() {
      return this.key;
    }

    public V getValue() {
      return this.value;
    }

  }
}
