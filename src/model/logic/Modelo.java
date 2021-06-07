package model.logic;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import model.data_structures.ArregloDinamico;
import model.data_structures.Edge;
import model.data_structures.GrafoListaAdyacencia;
import model.data_structures.ILista;
import model.data_structures.TablaHashLinearProbing;
import model.data_structures.Vertex;
import model.utils.Ordenamiento;

public class Modelo 
{
	private GrafoListaAdyacencia<String,Integer > grafo;
	private TablaHashLinearProbing<Integer,Vertex<String,Integer>> puntosDeConexion;
	private Haversine haversine;
	
	public Modelo()
	{
		grafo = new GrafoListaAdyacencia<String,Integer >(5000);
		puntosDeConexion = new  TablaHashLinearProbing<>(5000, 0.5);
		haversine = new Haversine();
		try
		{
			cargarLandingPoints();
			cargaConectionsLandingPointsSub();
			cargaCountries();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void cargarLandingPoints() throws NumberFormatException, ParseException
	{
		try
		{
			
			Reader in = new FileReader("./data/landing_points.csv");
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for(CSVRecord record:records)
			{
				int id = Integer.parseInt(record.get(0));
				String idNom= record.get(1);
				String[] loc = record.get(2).split(",");
				String ciudad = loc[0];
				String pais = loc.length==2?loc[1].trim():loc[2].trim();
				double lat = Double.parseDouble(record.get(3));
				double longt = Double.parseDouble(record.get(4));
				
				LandingPointSub<String,Integer> actual = new LandingPointSub<>(idNom, id, pais, ciudad, lat, longt);
				puntosDeConexion.put(id, actual);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void cargaConectionsLandingPointsSub() throws NumberFormatException, ParseException
	{
		try
		{
			int i = 1;
			Reader in = new FileReader("./data/connections.csv");
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for(CSVRecord record:records)
			{
				int idOrigin = Integer.parseInt(record.get(0));
				int idDestination = Integer.parseInt(record.get(1));
				String cableid = record.get(3);
				LandingPointSub<String, Integer> o = (LandingPointSub<String,Integer>) puntosDeConexion.get(idOrigin);
				LandingPointSub<String, Integer> d = (LandingPointSub<String,Integer>) puntosDeConexion.get(idDestination);
				LandingPointSub<String,Integer> origin = new LandingPointSub<>(o.getId()+cableid,o.getInfo(),o.darPais(),o.darCiudad(),o.darLatitud(),o.darLongitud());
				LandingPointSub<String,Integer> dest = new LandingPointSub<>(d.getId()+cableid,d.getInfo(),d.darPais(),d.darCiudad(),d.darLatitud(),d.darLongitud());
				if(grafo.containsVertex(origin.getId())==false)
				{
					grafo.insertVertex(o.getId()+cableid, origin);
				}
				if(grafo.containsVertex(dest.getId())==false)
				{
					grafo.insertVertex(d.getId()+cableid, dest);
				}	
				float distance = (float) haversine.distance(origin.darLatitud(), origin.darLongitud(), dest.darLatitud(), dest.darLongitud());
				
				grafo.addEdge(o.getId()+cableid, d.getId()+cableid,distance);	
				i++;
			}
			agregarConexionesMismosLandingPoints();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void cargaCountries()throws NumberFormatException, ParseException
	{
		ILista<Vertex<String,Integer>> vertices = grafo.vertices();
		int i = 1;
		try
		{
			Reader in = new FileReader("./data/countries.csv");
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for(CSVRecord record:records)
			{
				if(!record.get(1).isBlank())
				{
					String pais = record.get(0);
					String capital = record.get(1);
					double lat = Double.parseDouble(record.get(2)); 
					double longt = Double.parseDouble(record.get(3));
					LandingPointer<String, Integer> actual = new LandingPointer<>(capital,i, pais, capital, lat, longt);
					grafo.insertVertex(capital, actual);
					for(int j = 1;j<= vertices.size();j++)
					{
						LandingPointSub<String, Integer> actualV = (LandingPointSub<String, Integer>) vertices.getElement(j);
						String pais1 = actualV.darPais();
						if(pais.compareToIgnoreCase(pais1)==0)
						{
							float dis = (float) haversine.distance(actualV.darLatitud(), actualV.darLongitud(),lat, longt);
							grafo.addEdge(actualV.getId(), capital, dis);
						}
					}
					i++;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(i);
			e.printStackTrace();
		}
	}
	private void agregarConexionesMismosLandingPoints()
	{
		ILista<Edge<String,Integer>> arcos = grafo.edges();
		for(int i = 1; i<=arcos.size();i++)
		{
			Vertex<String,Integer> originActuali = arcos.getElement(i).getOrigin();
			Vertex<String,Integer> destinoActuali = arcos.getElement(i).getDestination();
			for(int j = i+1; j<arcos.size();j++)
			{
				Vertex<String,Integer> originActualj = arcos.getElement(j).getOrigin();
				Vertex<String,Integer> destinoActualj = arcos.getElement(j).getDestination();
				if((originActuali.getInfo()== originActualj.getInfo())&&(destinoActuali.getInfo()==destinoActualj.getInfo()) )
				{
					if((originActuali.getId().compareToIgnoreCase(originActualj.getId())!=0)&&(destinoActuali.getId().compareToIgnoreCase(destinoActualj.getId())!=0))
					{
						grafo.addEdge(originActuali.getId(), originActualj.getId(), 100/1000);
						grafo.addEdge(destinoActuali.getId(), destinoActualj.getId(),100/1000 );
					}
				}
			}	
		}
	}
	public GrafoListaAdyacencia<String,Integer> darGrafo()
	{
		return grafo;
	}
	public int darGradoConectividadDePunto(String nombre)
	{
		Vertex<String,Integer> buscado = grafo.getVertex(nombre);
		return buscado != null?buscado.vertices().size():0;
	}
	
    //-----------------------------------------------	
	//Requerimientos
	//-----------------------------------------------	
	public boolean req1(String landingP1, String landingP2)
	{
		boolean estan = false;
		Edge<String,Integer> arco = grafo.getEdge(landingP1, landingP2);
		if(arco!=null)
		{
			if(arco.weight()==100/1000)
			{
				estan = true;
			}
		}
		return estan;
	}
	
	public ILista<Vertex<String,Integer>> req2()
	{
		ILista<Vertex<String,Integer>> verticesSub = new ArregloDinamico<>(10);
		ILista<Vertex<String, Integer>> vertices = grafo.vertices();
		for(int i =1; i<= vertices.size();i++)
		{
			Vertex<String,Integer> actual = vertices.getElement(i);
			String clase = actual.getClass().getName();
			if(clase.contains("LandingPointSub"))
			{
				if(actual.outdegree()>1)
				{
					verticesSub.addLast(actual);
				}	
			}
		}
		return verticesSub;
	}
	
	public void req3(String pais1, String pais2)
	{
		ILista<Edge<String,Integer>> rutaArcos = grafo.minPath(pais1, pais2);
		double pesoTotal = 0;
		for(int i = 1; i<= rutaArcos.size();i++)
		{
			float dist = rutaArcos.getElement(i).weight();
			String salida = rutaArcos.getElement(i).getOrigin().getId();
			String dest = rutaArcos.getElement(i).getOrigin().getId();
			System.out.println(salida +"---------->"+dest+ " con una distancia de: "+dist+"km");
			pesoTotal+= dist;
		}
		System.out.println("Con una distancia minima total de "+pesoTotal+"km");
	}
	
	public void req4()
	{
		ILista<Vertex<String, Integer>> vertices = grafo.vertices();
		ILista<Edge<String,Integer>> max = null;
		for(int i = 1; i<= vertices.size();i++)
		{
			ILista<Edge<String,Integer>> actual = grafo.mstPrim(vertices.getElement(i).getId());
			if(actual.size()>= max.size())
			{
				max = actual;
			}
		}
		float dist = 0;
		for(int i = 1;i<=max.size();i++)
		{
			dist+=max.getElement(i).weight();
		}
		System.out.println("El numero total de nodos conectados es "+max.size());
		System.out.println("El costo total seria de "+dist+"km");
	}
	public ILista<Vertex<String, Integer>> req5(String landingPoint)
	{
		ILista<Vertex<String,Integer>> afectados = new ArregloDinamico<>(10);
		Vertex<String, Integer> danado = grafo.getVertex(landingPoint);
		ILista<Vertex<String,Integer>> adjacentes = danado.vertices();
		for(int i = 1;i <=adjacentes.size();i++)
		{
			Vertex<String, Integer> actual = adjacentes.getElement(i);
			String clase = actual.getClass().getName();
			if(clase.contains("LandingPointTerr"))
			{
				LandingPointer<String, Integer> actualTerr =(LandingPointer<String, Integer>) actual;
				Edge<String, Integer> arco = grafo.getEdge(landingPoint, actual.getId());
				actualTerr.setDistancia(arco.weight());
				afectados.addLast(actual);
			}
		}
		Ordenamiento<Vertex<String, Integer>> ordenamiento = new Ordenamiento<>();
		Comparator<Vertex<String, Integer>> comparador = new LandingPointer.comparadorXDistancia();
		ordenamiento.ordenarShell(afectados, comparador, false);
		return afectados;
	}
}