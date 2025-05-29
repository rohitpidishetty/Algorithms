import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Rohit Viswakarma, Pidishetti
 * @R-Number: 11908362
 * @Annexure: Assignment - 4
 * @Title: Defining a Minimum Spanning Tree (MST) using Kruskal's algorithm.
 * @Comments: I have provided a detailed code about defining an MST using
 *            kruskal's algorithm. I have created a class 'Graph.java' in which
 *            I have defined my graph structure for all three types of graphs
 *            i.e., dense graph, sparse graph and a graph with negative weights
 *            in it. I have used an adjacency list over a matrix, because of the
 *            space complexity that a matrix would consume to store a graph. All
 *            the three graphs are defined in the java file 'Graph.java'. The
 *            file also contains few essential methods which i'll be using in
 *            this code.
 * @Output: A fully connected graph with minimum cost.
 * @Links: https://nfrac-in.web.app/
 */

public class KruskalsAlgorithm {
  /**
   * The class Collection is an inner class that I have implemented so that I can
   * be able to store my neighbor vertices and pull out the minimal weighted
   * neighbor each time. The reason why the class Collection implements Comparable
   * class of generic type 'Collection' is to return the minimal weight from the
   * Object which is in the form of Collection@(U, V, Weight). The method
   * compareTo helps to return the minimal weighted object from the priority queue
   * which implements the minimum heap by default.
   */
  static class Collection implements Comparable<Collection> {
    int u;
    int v;
    int wt;

    /**
     * @param u
     * @param v
     * @param wt
     */
    Collection(int u, int v, int wt) {
      this.u = u;
      this.v = v;
      this.wt = wt;
    }

    /**
     * The constructor Collection will be called at the time of object creation and
     * the parameters will be taken into consideration and will be assigned to the
     * class variables.
     */

    @Override
    public int compareTo(Collection o) {
      return this.wt - o.wt;
    }

    /**
     * The method has been over-ridden from the parent class Comparable, to return
     * the minimal weighted node from the priority queue.
     */
  }

  /**
   * The array 'Introducer' will keep a track of the parent/predecessor node which
   * has introduced the child node.
   */

  static int Introducer[];

  /**
   * 
   * @param Node
   * @return
   *         The method 'find' will return the parent of the current child node.
   *         If the introducer of the current node is equal to the node then we
   *         will simply return the node if not we will recursively call the
   *         method of the for the introducer of the child node.
   */
  static int find(int Node) {
    return Introducer[Node] == Node ? Node : find(Introducer[Node]);
  }

  /**
   * @param X1
   * @param X2
   *           The method 'union' will associate 2 nodes if the introducers are
   *           different, else we will simply return.
   */

  static void union(int X1, int X2) {
    int introducerOf_X1 = find(X1);
    int introducerOf_X2 = find(X2);
    if (introducerOf_X1 == introducerOf_X2)
      return;
    Introducer[introducerOf_X2] = introducerOf_X1;
  }

  static void kruskal_mst(int V, List<ArrayList<ArrayList<Integer>>> graph) {
    /**
     * The main principle of kruskal's algorithm lies on the strategy of finding
     * associations between nodes and including them if they arent. Firstly, all the
     * edges will be sorted in ascending order, and we start picking up the edges
     * with minimum weight in-order and halt the program when all the vertices are
     * connected.
     */
    boolean associationMatrix[][] = new boolean[V][V];
    /**
     * The visited matrix will keep a track of the association between '2' nodes.
     */
    ArrayList<KruskalsAlgorithm.Collection> edges = new ArrayList<>();
    /**
     * Lets loop and gather all the nodes, so that we can sort them in-order. We
     * only add the node if its not associated with its parent, else not.
     */
    for (int v = 0; v < graph.size(); v++) {
      /**
       * We loop for every node/vertex and run an inner loop for all its neighbors and
       * add the node if its not associated with its parent and then mark it as
       * associated in the association matrix.
       */
      for (int j = 0; j < graph.get(v).size(); j++) {
        ArrayList<Integer> Node = graph.get(v).get(j);
        if (!associationMatrix[v][Node.get(0)]) {
          associationMatrix[v][Node.get(0)] = true;
          associationMatrix[Node.get(0)][v] = true;
          edges.add(new Collection(v, Node.get(0), Node.get(1)));
        }
      }
    }

    /**
     * Defining the parents/predecessors of each vertex as itself in the graph.This
     * will be later modified in the function union.
     */
    Introducer = new int[V];
    for (int i = 0; i < V; i++)
      Introducer[i] = i;

    /**
     * Sorting the objects with respect to their weights in ascending order using
     * the method sort, that is provided by the Collections framework.
     */
    Collections.sort(edges);
    /**
     * The variable count has to be less than V as we need V-1 edges to connect the
     * graph completely. We pull out each sorted edge and associate them using the
     * 'union' function if and only if their predecessors which will be given by the
     * method 'find', dont match.
     */
    int count = 1;
    for (int i = 0; count < V; i++) {
      KruskalsAlgorithm.Collection Node = edges.get(i);
      int X1 = find(Node.u);
      int X2 = find(Node.v);
      if (X1 != X2) {
        union(X1, X2);
        /**
         * The method 'build' is defined in the class Graph, it helps to
         * generate a list of paths that explicitly show the linkage between the nodes
         * that define the minimal spanning tree, this method will be initiated if and
         * only if the current parent and neighbor node in the present configuration are
         * not equal to each other. The method 'updateWeight' updates the
         * minimum weight that takes to define an MST. The variable 'count' will be
         * incremented in every iteration.
         */
        Graph.updateWeight(Node.wt);
        Graph.build(Node.u, Node.v);
        count++;
      }
    }
    /**
     * The method 'printTrace' is defined in the class Graph, this method prints the
     * total weight and the MST for a given problem i.e., graph.
     */
    Graph.printTrace();
  }

  /**
   * @param args
   *             This is the main method where the program execution starts from,
   *             on running the program using the following command 'java
   *             KruskalsAlgorithm' after compiling the code using the command
   *             'javac KruskalsAlgorithm.java'.
   */
  public static void main(String[] args) {
    /**
     * Creating an object to the class 'Graph' to pull out the 3 types of graphs.
     */
    Graph graph = new Graph();
    /**
     * Let's now run the kruskal_mst algorithm on a dense graph and analyse the
     * results.
     */
    System.out.println("Running MST using Kruskals algorithm on a Dense graph");
    KruskalsAlgorithm.kruskal_mst(graph.denseGraph.size(), graph.denseGraph);
    /**
     * Output:
     * Running MST using Kruskals algorithm on a Dense graph
     * Total Weight : 51
     * Minimum Spanning Tree Trace: [V0 -> V2, V1 -> V3, V4 -> V5, V12 -> V14, V0 ->
     * V3, V7 -> V12, V8 -> V9, V0 -> V6, V1 -> V8, V7 -> V10, V7 -> V9, V13 -> V14,
     * V2 -> V5, V3 -> V11]
     */
    System.out.println();
    /**
     * Let's now run the kruskal_mst algorithm on a sparse graph and analyse the
     * results.
     */
    System.out.println("Running MST using Kruskals algorithm on a Sparse graph");
    KruskalsAlgorithm.kruskal_mst(graph.sparseGraph.size(), graph.sparseGraph);
    /**
     * Output:
     * Running MST using Kruskals algorithm on a Sparse graph
     * Total Weight : 89
     * Minimum Spanning Tree Trace: [V8 -> V9, V2 -> V4, V5 -> V10, V0 -> V1, V3 ->
     * V7, V7 -> V11, V0 -> V3, V12 -> V14, V2 -> V6, V13 -> V14, V1 -> V5, V10 ->
     * V13, V3 -> V9, V1 -> V6]
     */
    System.out.println();
    /**
     * Let's now run the kruskal_mst algorithm on a graph that has negative weights
     * and analyse the results.
     */
    System.out.println("Running MST using Kruskals algorithm on a Negative weighted graph");
    KruskalsAlgorithm.kruskal_mst(graph.negativeGraph.size(), graph.negativeGraph);
    /**
     * Output:
     * Running MST using Kruskals algorithm on a Negative weighted graph
     * Total Weight : -11
     * Minimum Spanning Tree Trace: [V4 -> V6, V3 -> V6, V6 -> V8, V4 -> V5, V2 ->
     * V4, V6 -> V7, V8 -> V9, V0 -> V2, V0 -> V1, V9 -> V10]
     */
  }
}
