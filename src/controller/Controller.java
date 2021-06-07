package controller;

import java.util.Scanner;

import model.data_structures.GrafoListaAdyacencia;
import model.data_structures.ILista;
import model.data_structures.Vertex;
import model.logic.LandingPointSub;
import model.logic.LandingPointer;
import model.logic.Modelo;
import view.View;

public class Controller 
{
	/**
	 *  Instancia del Modelo
	 */
	private Modelo modelo;
	
	/**
	 *  Instancia de la Vista
	 */
	private View view;
	
	/**
	 * Crear la vista y el modelo del proyecto
	 */
	public Controller ()
	{
		modelo = new Modelo();
		view = new View();
		
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		view.printMenu();
		while( !fin )
			{
				int option = lector.nextInt();
				switch(option)
				{
					case 1:
						
						break;
					case 2:
						view.printMessage("Los landing points submarinos que sirven de interconexion son: ");
						ILista<Vertex<String, Integer>> landing = modelo.req2();
						int c = 0;
						for(int i= 1; i<= landing.size();i++)
						{
							LandingPointSub<String,Integer> actual = (LandingPointSub<String,Integer>) landing.getElement(i);
							view.printMessage("NOMBRE: "+actual.getId()+" PAIS: "+actual.darPais()+" INDENTIFICADOR: "+actual.getInfo());
							c+= actual.edges().size();
						}
						view.printMessage("Con un total de cables conectados de "+c);
						break;
							
					case 3:
						view.printMessage("Ingresar el nombre de la capital del pais de salida:");
						String ciudadSal = lector.next();
						view.printMessage("Ingresar el nombre de la capital del pais de destino:");
						String ciudadDes = lector.next();
						modelo.req3(ciudadSal, ciudadDes);
						break;
					case 4:
						modelo.req4();
						break;
						
					case 5:
						view.printMessage("Ingrese el nombre del landing point a analizar: ");
						String landingPoint= lector.next();
						ILista<Vertex<String,Integer>> afectados = modelo.req5(landingPoint);
						view.printMessage("El numero total de paises afectados son "+afectados.size());
						for(int i = 1; i<=afectados.size();i++)
						{
							LandingPointer<String,Integer> actual = (LandingPointer<String,Integer>) afectados.getElement(i);
							view.printMessage(actual.getId());
						}
						break;
					
					case 6:
						view.printMessage("--------- \n Hasta pronto !! \n---------"); 
						lector.close();
						fin = true;
						break;
				
				}
			}
		
	}

}
