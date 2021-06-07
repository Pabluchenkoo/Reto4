package model.data_structures;

public class MinPQ <K extends Comparable<K>, V extends Comparable<V>> implements IMinPQ<K, V>
{
	ILista<NodoTS<K, V>> arbol;
	public int tamanio;
	
	public MinPQ(int inicial)
	{
		arbol = new ArregloDinamico<NodoTS<K, V>>(inicial);
		tamanio = 0;
	}
	
	public void swim(ILista<NodoTS<K, V>> lista, int pos)
	{
		boolean termino = false;
		while(pos > 1 && !termino)
		{
			NodoTS<K, V> inicial = lista.getElement(pos / 2);
			NodoTS<K,V> actual = lista.getElement(pos);
			if(inicial.getKey().compareTo(actual.getKey()) > 0)
			{
				lista.exchange(pos/2, pos);
			}
			else
			{
				termino = true;
			}
			pos = pos / 2;
		}
	}
	
	
	public void insert(K key, V value)
	{
		arbol.addLast(new NodoTS<K, V>(key, value));
		tamanio +=1;
		swim(arbol, tamanio);
		
	}
	
	public NodoTS<K, V> min()
	{
		return tamanio > 1? arbol.getElement(0) : null;
	}
	
	public void sink(ILista<NodoTS<K ,V>> lista, int pos)
	{
		boolean termino = false;
		int size = lista.size();
		while((2 * pos <= size) && !termino)
		{
			int posMenor = pos;
			int izq = 2*pos;
			int der = izq + 1;
			
			if(lista.getElement(posMenor).compareTo(lista.getElement(izq))> 0)
			{
				posMenor = izq;
			}
			if(der <= size && lista.getElement(posMenor).compareTo(lista.getElement(der)) > 0)
			{
				posMenor = der;
			}
			
			if(posMenor == pos)
				termino = true;
			else
			{
				lista.exchange(pos, posMenor);
				pos = posMenor;
			}	
		}
	}
	
	public NodoTS<K, V> delMin()
	{
		NodoTS<K, V> retornar = null;
		if(tamanio > 1)
		{
			arbol.exchange(1, tamanio);
			retornar = arbol.removeLast();
			tamanio -= 1;
			sink(arbol, 1);
		}
		else if(tamanio > 0)
		{
			retornar = arbol.removeLast();
		}
		
		return retornar;
	}
	
	public int size()
	{
		return tamanio;
	}
	
	public boolean isEmpty()
	{
		return tamanio == 0;
	}
	
}