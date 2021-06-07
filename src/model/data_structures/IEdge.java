package model.data_structures;

public interface IEdge<K extends Comparable<K>, V extends Comparable<V>>  
{

    Vertex<K,V> getOrigin();

    Vertex<K,V> getDestination();

    float weight();

}
