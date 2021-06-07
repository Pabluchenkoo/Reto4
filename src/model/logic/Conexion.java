package model.logic;

import model.data_structures.Edge;
import model.data_structures.Vertex;

public class Conexion<K extends Comparable<K>, V extends Comparable<V>> extends Edge<K,V> 
{
	private double capacidad;
	public Conexion(Vertex<K, V> origin, Vertex<K, V> destination, float weight,double capacidad) 
	{
		super(origin, destination, weight);
		this.capacidad = capacidad;
	}

}
