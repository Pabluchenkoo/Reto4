package model.data_structures;

public interface ICola <T extends Comparable<T>>
{
	public void enqueue(T element);
	
	public T dequeue();
	
	public T peek();
	
}