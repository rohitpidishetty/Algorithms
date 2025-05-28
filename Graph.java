import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
  int[][][] dense = {
      { { 8, 5 }, { 1, 7 }, { 3, 3 }, { 2, 1 }, { 6, 4 } },
      { { 0, 7 }, { 8, 4 }, { 3, 2 }, { 7, 8 }, { 4, 11 } },
      { { 0, 1 }, { 5, 6 }, { 6, 9 }, { 8, 14 } },
      { { 0, 3 }, { 1, 2 }, { 11, 7 }, { 6, 13 }, { 4, 11 } },
      { { 3, 11 }, { 1, 11 }, { 5, 2 }, { 6, 18 }, { 8, 13 }, { 9, 6 } },
      { { 2, 6 }, { 4, 2 }, { 7, 21 }, { 6, 14 } },
      { { 5, 14 }, { 0, 4 }, { 2, 9 }, { 3, 13 }, { 4, 18 }, { 10, 8 } },
      { { 5, 21 }, { 1, 8 }, { 9, 5 }, { 12, 3 }, { 10, 4 }, { 14, 9 } },
      { { 0, 5 }, { 1, 4 }, { 2, 14 }, { 4, 13 }, { 9, 3 }, { 12, 5 }, { 14, 12 } },
      { { 4, 6 }, { 7, 5 }, { 13, 7 }, { 11, 7 }, { 8, 3 } },
      { { 6, 8 }, { 12, 4 }, { 7, 4 } },
      { { 3, 7 }, { 9, 7 }, { 13, 19 } },
      { { 8, 5 }, { 7, 3 }, { 10, 4 }, { 14, 2 } },
      { { 11, 19 }, { 9, 7 }, { 14, 5 } },
      { { 8, 12 }, { 12, 2 }, { 7, 9 }, { 13, 5 } }
  };

  int[][][] sparse = {
      { { 1, 5 }, { 3, 6 } },
      { { 6, 11 }, { 0, 5 }, { 5, 9 } },
      { { 4, 3 }, { 6, 7 } },
      { { 0, 6 }, { 9, 10 }, { 7, 5 } },
      { { 2, 3 } },
      { { 1, 9 }, { 10, 4 } },
      { { 2, 7 }, { 1, 11 }, { 13, 12 }, { 8, 14 } },
      { { 3, 5 }, { 12, 15 }, { 11, 5 } },
      { { 9, 2 }, { 6, 14 } },
      { { 3, 10 }, { 8, 2 } },
      { { 5, 4 }, { 13, 9 } },
      { { 7, 5 } },
      { { 7, 15 }, { 14, 6 } },
      { { 6, 12 }, { 10, 9 }, { 14, 7 } },
      { { 12, 6 }, { 13, 7 } }
  };

  int[][][] negative = {
      { { 1, 5 }, { 2, 4 } },
      { { 0, 5 }, { 9, 7 } },
      { { 0, 4 }, { 3, 6 }, { 7, 11 }, { 4, -2 } },
      { { 2, 6 }, { 6, -7 } },
      { { 2, -2 }, { 5, -3 }, { 6, -9 } },
      { { 4, -3 }, { 7, 8 } },
      { { 3, -7 }, { 4, -9 }, { 7, -1 }, { 8, -4 } },
      { { 5, 8 }, { 2, 11 }, { 6, -1 }, { 10, 12 }, { 8, 14 } },
      { { 7, 14 }, { 6, -4 }, { 9, 1 } },
      { { 1, 7 }, { 8, 1 }, { 10, 5 } },
      { { 7, 12 }, { 9, 5 } }
  };

  List<ArrayList<ArrayList<Integer>>> denseGraph = new ArrayList<>();
  List<ArrayList<ArrayList<Integer>>> sparseGraph = new ArrayList<>();
  List<ArrayList<ArrayList<Integer>>> negativeGraph = new ArrayList<>();

  Graph() {
    int[][][][] graphs = { dense, sparse, negative };
    String[] type = { "DENSE", "SPARSE", "NEGATIVE" };
    int idx = 0;
    for (int[][][] links : graphs) {
      List<ArrayList<ArrayList<Integer>>> graph = new ArrayList<>();
      for (int i = 0; i < links.length; i++) {
        ArrayList<ArrayList<Integer>> edgesWeights = new ArrayList<>();
        for (int j = 0; j < links[i].length; j++) {
          ArrayList<Integer> config = new ArrayList<>();
          config.add(links[i][j][0]);
          config.add(links[i][j][1]);
          edgesWeights.add(config);
        }
        graph.add(edgesWeights);
      }
      switch (type[idx++]) {
        case "DENSE":
          denseGraph.addAll(graph);
          break;
        case "SPARSE":
          sparseGraph.addAll(graph);
          break;
        case "NEGATIVE":
          negativeGraph.addAll(graph);
          break;
      }
    }
  }

  static int minWt = 0;

  static String minPath = "";

  static void printTrace() {
    System.out
        .println("Total Weight : " + minWt + "\nMinimum Spanning Tree Trace: " + Arrays.asList(minPath.split("\n")));
    minWt = 0;
    minPath = "";
  }

  static void build(int U, int V) {
    minPath += "V" + U + " <-> V" + V + "\n";
  }

  static void updateWeight(int Wt) {
    minWt += Wt;
  }
}