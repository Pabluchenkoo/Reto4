package model.data_structures;

public class ListaEncadenada<T extends Comparable<T>> implements ILista<T>
{

	private NodoLista<T> first;

	private NodoLista<T> last;
	private int size;
	
	public ListaEncadenada()
	{
		first = null;
		last = null;
		size = 0;
	}
	
	public ListaEncadenada(T elem)
	{
		first = new NodoLista<T>(elem);
	}
	@Override

	public void addFirst(T elem) 
	{
		NodoLista<T> nuevo = new NodoLista<T>(elem);
		if (first == null)
		{
			first = nuevo;
			last = nuevo;
		}
		else
		{
			nuevo.cambiarSiguiente(first);
			first = nuevo;
		}
		size++;

	}

	@Override
	public void addLast(T elem) 
	{
		NodoLista<T> nuevo = new NodoLista<T>(elem);
		if (first == null)
		{
			first = nuevo;
	
		}
		else
		{
			last.cambiarSiguiente(nuevo);
		}
		last = nuevo;
		size++;
	}

	@Override
	public void insertElement(T elem, int pos) 
	{
		
		if(pos== size)
		{
			addLast(elem);
		}
		else if(pos == 1)
		{
			addFirst(elem);
			
		}
		else
		{
			NodoLista<T> nuevo = new NodoLista<T>(elem);
			NodoLista<T> anterior = first;
			int i = 1;
			
			while(anterior!= null && i!= pos)
			{
				anterior = anterior.darSiguiente();
				i++;
			}
			
			if(anterior!=null)
			{
			nuevo.cambiarSiguiente(anterior.darSiguiente());
			anterior.cambiarSiguiente(nuevo);
			}
			size++;
		}
		
	}

	@Override
	public T removeFirst() 
	{
		NodoLista<T> eliminado = first;
		if(first != null)
		{
			NodoLista<T> nuevoPrimero = first.darSiguiente();
			first = nuevoPrimero;
		}	
		size--;
		return eliminado ==null?null:eliminado.darInformation() ;
	}

	@Override
	public T removeLast() 
	{
		NodoLista<T> eliminado = last;
		if(last != null)
		{ 
			last = null;
		}	
		size--;
		return eliminado.darInformation() ;
	}

	@Override
	public T deleteElement(int pos) 
	{
		
		NodoLista<T> eliminado = null;
		if(pos== size)
		{
			eliminado = last;
			removeLast();
		}
		else if(pos == 1)
		{
			eliminado = first;
			removeFirst();
			
		}
		else
		{
			NodoLista<T> anterior = first;
			int i = 1;
			
			while(anterior!= null && i!= pos)
			{
				anterior = anterior.darSiguiente();
				i++;
			}
			eliminado = anterior.darSiguiente();
			anterior.cambiarSiguiente(anterior.darSiguiente().darSiguiente());
			size--;
		}
		
		return eliminado.darInformation();
	}

	@Override
	public T firstElement() 
	{
		if(size != 0)
		{
			return first.darInformation();
		}
		else
			return null;
	}

	@Override
	public T lastElement() 
	{
		if(size != 0)
		{
			return last.darInformation();
		}
		else
		{
			return null;
		}
	}

	@Override
	public T getElement(int pos) 
	{
		T buscado = null;
		if(pos ==1)
			buscado = first.darInformation();
		else if (pos == size)
			buscado = last.darInformation();
		else
		{
			NodoLista<T> anterior = first;
			int i = 1;
		
			while(anterior.darSiguiente()!= null && i!= pos-1)
			{
				anterior = anterior.darSiguiente();
				i++;
			}
			if(anterior.darSiguiente() != null)
				buscado = anterior.darSiguiente().darInformation();
		}
		return buscado;
	}

	@Override
	public int size() 
	{
		
		return size;
	}


	@Override
	public boolean isEmpty() 
	{
		
		return size == 0;
	}

	@Override
	public boolean isPresent(T element) 
	{
		NodoLista<T> actual = first;
		boolean present = false;
		int i = 1;
		while(actual!= null && !present)
		{
			if (actual.darInformation().compareTo(element)==0)
				present = true;
			actual = actual.darSiguiente();
			i++;
			
		}
		return present;
	}
	
	@Override
	public ILista<T> sublista(int numElementos) 
	{
		ListaEncadenada<T> subLista = new ListaEncadenada<>(); 
		boolean termino = false;
		for (int i = 1; i<= numElementos && !termino; i++)
		{
			
			subLista.addLast(this.getElement(i));
		}
		return subLista;
	}
	

	@Override
	public void exchange(int pos1, int pos2) 
	{ 
		T Epos1 = getElement(pos1) ;
		T Epos2 = getElement(pos2) ;
		
		changeElement(pos2, Epos1);
		changeElement(pos1, Epos2);
		
	}

	@Override
	public void changeElement(int pos, T elem) 
	{
		NodoLista<T> nuevo = new NodoLista<T>(elem);
		NodoLista<T> anterior = first;
		if(pos ==1)
		{
			NodoLista<T> temporal = first.darSiguiente();
			nuevo.cambiarSiguiente(temporal);
			first = nuevo;
		}
		else
		{
			int i = 1;
			while(anterior.darSiguiente()!= null&& i != pos-1)
			{
				anterior = anterior.darSiguiente();
				i++;
			}
			if(anterior.darSiguiente().darSiguiente()==null)
			{
				anterior.cambiarSiguiente(nuevo);
			}
			else
			{
				NodoLista<T> siguiente = anterior.darSiguiente().darSiguiente();
				nuevo.cambiarSiguiente(siguiente);
				anterior.cambiarSiguiente(nuevo);
			}
		}
		
	}

	@Override
	public ILista<T> subListaPos(int pos, int sizeSub) 
	{
		ListaEncadenada<T> subLista = new ListaEncadenada<>(); 
		for (int i = 0; i< (sizeSub); i++)
		{
				subLista.addLast(getElement(pos+i));
		}
		return subLista;
	}

	@Override
	public int compareTo(ILista<T> o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
