package examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.sun.org.apache.regexp.internal.recompile;


public class GraphExamples<V,E> {

	static final private Object NUMBER = new Object();
	static final private Object VISITED = new Object();
	static final private Object DISCOVERY = new Object();
	
	private Graph<V,E> g;
	public GraphExamples(Graph<V,E> g){
		this.g=g;
	}
	
	
	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		
		
		
		// make an undirected graph
		IncidenceListGraph<String,String> g = 
			new IncidenceListGraph<>(false);
		GraphExamples<String,String> ge = new GraphExamples<>(g);

		Vertex vA = g.insertVertex("A");
		Vertex vB = g.insertVertex("B");
		Vertex vC = g.insertVertex("C");
		Vertex vD = g.insertVertex("D");
		Vertex vE = g.insertVertex("E");
		Vertex vF = g.insertVertex("F");
		Vertex vG = g.insertVertex("G");
		Edge e_a = g.insertEdge(vA,vB,"a");
		Edge e_b = g.insertEdge(vD,vC,"b");
		Edge e_c = g.insertEdge(vD,vB,"c");
		Edge e_d = g.insertEdge(vC,vB,"d");
		Edge e_e = g.insertEdge(vC,vE,"e");
		Edge e_f = g.insertEdge(vB,vE,"f");
		Edge e_g = g.insertEdge(vD,vE,"g");
		Edge e_h = g.insertEdge(vE,vG,"h");
		Edge e_i = g.insertEdge(vG,vF,"i");
		Edge e_j = g.insertEdge(vF,vE,"j");

//	A--B     F
//	  /|\   /|
//	 / |  \/ |
//	C--D--E--G   
//	\     /
//	 \---/
//      
	}
}

