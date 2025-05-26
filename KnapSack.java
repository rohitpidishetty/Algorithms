import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class TopDownApproachHelper {
  int[][] generateMemoizationTable(int row, int col) {
    int[][] MT = new int[row + 1][col + 1];
    int INITIALIZE = -1;
    for (int i = 0; i <= row; i++) {
      for (int j = 0; j <= col; j++)
        MT[i][j] = INITIALIZE;
    }
    return MT;
  }
  int runKnapSack(int[][] MT, int capacity, int[] weights, int V, int[] values) {
    if (V == 0 || capacity == 0)
      return 0;
    if (MT[V][capacity] != -1)
      return MT[V][capacity];
    if (weights[V - 1] > capacity)
      MT[V][capacity] = runKnapSack(MT, capacity, weights, V - 1, values);
    else {
      int include = values[V - 1] + runKnapSack(MT, capacity - weights[V - 1], weights, V - 1, values);
      int skip = runKnapSack(MT, capacity, weights, V - 1, values);
      MT[V][capacity] = include >= skip ? include : skip;
    }
    return MT[V][capacity];
  }
}
class BottomUpApproachHelper {
  static class collection {
    int value = 0;
    int weight = 0;
    collection(int weight, int value) {
      this.weight = weight;
      this.value = value;
    }
  }
}
public class KnapSack {
  int TopDownApproach(int[] values, int[] weights, int capacity) {
    int v = values.length;
    TopDownApproachHelper tdah = new TopDownApproachHelper();
    int[][] memoTable = tdah.generateMemoizationTable(v, capacity);
    return tdah.runKnapSack(memoTable, capacity, weights, v, values);
  }
  int BottomUpApproach(int capacity, int[] weights, int[] values) {
    HashMap<BottomUpApproachHelper.collection, ArrayList<Integer>> matrix = new HashMap<>();
    Stack<BottomUpApproachHelper.collection> stack = new Stack<>();
    for (int i = 0; i < weights.length; i++)
      matrix.put(new BottomUpApproachHelper.collection(weights[i], values[i]), new ArrayList<Integer>());
    matrix.forEach((e, v) -> {
      for (int cap = 0; cap <= capacity; cap++)
        v.add((e.weight <= cap)
            ? (Math.max((stack.size() > 0 ? matrix.get(stack.peek()).get(cap) : 0),
                e.value + (stack.size() > 0 ? matrix.get(stack.peek()).get(cap - e.weight) : 0)))
            : (stack.size() > 0 ? matrix.get(stack.peek()).get(cap) : 0));
      stack.add(e);
    });
    ArrayList<ArrayList<Integer>> L = new ArrayList<>();
    matrix.forEach((e, v) -> {
      ArrayList<Integer> list = new ArrayList<>();
      for (int j = 1; j < matrix.get(e).size(); j++)
        list.add(matrix.get(e).get(j));
      L.add(list);
    });
    stack.clear();
    matrix.clear();
    return L.get(L.size() - 1).get(L.get(L.size() - 1).size() - 1);
  }
  public static void main(String[] args) {
    KnapSack ks = new KnapSack();
    int[] Values = { 20, 30, 10, 50 }; // n
    int[] Weights = { 1, 3, 4, 6 }; // (Highest weight) < Capacity(W)
    int Capacity = 10; // W
    int MAXbyTD = ks.TopDownApproach(Values, Weights, Capacity);
    System.out.println(MAXbyTD);
    int MAXbyBU = ks.BottomUpApproach(Capacity, Weights, Values);
    System.out.println(MAXbyBU);
  }
}