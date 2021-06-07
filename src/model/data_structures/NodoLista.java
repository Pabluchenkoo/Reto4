package model.data_structures;

public class NodoLista <T extends Comparable <T>>
{
	private NodoLista<T> siguiente;
	
	private T information;
	
	public NodoLista (T info)
	{
		information=info;
		siguiente=null;
		this.information = info;
	}
	public void cambiarSiguiente(NodoLista<T> pSiguiente)
	{
		siguiente=pSiguiente;
	}
	public NodoLista<T> darSiguiente()
	{
		return siguiente;
	}
	public T darInformation()
	{
		return information;
	}
	public void cambiarInformation(T pInformation)
	{
		information=pInformation;
	}
	
}
