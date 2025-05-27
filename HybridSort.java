import java.util.Arrays;

class Sorters {
  static Integer[] Sorted;

  Sorters(Integer[] array) {
    Sorted = new Integer[array.length];
  }

  public Integer[] InsertionSort(Integer[] array) {
    int j;
    int temp;
    int idx = 0;
    for (int i = 1; i < array.length; i++) {
      temp = array[i];
      j = i - 1;
      while (j >= 0 && array[j] > temp)
        array[j + 1] = array[j--];
      array[j + 1] = temp;
    }
    for (Integer number : array)
      Sorted[idx++] = number;
    return Sorted;
  }

  static void merge(Integer[] array, int left, int mid, int right) {
    int i = left;
    int j = (mid + 1);
    int k = left;
    while (i <= mid && j <= right) {
      if (array[i] < array[j])
        Sorted[k++] = array[i++];
      else
        Sorted[k++] = array[j++];
    }
    for (; i <= mid; i++)
      Sorted[k++] = array[i];
    for (; j <= right; j++)
      Sorted[k++] = array[j];
    for (k = left; k <= right; k++)
      array[k] = Sorted[k];
  }

  public Integer[] MergeSort(Integer[] array, int left, int right) {
    if (left < right) {
      int mid = (left + right) / 2;
      MergeSort(array, left, mid);
      MergeSort(array, mid + 1, right);
      merge(array, left, mid, right);
    }
    return Sorted;
  }

}

public class HybridSort {
  public Integer[] sort(Integer[] array, Integer thresholdValue, Sorters sort) {
    Integer SIZE = array.length;
    System.out.println("Len of array : N = " + SIZE + ", K = " + thresholdValue + " "
        + (SIZE <= thresholdValue ? "sort.InsertionSort(array)" : "sort.MergeSort(array, 0, SIZE - 1)"));
    return SIZE <= thresholdValue ? sort.InsertionSort(array) : sort.MergeSort(array, 0, SIZE - 1);
  }

  public static void main(String[] args) {
    HybridSort hs = new HybridSort();

    Integer[][] lists = { { 5, 4, 4, 5, 47, 5, 4, 8, 4, 6, 4, 4, 5, 8 },
        { 64, 654, 684, 351, 6541, 21, 643, 216, 54, 321,
            65 },
        { 8, 5, 4, 2, 6, 12, 6, 2, 6, 1, 6, 1, 6, 2, 5, 0,
            45, 2, 5, 5 },
        { 9, 54, 74, 2, 14, 5, 4, 4, 0, 5, 8015, 05, 5 }, { 64, 58, 4, 56, 46, 13, 5, 4, 3, 58, 7, 4 } };
    Integer[] kValConfiguration = { lists[0].length / 2,
        lists[1].length * 2, lists[2].length * 2,
        lists[3].length / 3, lists[4].length * 2 };
    Integer cnf = 0;
    for (Integer[] list : lists) {
      Sorters sort = new Sorters(list);
      Integer THRESHOLD = kValConfiguration[cnf++];
      System.out.println(Arrays.toString(hs.sort(list, THRESHOLD, sort)));
    }
  }
}
