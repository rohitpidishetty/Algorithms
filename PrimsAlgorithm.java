import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Author: Rohit Viswakarma, Pidishetti
 * @R-Number: 11908362
 * @Annexure: Assignment - 4
 * @Title: Defining a Minimum Spanning Tree (MST) using Prim's algorithm.
 * @Comments: I have provided a detailed code about defining an MST using
 *            prims's algorithm. I have created a class 'Graph.java' in which I
 *            have defined my graph structure for all three types of graphs
 *            i.e., dense graph, sparse graph and a graph with negative weights
 *            in it. I have used an adjacency list over a matrix, because of the
 *            space complexity that a matrix would consume to store a graph. All
 *            the three graphs are defined in the java file 'Graph.java'. The
 *            file also contains few essential methods which i'll be using in
 *            this code.
 * @Output: A fully connected graph with minimum cost.
 * @Links: https://nfrac-in.web.app/
 */

public class PrimsAlgorithm {
  /**
   * The class Collection is an inner class that I have implemented so that I can
   * be able to store my neighbor vertices and pull out the minimal weighted
   * neighbor each time. The reason why the class Collection implements Comparable
   * class of generic type 'Collection' is to return the minimal weight from the
   * Object which is in the form of Collection@(parentvertex, neighborvertex,
   * Weight). The method compareTo helps to return the minimal weighted object
   * from the priority queue which implements the minimum heap by default.
   */
  static class Collection implements Comparable<Collection> {
    int parent;
    int weight;
    int neighbor;

    /**
     * 
     * @param parent
     * @param weight
     * @param neighbor
     */

    Collection(int parent, int weight, int neighbor) {
      this.parent = parent;
      this.weight = weight;
      this.neighbor = neighbor;
    }

    /**
     * The constructor Collection will be called at the time of object creation and
     * the parameters will be taken into consideration and will be assigned to the
     * class variables.
     */

    @Override
    public int compareTo(Collection o) {
      return this.weight - o.weight;
    }

    /**
     * The method has been over-ridden from the parent class Comparable, to return
     * the minimal weighted node from the priority queue.
     */
  }

  /**
   * @param graph
   *              The method prim_mst will accept a graph that is in the form of 3
   *              dimensional adjacency list. I have also maintained a boolean
   *              visited array just to keep track of the nodes that have already
   *              been visited. And so that we do not visit a node again and again
   *              as it violates the graph traversal property. I have initialised
   *              the visited boolean array with false initally. Later on I will
   *              be marking the indexes as true once the node has been visited.
   *              The priority queue helps us to retrieve the node with less
   *              weight. This I have initially passed in the object as
   *              Collection@(parent:0,weight:0,neighbor:0) thats the starting
   *              vertex's configuration. We have to concentrate on the type of
   *              the priority queue as its type is a generic type of 'Collection'
   *              class.
   */

  static void prim_mst(List<ArrayList<ArrayList<Integer>>> graph) {
    boolean[] Visited = new boolean[graph.size()];
    Arrays.fill(Visited, false);
    PriorityQueue<PrimsAlgorithm.Collection> queue = new PriorityQueue<>();
    queue.add(new Collection(0, 0, 0));
    /**
     * This while loop will be running until the queue gets empty i.e., until all
     * the queue elemets get dequeued. The object configuration i.e., the
     * node/vertex will be retrieved using the dequeue operation poll(). Then all
     * its neighbor will be pulled from the graph and will be stored intot the
     * variable 'neighbors'.
     */
    while (!queue.isEmpty()) {
      PrimsAlgorithm.Collection vertex = queue.poll();
      List<ArrayList<Integer>> neighbors = graph.get(vertex.neighbor);
      /**
       * We will skip the loop if at all the neighbor has already been visited.
       */
      if (Visited[vertex.neighbor])
        continue;
      /**
       * If not, i.e., if a neighbor is not visited then we mark it as visited i.e.,
       * as true. The method 'build' is defined in the class Graph, it helps to
       * generate a list of paths that explicitly show the linkage between the nodes
       * that define the minimal spanning tree, this method will be initiated if and
       * only if the current parent and neighbor node in the present configuration are
       * not equal to each other. The method 'updateWeight' updates the
       * minimum weight that takes to define an MST.
       */
      Graph.updateWeight(vertex.weight);

      if (vertex.parent != vertex.neighbor)
        Graph.build(vertex.parent, vertex.neighbor);

      Visited[vertex.neighbor] = true;
      /**
       * Now we run a loop for all the neighbors of the current vertex and if that
       * particular neighbor vertex has not been visited then we enqueue it into the
       * priority queue. If it has been visited already then we simple ignore that
       * node. The method 'getIntroducer' helps to define the parent of the current
       * node i.e., the node that has introduced this current child node. I have
       * perposefully maintained this predecessor node to define the proper linkage.
       */
      neighbors.forEach(neighbor -> {
        /**
         * The node will be stored at the index '0' and the weight associated with it
         * will be at the index '1'.
         */
        int Node = neighbor.get(0);
        if (!Visited[Node]) {
          int Weight = neighbor.get(1);
          queue.add(new Collection(getIntroducer(neighbor, graph), Weight, Node));
        }
      });
    }
    /**
     * The method 'printTrace' is defined in the class Graph, this method prints the
     * total weight and the MST for a given problem i.e., graph.
     */
    Graph.printTrace();
  }

  /**
   * 
   * @param child
   * @param graph
   * @return
   *         This method helps to define the predecessor of any given child node.
   */
  static int getIntroducer(ArrayList<Integer> child, List<ArrayList<ArrayList<Integer>>> graph) {
    for (int Parent = 0; Parent < graph.size(); Parent++) {
      /**
       * Parent loop runs for all the parent vertices in the graph. The 'j' loops
       * through the neighbors of the Parent. The if condition determines the
       * predecessor of a given child.
       */
      for (int j = 0; j < graph.get(Parent).size(); j++) {
        if (graph.get(Parent).get(j).get(0) == child.get(0) && graph.get(Parent).get(j).get(1) == child.get(1))
          return Parent;
      }
    }
    return 0;
  }

  /**
   * @param args
   *             This is the main method where the program execution starts from,
   *             on running the program using the following command 'java
   *             PrimsAlgorithm' after compiling the code using the command 'javac
   *             PrimsAlgorithm.java'.
   */

  public static void main(String[] args) {
    /**
     * Creating an object to the class 'Graph' to pull out the 3 types of graphs.
     */
    Graph graph = new Graph();
    /**
     * Let's now run the prim_mst algorithm on a dense graph and analyse the
     * results.
     */
    System.out.println("Running MST using Prims algorithm on a Dense graph");
    PrimsAlgorithm.prim_mst(graph.denseGraph);
    /**
     * Output:
     * Running MST using Prims algorithm on a Dense graph
     * Total Weight : 51
     * Minimum Spanning Tree Trace: [V0 -> V2, V0 -> V3, V3 -> V1, V0 -> V6, V1 ->
     * V8, V8 -> V9, V8 -> V12, V12 -> V14, V12 -> V7, V7 -> V10, V14 -> V13, V9 ->
     * V4, V4 -> V5, V3 -> V11]
     */
    System.out.println();
    /**
     * Let's now run the prim_mst algorithm on a sparse graph and analyse the
     * results.
     */
    System.out.println("Running MST using Prims algorithm on a Sparse graph");
    PrimsAlgorithm.prim_mst(graph.sparseGraph);
    /**
     * Output:
     * Running MST using Prims algorithm on a Sparse graph
     * Total Weight : 89
     * Minimum Spanning Tree Trace: [V0 -> V1, V0 -> V3, V3 -> V7, V7 -> V11, V1 ->
     * V5, V5 -> V10, V10 -> V13, V13 -> V14, V14 -> V12, V3 -> V9, V9 -> V8, V1 ->
     * V6, V6 -> V2, V2 -> V4]
     */
    System.out.println();
    /**
     * Let's now run the prim_mst algorithm on a graph that has negative weights
     * associated with it and analyse the results.
     */
    System.out.println("Running MST using Prims algorithm on a Negative weighted graph");
    PrimsAlgorithm.prim_mst(graph.negativeGraph);
    /**
     * Output:
     * Running MST using Prims algorithm on a Negative weighted graph
     * Total Weight : -11
     * Minimum Spanning Tree Trace: [V0 -> V2, V2 -> V4, V4 -> V6, V6 -> V3, V6 ->
     * V8, V4 -> V5, V6 -> V7, V8 -> V9, V0 -> V1, V9 -> V10]
     */
  }
}
