package model.data_structures;

public interface ITablaSimbolos <K extends Comparable<K>, V extends Comparable<V>>
{
	
	public void put(K key,V value);

	public V get(K key);
	
	public boolean contains(K key);

	public boolean isEmpty();
	
	public int size();
	
	public V remove (K key);
	
	public ILista<K> keySet();
	
	public ILista<V> valueSet();
	
	public int hash(K key);
	
}
