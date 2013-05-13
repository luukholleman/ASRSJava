package asrsController;

import java.util.Random;

/**
 * BinPackingTask: startProcess() finishProcess() getBin(problemNumber, item)
 * calculateSpaceLeftInBin(problemNumber, bin) setBin(problemNumber, item, bin)
 * getItemSize(problemNumber, item) getBinSize() getNumberOfItems(problemNumber)
 * getNumberOfProblems()
 * 
 * @author Paul Veldhuijzen van Zanten, Ilja Clabbers
 */
public class BinPackingTask {
	/**
	 * NUMBEROFPROBLEMS number of problems to use
	 */
	final int NUMBEROFPROBLEMS = 20;
	/**
	 * MAXNUMBEROFITEMS each problem has maximized number of items
	 */
	final int MAXNUMBEROFITEMS = 15;
	/**
	 * MAXITEMSIZE each item has maximized size
	 */
	final int MAXITEMSIZE = 40;
	/**
	 * MAXTOTALSIZE all itemsizes sum up to this maximum
	 */
	final int MAXTOTALSIZE = 200;
	/**
	 * MAXNUMBEROFBINS max number of bins to be used for each problem
	 */
	final int MAXNUMBEROFBINS = 20;
	/**
	 * BINSIZE a bin has this maximum size to fill up with items
	 */
	final int BINSIZE = 60;

	private int[][] problems; // problem_number item_number (defines the
								// problems to be solved)
	private int[] numberOfItems; // problem_number (nr of items differs between
									// problems)
	private int[][] solutions; // problem_number item_number (stores in which
								// bin the item is stored)
	private long startTime; // starttijd voor algoritmevergelijking
	private long stopTime; // stoptijd voor algoritmevergelijking
	private boolean processRunning; // geeft aan of vergelijking gestart is
	private boolean debug; // moet er op console debug-informatie getoond
							// worden?

	/**
	 * Constructor for Task2, no debugging
	 * 
	 * @param seed
	 *            a seed to feed the randomizer
	 * @see Task2
	 */
	public BinPackingTask(long seed) {
		this(seed, false);
	}

	/**
	 * Constructor for Task2. This constructor fills the list of problems. A
	 * problem is a list of itemsizes. These are the product sizes that have to
	 * be used by the to be designed algorithm
	 * 
	 * @param seed
	 *            a seed to feed the randomizer
	 * @param debug
	 *            a boolean to decide whether or not to show debug info on the
	 *            console
	 */
	public BinPackingTask(long seed, boolean debug) {
		this.debug = debug;
		processRunning = false;

		problems = new int[NUMBEROFPROBLEMS][MAXNUMBEROFITEMS];
		numberOfItems = new int[NUMBEROFPROBLEMS];

		fillProblems(seed);
		// oplossingen allemaal op -1 gezet
		solutions = new int[NUMBEROFPROBLEMS][MAXNUMBEROFITEMS];
		for (int nop = 0; nop < NUMBEROFPROBLEMS; nop++) {
			for (int noi = 0; noi < MAXNUMBEROFITEMS; noi++) {
				solutions[nop][noi] = -1;
			}
		}
	}

	/**
	 * fillProblems is a helper function that creates the random items. All
	 * items in a problem may never sum up above MAXTOTALSIZE.
	 * 
	 * @param seed
	 *            a seed to feed the randomizer
	 */
	private void fillProblems(long seed) {
		Random generator = new Random(seed);
		for (int i = 0; i < NUMBEROFPROBLEMS; i++) {
			int total = 0;
			int j;
			for (j = 0; (j < MAXNUMBEROFITEMS) && (total <= MAXTOTALSIZE); j++) {
				int itemSize = generator.nextInt(MAXITEMSIZE) + 1;
				fillProblem(i, j, itemSize);
				total = total + itemSize;
			}
			numberOfItems[i] = j;
		}
	}

	/**
	 * fillProblem fills one specific problem with an item of specific size.
	 * 
	 * @param pr
	 *            the problem index
	 * @param item
	 *            the item index
	 * @param size
	 *            the specific size of the item
	 * @see Task2
	 */
	private void fillProblem(int pr, int item, int size) {
		problems[pr][item] = size;
		if (debug) {
			System.out.println("Fill problem " + pr + " with item " + item
					+ " (size " + size + ")");
		}
	}

	/**
	 * calculateNrOfBins is used in the doCalculation method to calculate the nr
	 * of used bins per problem
	 * 
	 * @param problemNumber
	 *            the problem index
	 * @return the number of used bins
	 */
	private int calculateNrOfBins(int problemNumber) {
		int maxbin = 0;
		int bin;

		// get highest bin number
		for (int i = 0; i < numberOfItems[problemNumber]
				&& solutions[problemNumber][i] != -1; i++) {
			bin = solutions[problemNumber][i] + 1;
			if (bin > maxbin) {
				maxbin = bin;
			}
		}

		return maxbin;
	}

	/**
	 * calculateSpaceLeft is used by the doCalculation method to get the sum of
	 * all space left in used bins.
	 * 
	 * @param problemNumber
	 *            the number of the problem
	 * @return int the amount of space left in the specific problem
	 */
	private int calculateSpaceLeft(int problemNumber) {
		int[] filling = new int[MAXNUMBEROFBINS];

		// clear filling
		for (int b = 0; b < MAXNUMBEROFBINS; b++) {
			filling[b] = 0;
		}

		// sum the item sizes of all bins
		int bin = 0;
		for (int i = 0; i < numberOfItems[problemNumber]
				&& solutions[problemNumber][i] != -1; i++) {
			bin = solutions[problemNumber][i];
			filling[bin] = filling[bin] + problems[problemNumber][i];
		}

		// compute total space left in all bins
		int empty_space = 0;
		int total_empty_space = 0;
		for (int b = 0; b < MAXNUMBEROFBINS; b++) {
			if (filling[b] > BINSIZE) {
				// always show as essential for tests
				System.out.println("Error: Bin " + b
						+ " is overpacked in problem " + problemNumber + "\n");
			}
			if (filling[b] > 0) {
				empty_space = BINSIZE - filling[b];
				total_empty_space = total_empty_space + empty_space;
			}
		}
		return total_empty_space;
	}

	/**
	 * doCalculation shows all the results:
	 * <p>
	 * number of bins needed over all problems, empty space in all used bins
	 * over all problems, time needed to succeed
	 */
	private void doCalculation() {
		int totalspaceleft = 0;
		int totalbins = 0;
		for (int problemNumber = 0; problemNumber < NUMBEROFPROBLEMS; problemNumber++) {
			totalbins = totalbins + calculateNrOfBins(problemNumber);
			totalspaceleft = totalspaceleft + calculateSpaceLeft(problemNumber);
		}
		System.out.println("Number of bins needed = " + totalbins);
		System.out.println("Space left in used bins = " + totalspaceleft);
		System.out.println("Calculation time = " + (stopTime - startTime));
	}

	/**
	 * startProcess starts the time and process. It has to be called before your
	 * algorithm.
	 * 
	 */
	public void startProcess() {
		if (!processRunning) {
			processRunning = true;
			startTime = System.currentTimeMillis();
		}
	}

	/**
	 * finishProcess stops the time and starts calculation of performance. It
	 * has to be called after your algorithm
	 * 
	 */
	public void finishProcess() {
		if (processRunning) {
			processRunning = false;
			stopTime = System.currentTimeMillis();
			doCalculation();
		}
	}

	/**
	 * getNumberOfProblems returns the number of problems
	 * 
	 * @return int number of problems
	 */
	public int getNumberOfProblems() {
		if (processRunning) {
			return NUMBEROFPROBLEMS;
		} else {
			return -1;
		}
	}

	/**
	 * getNumberOfItems returns the number of items for a specific problem
	 * 
	 * @return int number of items
	 */
	public int getNumberOfItems(int probl) {
		if (processRunning) {
			if (probl >= 0 && probl < NUMBEROFPROBLEMS) {
				return numberOfItems[probl];
			} else {
				if (debug)
					System.out
							.println("Array index out of bounds in getNumberOfItems");
				return -1;
			}
		} else {
			if (debug)
				System.out.println("Process not running");
			return -1;
		}
	}

	/**
	 * getBinSize returns the size of one bin
	 * 
	 * @return int size of bin
	 */
	public int getBinSize() {
		return BINSIZE;
	}

	/**
	 * getItemSize returns the size of an item in a problem
	 * 
	 * @param int problemNumber the number of the problem
	 * @param int item the index of the item in the problem
	 * @return int size of bin
	 */
	public int getItemSize(int problemNumber, int item) {
		return this.problems[problemNumber][item];
	}

	/**
	 * setBin is used to get an item from a problem and put it into a bin
	 * 
	 * @param problem_number
	 *            the problem to be tackled
	 * @param item
	 *            the item to put in a bin
	 * @param bin
	 *            the binnumber where to put it
	 */
	public void setBin(int problem_number, int item, int bin) {
		if (processRunning) {
			if (problem_number < 0 || problem_number >= NUMBEROFPROBLEMS) {
				if (debug)
					System.out
							.println("Problem number out of bounds in setBin");
			} else {
				if (item < 0 || item >= numberOfItems[problem_number]) {
					if (debug)
						System.out
								.println("Item number out of bounds in setBin");
				} else {
					if (bin < 0 || bin > MAXNUMBEROFBINS) {
						if (debug)
							System.out
									.println("Bin number out of bounds in setBin");
					} else {
						if (debug) {
							System.out.println("Problem " + problem_number
									+ ": Put item " + item + " with size "
									+ problems[problem_number][item]
									+ " in bin " + bin);
						}
						solutions[problem_number][item] = bin;
					}
				}
			}
		} else {
			System.out.println("Process is not running");
		}
	}

	/**
	 * calculateSpaceLeftInBin can be used to determine how many space there
	 * still is in a bin
	 * 
	 * @param problemNumber
	 *            the number of the problem
	 * @param bin
	 *            the number of the bin
	 * @return int the space left in the specific bin with the specific problem
	 */
	public int calculateSpaceLeftInBin(int problemNumber, int bin) {
		int binSize = 0;
		for (int noi = 0; noi < MAXNUMBEROFITEMS; noi++) {
			if (solutions[problemNumber][noi] == bin) {
				binSize += problems[problemNumber][noi];
			}
		}
		return BINSIZE - binSize;
	}

	/**
	 * getBin can be used to determine in what bin an item has been put
	 * 
	 * @param problemNumber
	 *            the number of the problem
	 * @param item
	 *            the number of the item
	 * @return int the bin in which an item is put
	 */
	public int getBin(int problemNumber, int item) {
		return solutions[problemNumber][item];
	}
}