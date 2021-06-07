package model.data_structures;
import model.data_structures.*;

public class Vertex <K extends  Comparable<K>,V extends Comparable<V>> implements IVertex<K, V>, Comparable<Vertex<K, V>> 
{
	private K id;
	private V value;
	private boolean marked;
	private int indegree;
	private ArregloDinamico<Edge<K,V>> arcos;

	public Vertex(K id, V value)
	{
		arcos = new ArregloDinamico<>(10);
		this.id = id;
		this.value = value;
		marked = false;
		indegree = 0;
	}

	public K getId()
	{
		return id;
	}

	public V getInfo()
	{
		return value;
	}

	public boolean getMark()
	{
		return marked;
	}

	public void addEdge(Edge<K, V> edge)
	{
		arcos.addLast(edge);
		indegree++;
	}


	public void mark(Edge<K,V> edgeTo) 
	{
		edgeTo.getDestination().marked = true;
	}

	@Override
	public void unmark()
	{
		marked = false;
	}

	@Override
	public int outdegree() {
		return indegree;
	}

	@Override
	public int indegree() {
		// TODO Auto-generated method stub
		return indegree;
	}

	@Override
	public Edge<K, V> getEdge(K vertex) 
	{
		Edge<K,V> temp = null;
		boolean encontro = false;
		for (int i = 1; i <= arcos.size() &&!encontro  ; i++)
		{
			if(vertex.compareTo(arcos.getElement(i).getDestination().getId())==0)
			{
				temp = arcos.getElement(i);
				encontro = true;
			}
		}
		return temp;
	}

	@Override
	public ILista<Vertex<K, V>> vertices() 
	{
		ArregloDinamico<Vertex<K,V>> lista = new ArregloDinamico<Vertex<K, V>>(10000);
		for (int i = 1; i <= arcos.size(); i++) 
		{
			lista.addLast(arcos.getElement(i).getDestination());
		}
		return lista;
	}

	@Override
	public ILista<Edge<K, V>> edges() 
	{
		return arcos;
	}

	@Override
	public int compareTo(Vertex<K, V> o) {
		return value.compareTo((V) o);
	}

	public void dfs(Edge<K,V> edgeTo)
	{
		this.marked = true;
		for(int i = 0; i <= arcos.size(); i++)
		{
			Vertex<K,V> destino = arcos.getElement(i).getDestination();
			if(!destino.marked)
			{
				destino.dfs(arcos.getElement(i));
			}
			
		}
	}
	
	public void bfs()
	{
		ICola<Vertex<K,V>> cola = new ColaEncadenada<Vertex<K,V>>();
		this.marked = true;
		cola.enqueue(this);
		while(cola.peek() != null)
		{
			Vertex<K, V> actual = cola.dequeue();
			for(int i = 0; i < actual.edges().size(); i++)
			{
				Vertex<K, V> dest = actual.arcos.getElement(i).getDestination();
				if(!dest.marked)
				{
					dest.marked = true;
					cola.enqueue(dest);
				}
			}
		}
	}
	
	public boolean mark()
	{
		return this.marked= true;
	}

	public ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> minPathTree() {
		ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> tabla = new TablaHashLinearProbing<K, NodoTS<Float, Edge<K, V>>>(1000, 0.5);
		MinPQIndexada<Float, K, Edge<K, V>> colaIndx = new MinPQIndexada<Float, K, Edge<K, V>>(1000);
		
		tabla.put(this.id, new NodoTS<Float, Edge<K,V>>(0f, null));
		relax(tabla, colaIndx, this, 0);
		while(!colaIndx.isEmpty())
		{
			NodoTS<Float, Edge<K,V>> actual = colaIndx.delMin();
			Edge<K, V> arcoAct = actual.getValue();
			float pesoAct = actual.getKey();
			relax(tabla, colaIndx, arcoAct.getDestination(), pesoAct);
		}
			
		return tabla;
	}
	
	public void relax(ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> tablaResultado, MinPQIndexada<Float, K, Edge<K, V>> colaIndexada, Vertex<K, V> actual, float pesoAcumulado)
	{
		actual.mark();
		
		for(int i = 1; i <= actual.edges().size(); i++ )
		{
			Edge<K, V> arcoActual = actual.arcos.getElement(i);
			Vertex<K, V> destino = arcoActual.getDestination();
			float peso = arcoActual.weight();
			if(!destino.getMark())
			{
				NodoTS<Float, Edge<K, V>> llegadaDestino = tablaResultado.get(destino.getId());
				if(llegadaDestino == null)
				{
					tablaResultado.put(destino.getId(), new NodoTS<Float, Edge<K, V>>(pesoAcumulado + peso, arcoActual));
					colaIndexada.insert(peso + pesoAcumulado, destino.getId(), arcoActual);
					
				}
				else if(llegadaDestino.getKey() > pesoAcumulado +peso)
				{
					llegadaDestino.setKey(pesoAcumulado + peso);
					llegadaDestino.setValue(arcoActual);
					colaIndexada.changePriority(destino.getId(), pesoAcumulado + peso, arcoActual);
				}
				
			}
		}
	}
	
	public ILista<Edge<K,V>> mstPrim()
	{
		ILista<Edge<K,V>> mst = new ArregloDinamico<Edge<K,V>>(10);
		MinPQ<Float,Edge<K,V>> cola = new MinPQ<Float,Edge<K,V>> (20);
		
		addEdgesToMinPQ(cola, this);
		while(!cola.isEmpty())
		{
			Edge<K,V> actual = cola.delMin().getValue();
			Vertex<K,V> dest = actual.getDestination();
			if(!dest.getMark())
			{
				mst.addLast(actual);
				addEdgesToMinPQ(cola, dest);
			}
		}
		return mst;
	}
	
	
	private void addEdgesToMinPQ(MinPQ<Float, Edge<K,V>> cola, Vertex<K,V> inicio)
	{
		inicio.mark();
		for(int i = 1; i<= inicio.edges().size();i++)
		{
			Edge<K,V> actual = inicio.edges().getElement(i);
			if(!actual.getDestination().getMark())
			{
				cola.insert(actual.weight(),actual);
			}	
		}
	}

}