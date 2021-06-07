package model.data_structures;

public class GrafoListaAdyacencia <K extends Comparable<K>, V extends Comparable<V>>
{
	private ITablaSimbolos<K, Vertex<K,V>> vertices;
	private int numEdges;
	
	public GrafoListaAdyacencia(int numVertices)
	{
		vertices = new TablaHashLinearProbing<>(numVertices, 0.5);
		numEdges = 0;
	}
	
	// Metodos
	
	public boolean containsVertex(K id)
	{
		return vertices.contains(id);
		
	}
	public int numVertices()
	{
		return vertices.size();
	}
	
	public int numEdges()
	{
		return numEdges;
	}
	public void insertVertex(K id, Vertex<K,V> value)
	{
		vertices.put(id, value);
	}
	
	public void addEdge(K source, K dest, float weight)
	{
		Edge<K,V> existe = getEdge(source,dest);
		Vertex<K,V> origin = getVertex(source);
		Vertex<K,V> destination = getVertex(dest);
		if(existe == null)
		{
			origin.addEdge(new Edge<K,V>(origin, destination, weight));
			destination.addEdge(new Edge<K,V>(destination,origin,weight));
			numEdges+=2;
		}
	}
	
	public Vertex<K,V> getVertex(K id)
	{
	
		return vertices.get(id);
		
	}
	
	public Edge<K,V> getEdge(K idS, K idD)
	{
		Edge<K,V> buscado = null;
		Vertex<K,V> salida =vertices.get(idS);
		if(salida!=null)
		{
			buscado = salida.getEdge(idD);
		}
		int j = 1;
		return buscado;
	}
	
	public int indegree(K vertex)
	{
		return vertices.get(vertex)==null?0:vertices.get(vertex).indegree();
	}
	
	public int outdegree(K vertex)
	{
		return vertices.get(vertex)==null?0:vertices.get(vertex).outdegree();
	}
	public ILista<Edge<K,V>> adjacentEdges(K id)
	{
		ILista<Edge<K,V>> adjacentes = null;
		Vertex<K,V> vertice =vertices.get(id);
		if(vertice!=null)
		{
			adjacentes = vertice.edges();
		}
		return adjacentes;
	}
	public ILista<Vertex<K,V>> adjacentVertex(K id)
	{
		ILista<Vertex<K,V>> adjacentes = null;
		Vertex<K,V> vertice =vertices.get(id);
		if(vertice!=null)
		{
			adjacentes = vertice.vertices();
		}
		return adjacentes;
	}
	
	public ILista<Edge<K,V>> edges()
	{
		ILista<Edge<K,V>> arcos = new ArregloDinamico<>(10);
		ILista<Vertex<K,V>> vert =vertices.valueSet();
		for(int i =1;i<= vert.size();i++)
		{
			ILista<Edge<K,V>> arcosActuales = vert.getElement(i).edges();
			for(int j = 1;j<=arcosActuales.size();j++)
			{
				arcos.addLast(arcosActuales.getElement(j));
			}
		}
		
		return arcos;
	}
	public ILista<Vertex<K,V>> vertices()
	{
		return vertices.valueSet();
	}
	public void unMark()
	{
		ILista<Vertex<K,V>> vertices = vertices();
		for(int i = 1; i<=vertices.size();i++)
		{
			vertices.getElement(i).unmark();
		}
	}
	
	public void dfs(K id )
	{
		Vertex<K,V> inicio = getVertex(id);
		inicio.dfs(null);
		unMark();
	}
	
	public void bfs(K id)
	{
		Vertex<K,V> inicio = getVertex(id);
		inicio.bfs();
		unMark();
	}
	
	private ITablaSimbolos<K, NodoTS<Float, Edge<K,V>>> minPathTree(K idOrigen)
	{
		Vertex<K, V> inicio = getVertex(idOrigen);
		return inicio.minPathTree();
	}
	
	public ILista<Edge<K, V>> minPath(K idOrigen, K idDestino)
	{
		ITablaSimbolos<K, NodoTS<Float, Edge<K,V>>> tree = minPathTree(idOrigen);
		ILista<Edge<K, V>> path = new ArregloDinamico<Edge<K, V>>(100000);
		K idBusqueda = idDestino;
		NodoTS<Float, Edge<K,V>> actual;
		
		while((actual = tree.get(idBusqueda)) != null && actual.getValue() != null)
		{
			path.addLast(actual.getValue());
			idBusqueda = actual.getValue().getOrigin().getId();
		}
		
		return path;
	}
	
	public ILista<Edge<K,V>> mstPrim(K idOrigen)
	{
		if(vertices.contains(idOrigen))
		{
			ILista<Edge<K,V>> mst = getVertex(idOrigen).mstPrim();
			unMark();
			return mst;
		}
		else
		{
		  return null;
		}
	}
}