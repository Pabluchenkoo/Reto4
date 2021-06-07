package model.data_structures;

public interface IMinPQ <K extends Comparable<K>, V extends Comparable<V>>
{
	
	public void insert(K key, V value);
	
	public NodoTS<K, V> min();
	
	public NodoTS<K, V> delMin();
	
	public int size();
	
	public boolean isEmpty();
}
