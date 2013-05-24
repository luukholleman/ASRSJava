package tspAlgorithm;
public class Test
{
  public static void main (String[] args)
  {
    allPermutations (8);

    System.out.println("Done");
  }

  public static int[][] allPermutations (int N)
  {
    // base case
    if (N == 2)
    {
      return new int[][] {{1, 2}, {2, 1}};
    }
    else if (N > 2)
    {
      // start with all permutations of previous degree
      int[][] permutations = allPermutations (N - 1);

      for (int i = 0; i < factorial (N); i += N)
      {
        // copy each permutation N - 1 times
        for (int j = 0; j < N - 1; ++j)
        {
          // similar to javascript's array.splice
          permutations = insertRow (permutations, i, permutations [i]);
        }
      }

      // "weave" next number in
      for (int i = 0, j = N - 1, d = -1; i < permutations.length; ++i)
      {
        // insert number N at index j
        // similar to javascript's array.splice
        permutations = insertColumn (permutations, i, j, N);

        // index j is  N-1, N-2, N-3, ... , 1, 0; then 0, 1, 2, ... N-1; then N-1, N-2, etc.
        j += d;

        // at beginning or end of the row, switch weave direction
        if (j < 0 || j > N - 1)
        {
          d *= -1;
          j += d;
        }
      }
      
      return permutations;
    }
    else
    {
      throw new IllegalArgumentException ("N must be >= 2");
    }
  }

  private static void arrayDeepCopy (int[][] src, int srcRow, int[][] dest,
                                     int destRow, int numOfRows)
  {
    for (int row = 0; row < numOfRows; ++row)
    {
      System.arraycopy (src [srcRow + row], 0, dest [destRow + row], 0,
                        src[row].length);
    }
  }

  public static int factorial (int n)
  {
    return n == 1 ? 1 : n * factorial (n - 1);
  }

  private static int[][] insertColumn (int[][] src, int rowIndex,
                                       int columnIndex, int columnValue)
  {
    int[][] dest = new int[src.length][0];

    for (int i = 0; i < dest.length; ++i)
    {
      dest [i] = new int [src[i].length];
    }

    arrayDeepCopy (src, 0, dest, 0, src.length);

    int numOfColumns = src[rowIndex].length;

    int[] rowWithExtraColumn = new int [numOfColumns + 1];

    System.arraycopy (src [rowIndex], 0, rowWithExtraColumn, 0, columnIndex);

    System.arraycopy (src [rowIndex], columnIndex, rowWithExtraColumn,
                      columnIndex + 1, numOfColumns - columnIndex);

    rowWithExtraColumn [columnIndex] = columnValue;

    dest [rowIndex] = rowWithExtraColumn;

    return dest;
  }

  private static int[][] insertRow (int[][] src, int rowIndex,
                                    int[] rowElements)
  {
    int srcRows = src.length;
    int srcCols = rowElements.length;

    int[][] dest = new int [srcRows + 1][srcCols];

    arrayDeepCopy (src, 0, dest, 0, rowIndex);
    arrayDeepCopy (src, rowIndex, dest, rowIndex + 1, src.length - rowIndex);

    System.arraycopy (rowElements, 0, dest [rowIndex], 0, rowElements.length);

    return dest;
  }

  public static void printArray (int[][] array)
  {
    for (int row = 0; row < array.length; ++row)
    {
      for (int col = 0; col < array[row].length; ++col)
      {
        System.out.print (array [row][col] + " ");
      }

      System.out.print ("\n");
    }

    System.out.print ("\n");
  }
}