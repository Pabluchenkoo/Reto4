package model.data_structures;

public class MinPQIndexada <K extends Comparable<K>, IK extends Comparable<IK>, V extends Comparable<V>> extends MinPQ<K, V>
{

	public MinPQIndexada(int inicial) {
		super(inicial);
	}
	
	public void insert(K key, IK indexedKey, V value) {
        arbol.addLast(new NodoIndexedMinPQ<K, IK, V>(key, indexedKey, value));
        tamanio += 1;
        swim(arbol, tamanio);
    }
    
    public void changePriority(IK indexedKey, K newKey, V value) {
        int posicionBuscado = -1;
        for(int i = 1; i <= arbol.size() && posicionBuscado == -1; i++) {
            @SuppressWarnings("unchecked")
            NodoIndexedMinPQ<K, IK, V> actual = (NodoIndexedMinPQ<K, IK, V>) arbol.getElement(i);
            if(actual.getIndexedKey().compareTo(indexedKey) == 0) {
                posicionBuscado = i;
            }
        }
        
        
        if(newKey.compareTo(arbol.getElement(posicionBuscado).getKey()) > 0) {
            arbol.getElement(posicionBuscado).setKey(newKey);
            arbol.getElement(posicionBuscado).setValue(value);
            sink(arbol, posicionBuscado);
        }else if(newKey.compareTo(arbol.getElement(posicionBuscado).getKey()) < 0) {
            arbol.getElement(posicionBuscado).setKey(newKey);
            arbol.getElement(posicionBuscado).setValue(value);
            swim(arbol, posicionBuscado);
        }
    }    

}
