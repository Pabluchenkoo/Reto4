package model.data_structures;

public class ColaEncadenada <T extends Comparable<T>> extends ListaEncadenada<T> implements ICola<T>
{

	public void enqueue(T element)
	{
		this.addLast(element);
	}
	
	public T dequeue()
	{
		return this.removeFirst();
	}
	
	public T peek()
	{
		return this.firstElement();
	}
}