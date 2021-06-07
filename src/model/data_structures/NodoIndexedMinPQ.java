package model.data_structures;
public class NodoIndexedMinPQ <K extends Comparable<K>, IK extends Comparable<IK>, V> extends NodoTS<K, V>
{
	private IK llave;
	
	public NodoIndexedMinPQ(K llave, IK llaveIndx, V value)
	{
		super(llave, value);
		this.llave = llaveIndx;
	}
	
	public IK getIndexedKey()
	{
		return llave;
	}
}
