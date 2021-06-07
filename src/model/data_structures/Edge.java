package model.data_structures;

import model.data_structures.Vertex;

public class Edge <K extends  Comparable<K>,V extends Comparable<V>> implements IEdge<K, V>, Comparable<Edge<K, V>>
{
	private float weight;
	
	private Vertex<K,V> origin;
	
	private Vertex<K,V> destination;
	
	private int numberOfTrips;

	public Edge(Vertex<K,V> origin, Vertex<K,V>destination, float weight){
		this.weight = weight;
		this.origin = origin;
		this.destination = destination;
	}

	public Vertex<K, V> getOrigin() 
	{
		return origin;
	}

	public Vertex<K, V> getDestination() 
	{
		return destination;
	}

	public float weight() 
	{
		return weight;
	}


	public void setWeight(float weight) 
	{
		this.weight = weight;
	}

	public int compareTo(Edge<K, V> o) 
	{
		return Double.compare(weight, o.weight());
	}

}
