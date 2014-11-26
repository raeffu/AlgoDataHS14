package examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphExamples<V, E> {

  static final private Object NUMBER = new Object();
  static final private Object VISITED = new Object();
  static final private Object DISCOVERY = new Object();
  // for dijkstra:
  static final private Object WEIGHT = new Object();
  static final private Object DISTANCE = new Object();
  static final private Object PQLOCATOR = new Object();

  private Graph<V, E> g;
  private Vertex<V>[] vertexArray;

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

  public void setGateways() {
    Iterator<Vertex<V>> it = g.vertices();
    while (it.hasNext())
    {
      setGW(it.next());
    }
  }

  public Vertex<V>[] shortestPath(Vertex<V> from, Vertex<V> to) {
    ArrayList<Vertex<V>> al = new ArrayList<>();

    if(from.has(to))
    {
      while (from != to)
      {
        al.add(from);
        from = (Vertex<V>) from.get(to);
      }
      al.add(to);
      return al.toArray(new Vertex[0]);
    }
    return null;
  }

  public int[][] getGatewayMatrix(int[][] ad) {
    int n = ad.length;
    int[][] dist = new int[n][n];
    int[][] gw = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int k = 0; k < n; k++)
      {
        dist[i][k] = ad[i][k];
        if(i != k && ad[i][k] != 1)
          dist[i][k] = n; // infinity!
        gw[i][k] = -1;
        if(ad[i][k] == 1)
          gw[i][k] = k;
      }
    for (int k = 0; k < n; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
        {
          int newDist = dist[i][k] + dist[k][j];
          if(newDist < dist[i][j])
          {
            dist[i][j] = newDist;
            gw[i][j] = gw[i][k];
          }
        }
    return gw;
  }

  public void setNumbers() {
    vertexArray = new Vertex[g.numberOfVertices()];
    Iterator<Vertex<V>> it = g.vertices();
    int num = 0;
    while (it.hasNext())
    {
      vertexArray[num] = it.next();
      vertexArray[num].set(NUMBER, num++);
    }
  }

  public int[][] getAdjacencyMatrix() {
    setNumbers();
    int n = g.numberOfVertices();
    int[][] ad = new int[n][n];
    boolean directed = g.isDirected();
    Iterator<Edge<E>> it = g.edges();
    while (it.hasNext())
    {
      Vertex<V>[] endPts = g.endVertices(it.next());
      int i = (int) endPts[0].get(NUMBER);
      int k = (int) endPts[1].get(NUMBER);
      ad[i][k] = 1;
      if(!directed)
        ad[k][i] = 1;
    }
    return ad;
  }

  private void visitDFS(int[][] ad, int p, boolean[] visited) {
    visited[p] = true;
    for (int i = 0; i < ad.length; i++)
    {
      if(ad[p][i] == 1 && !visited[i])
        visitDFS(ad, i, visited);
    }
  }

  public int[] shortestPath(int[][] ad, int from, int to) {
    // returns the vertex numbers of the shortest path
    // (hopping distance) fromn 'from' to 'to' or 'null'
    // if no path exists
    int n = ad.length;
    int[] visitedFrom = new int[n];
    Arrays.fill(visitedFrom, -1);
    visitedFrom[to] = to;
    LinkedList<Integer> q = new LinkedList<>();
    q.addLast(to); // we start at to (for directed graphs!)
    boolean found = false;
    while (!q.isEmpty() && !found)
    {
      int p = q.removeFirst();
      for (int i = 0; i < n; i++)
      {
        // we take backwards direction!
        if(ad[i][p] == 1 && visitedFrom[i] == -1)
        {
          visitedFrom[i] = p;
          q.addLast(i);
          if(i == from)
            found = true;
        }
      }
    }

    if(visitedFrom[from] == -1)
      return null;
    int len = 2;
    int p = from;
    // get the length of the path
    while (visitedFrom[p] != to)
    {
      len++;
      p = visitedFrom[p];
    }
    // now we construct the path
    int[] path = new int[len];
    for (int i = 0; i < len; i++)
    {
      path[i] = from;
      from = visitedFrom[from];
    }
    return path;
  }

  public boolean isConnected(int ad[][]) {
    int n = ad.length;
    boolean[] visited = new boolean[n];
    visitDFS(ad, 0, visited);
    for (boolean v : visited)
      if(!v)
        return false;
    return true;
  }

  public void dijkstra(Vertex<V> s) {
    // sets the attribute 's' of each vertex 'u' from wich
    // we can reach 's' to 'g' where 'g' is the gateway
    // of 'u' on the shortest path from 'u' to 's'
    MyPriorityQueue<Double, Vertex<V>> pq = new MyPriorityQueue<>();
    Iterator<Vertex<V>> it = g.vertices();

    while (it.hasNext())
    {
      Vertex<V> v = it.next();
      v.set(DISTANCE, Double.POSITIVE_INFINITY);
      Locator<Double, Vertex<V>> loc = pq.insert(Double.POSITIVE_INFINITY, v);
      v.set(PQLOCATOR, loc);
    }
    s.set(DISTANCE, 0.0);
    pq.replaceKey((Locator<Double, Vertex<V>>) s.get(PQLOCATOR), 0.0);

    while (!pq.isEmpty())
    {
      Vertex<V> u = pq.removeMin().element();
      Iterator<Edge<E>> itEdge = g.incidentInEdges(u);
      while (itEdge.hasNext())
      {
        Edge<E> e = itEdge.next();
        double weight = 1.0;// default weight
        if(e.has(WEIGHT))
          weight = (Double) e.get(WEIGHT);
        Vertex<V> z = g.opposite(e, u);
        Double r = (Double) u.get(DISTANCE) + weight;

        if(r < (Double) z.get(DISTANCE))
        {
          z.set(DISTANCE, r);
          z.set(s, u); // set gateway
          pq.replaceKey((Locator<Double, Vertex<V>>) z.get(PQLOCATOR), r);
        }
      }
    }
  }

  /**
   * @param args
   * 
   */
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
    e_f.set(WEIGHT, 7.0);
    Edge e_g = g.insertEdge(vD, vE, "g");
    Edge e_h = g.insertEdge(vE, vG, "h");
    e_h.set(WEIGHT, 3.0);
    Edge e_i = g.insertEdge(vG, vF, "i");
    Edge e_j = g.insertEdge(vF, vE, "j");

    System.out.println(g);
    ge.setGateways();
    System.out.print("Path: ");
    Vertex<String>[] path = ge.shortestPath(vA, vG);
    if(path == null)
      System.out.println("no path");
    else
    {
      for (Vertex<String> v : path)
        System.out.print(v);
    }
    System.out.println();
    ge.setNumbers();
    int[][] ad = ge.getAdjacencyMatrix();
    System.out.println(ge.isConnected(ad));
    int[] spath = ge.shortestPath(ad, (int) vC.get(NUMBER), (int) vF.get(NUMBER));
    if(spath == null)
      System.out.println("no path");
    else
    {
      for (int i = 0; i < spath.length; i++)
      {
        System.out.println(ge.vertexArray[spath[i]]);
      }
    }

    int[][] gw = ge.getGatewayMatrix(ad);
    int n = gw.length;
    for (int i = 0; i < n; i++)
      System.out.println(ge.vertexArray[i] + ", " + i);
    for (int i = 0; i < n; i++)
    {
      System.out.println();
      for (int k = 0; k < n; k++)
      {
        System.out.print(gw[i][k] + ", ");
      }
    }

    // A__B     F
    //   /|\   /|
    //  / | \ / |
    // C__D__E__G
    // \     /
    //  \___/
    //
  }
}
