package model.data_structures;

public interface IVertex<K extends Comparable<K>, V extends Comparable<V>>  {

    K getId();

    V getInfo();

    boolean getMark();

    void addEdge( Edge<K,V> edge );

    void mark(Edge<K,V> edgeTo);

    void unmark();

    int outdegree();


    int indegree();


    Edge<K,V> getEdge(K vertex);


    ILista<Vertex<K,V>> vertices();


    ILista<Edge<K,V>> edges();
}
