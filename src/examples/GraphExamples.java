package examples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphExamples<V, E> {

  static final private Object NUMBER = new Object();
  static final private Object VISITED = new Object();
  static final private Object DISCOVERY = new Object();

  private Graph<V, E> g;

  public GraphExamples(Graph<V, E> g) {
    this.g = g;
  }

  public boolean isConnected() {
    Vertex<V> v = g.aVertex();
    visitDFS(v);// sets the attr. VISITED to all reachable vertices
    // do we have a VISITED attr. for all vertices?
    Iterator<Vertex<V>> it = g.vertices();
    int cnt = 0;
    while (it.hasNext())
    {
      Vertex<V> w = (Vertex<V>) it.next();
      if(w.has(VISITED))
      {
        cnt++;
        w.destroy(VISITED);
      }
    }

    return g.numberOfVertices() == cnt;
  }

  private void visitDFS(Vertex<V> v) {
    v.set(VISITED, null);
    Iterator<Edge<E>> edges = g.incidentEdges(v);
    while (edges.hasNext())
    {
      Edge<E> e = (Edge<E>) edges.next();
      Vertex<V> neighbour = g.opposite(e, v);
      if(!neighbour.has(VISITED))
      {
        visitDFS(neighbour);
      }
    }
  }

  public int numberOfConnectedComponents() {
    Iterator<Vertex<V>> it = g.vertices();
    int cnt = 0;

    while (it.hasNext())
    {
      Vertex<V> v = it.next();
      if(!v.has(VISITED))
      {
        cnt++;
        visitDFS(v);
      }
      v.destroy(VISITED);
    }

    return cnt;
  }

  private void setGW(Vertex<V> s) {
    // sets for all (reachable) vertices the attribute 's' to the value
    // 'g' where 'g' is the first vertex on the shortest path
    // to 's' (considering 'hopping' distance)
    s.set(s, s);
    LinkedList<Vertex<V>> q = new LinkedList<>();
    q.addLast(s);

    while (!q.isEmpty())
    {
      Vertex<V> v = q.removeFirst();
      Iterator<Edge<E>> it = g.incidentEdges(v);
      while (it.hasNext())
      {
        Edge<E> e = (Edge<E>) it.next();
        Vertex<V> w = g.opposite(e, v);
        if(!w.has(s))
        {
          w.set(s, v);
          q.addLast(w);
        }
      }
    }
  }
  
  public void setGateways(){
    Iterator<Vertex<V>> it = g.vertices();
    while(it.hasNext()){
      setGW(it.next());
    }
  }
  
  public Vertex<V>[] shortestPath(Vertex<V> from, Vertex<V> to){
    ArrayList<Vertex<V>> al = new ArrayList<>();
    
    if(from.has(to)){
      while(from!=to){
        al.add(from);
        from = (Vertex<V>) from.get(to);
      }
      al.add(to);
      return al.toArray(new Vertex[0]);
    }
    return null;
  }

  /**
   * @param args
   * 
   */
  public static void main(String[] args) {

    // make an undirected graph
    IncidenceListGraph<String, String> g = new IncidenceListGraph<>(false);
    GraphExamples<String, String> ge = new GraphExamples<>(g);

    Vertex vA = g.insertVertex("A");
    Vertex vB = g.insertVertex("B");
    Vertex vC = g.insertVertex("C");
    Vertex vD = g.insertVertex("D");
    Vertex vE = g.insertVertex("E");
    Vertex vF = g.insertVertex("F");
    Vertex vG = g.insertVertex("G");
    Edge e_a = g.insertEdge(vA, vB, "a");
    Edge e_b = g.insertEdge(vD, vC, "b");
    Edge e_c = g.insertEdge(vD, vB, "c");
    Edge e_d = g.insertEdge(vC, vB, "d");
    Edge e_e = g.insertEdge(vC, vE, "e");
    Edge e_f = g.insertEdge(vB, vE, "f");
    Edge e_g = g.insertEdge(vD, vE, "g");
    Edge e_h = g.insertEdge(vE, vG, "h");
    Edge e_i = g.insertEdge(vG, vF, "i");
    Edge e_j = g.insertEdge(vF, vE, "j");

    System.out.println(g);
    System.out.println(ge.isConnected());
    System.out.println(ge.numberOfConnectedComponents());
    
    ge.setGateways();
    for(Vertex<String>v:ge.shortestPath(vA,vG)){
      System.out.print(v);
    };

    // A--B F
    // /|\ /|
    // / | \/ |
    // C--D--E--G
    // \ /
    // \---/
    //
  }
}
