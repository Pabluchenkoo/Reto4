  
package model.logic;

import model.data_structures.Vertex;

public class LandingPointSub<K extends Comparable<K>, V extends Comparable<V>> extends Vertex<K,V>
{

	private String pais;
	private String ciudad;
	private double latitud;
	private double longitud;
	public LandingPointSub(K id, V value, String pais,String ciudad,double lat,double longt) 
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
	public String darCiudad()
	{
		return ciudad;
	}

	public double darLongitud()
	{
		return longitud;
	}
	
	public double darLatitud()
	{
		return latitud;
	}
}
