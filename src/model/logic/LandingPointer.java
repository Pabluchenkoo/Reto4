package model.logic;

import java.util.Comparator;

import model.data_structures.Vertex;

public class LandingPointer<K extends Comparable<K>, V extends Comparable<V>> extends Vertex<K,V> 
{
	private String pais;
	private String ciudad;
	private double latitud;
	private double longitud;
	private float distancia;
	
	public LandingPointer(K id, V value, String pais,String ciudad,double lat,double longt) 
	{
		super(id, value);
		this.pais = pais;
		this.ciudad = ciudad;
		latitud = lat;
		longitud = longt;
	}
	public String darPais()
	{
		return pais;
	}
	public double darLongitud()
	{
		return longitud;
	}
	
	public double darLatitud()
	{
		return latitud;
	}
	
	public void setDistancia(float distancia)
	{
		this.distancia = distancia;
	}
	
	public static class comparadorXDistancia implements  Comparator<Vertex<String, Integer>>
	{

		@Override
		public int compare(Vertex o1, Vertex o2) 
		{
			LandingPointer<String, Integer> oL1 = (LandingPointer<String, Integer>) o1;
			LandingPointer<String, Integer> oL2 = (LandingPointer<String, Integer>) o2;
			float dif =  oL1.distancia-oL2.distancia;
			if (dif == 0)
				return 0;
			else if(dif <0)
				return -1;
			else
				return 1;
		}

	
		
	}
}