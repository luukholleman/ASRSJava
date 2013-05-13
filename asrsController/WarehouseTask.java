package asrsController;

import java.util.Random;

/**
 * startProcess() finishProcess() getNumberOfProblems()
 * getNumberOfItems(problemNumber) int getCoordHorDigit(int probl, int item) int
 * getCoordVertDigit(int probl, int item) char getCoordHorLetter(int probl, int
 * item) char getCoordVertLetter(int probl, int item) void setOrder(int problem,
 * int item, int robot, int sequence)
 * 
 * 
 * @author P60181745
 * 
 */
public class WarehouseTask {

	final int numberOfProblems = 20;
	final int maxNumberOfItems = 40;

	final int maxX = 20;
	final int maxY = 10;

	int problem[][][]; // problem item coordinate
	int numberOfItems[];
	int errorCount;

	int solution[][][]; // problem robot item

	long startTime;
	long stopTime;
	boolean processRunning;

	public WarehouseTask(long seed) {
		processRunning = false;
		errorCount = 0;

		problem = new int[numberOfProblems][maxNumberOfItems][2];
		numberOfItems = new int[numberOfProblems];

		fillProblem(seed);

		solution = new int[numberOfProblems][2][maxNumberOfItems];
		for (int nop = 0; nop < numberOfProblems; nop++)
			for (int rob = 0; rob < 2; rob++)
				for (int noi = 0; noi < maxNumberOfItems; noi++)
					solution[nop][rob][noi] = -1;
	}

	protected boolean problemOkay(int problemNumber) {
		boolean result = true;
		int noi = numberOfItems[problemNumber];
		boolean check[] = new boolean[noi];
		for (int i = 0; i < noi; i++)
			check[i] = false;
		for (int rob = 0; rob < 2; rob++) {
			for (int i = 0; i < maxNumberOfItems; i++) {
				int item = solution[problemNumber][rob][i];
				if (item == -1)
					break;
				if (check[item]) {
					System.out.print("Item " + item
							+ " occurs more than once in problem "
							+ problemNumber + "\n");
					result = false;
				} else {
					check[item] = true;
				}
			}
		}
		for (int i = 0; i < noi; i++) {
			if (!check[i]) {
				System.out.print("Item " + i + " missing in problem number "
						+ problemNumber + "\n");
				result = false;
			}
		}
		return result;
	}

	protected double calculateSinglePath(int problemNumber, int robot) {
		double length;
		int x1, y1, x2, y2;
		length = 0;
		if (robot == 0) {
			x1 = -1;
			y1 = 0;
		} else {
			x1 = maxX;
			y1 = 0;
		}
		for (int i = 0; i < numberOfItems[problemNumber]
				&& solution[problemNumber][robot][i] != -1; i++) {
			int item = solution[problemNumber][robot][i];
			x2 = problem[problemNumber][item][0];
			y2 = problem[problemNumber][item][1];
			length = length
					+ Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
			x1 = x2;
			y1 = y2;
		}
		if (robot == 0) {
			x2 = -1;
			y2 = 0;
		} else {
			x2 = maxX;
			y2 = 0;
		}
		length = length
				+ Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return length;
	}

	protected void doCalculation() {
		double total = 0.0;
		for (int problemNumber = 0; problemNumber < numberOfProblems; problemNumber++) {
			total = total + calculateSinglePath(problemNumber, 0);
			total = total + calculateSinglePath(problemNumber, 1);
		}
		System.out.println("Length of total path = " + total);
		System.out.println("Calculation time = " + (stopTime - startTime));
	}

	protected void calculateResult() {
		if (errorCount > 0) {
			System.out.println("Number of errors = " + errorCount
					+ ", no calculation of result.");
		}
		boolean resultOkay = true;
		for (int problemNumber = 0; problemNumber < numberOfProblems; problemNumber++) {
			if (!problemOkay(problemNumber))
				resultOkay = false;
		}
		if (resultOkay) {
			doCalculation();
		} else {
			System.out.println("Error(s) found, no result calculated");
		}
	}

	protected void fillProblem(int pr, int item, int x, int y) {
		problem[pr][item][0] = x;
		problem[pr][item][1] = y;
	}

	protected boolean notUnique(int pr, int nextItem, int x, int y) {
		boolean result = false;
		for (int i = 0; i < nextItem; i++) {
			if (problem[pr][i][0] == x && problem[pr][i][1] == y)
				result = true;
		}
		return result;
	}

	protected void fillProblem(long seed) {
		Random generator = new Random(seed);
		for (int i = 0; i < numberOfProblems; i++) {
			int noOfItems = generator.nextInt(maxNumberOfItems) + 1;
			int x, y;
			numberOfItems[i] = noOfItems;
			for (int j = 0; j < noOfItems; j++) {
				do {
					x = generator.nextInt(maxX);
					y = generator.nextInt(maxY);
				} while (notUnique(i, j, x, y));
				fillProblem(i, j, x, y);
			}
		}
	}

	public void startProcess() {
		if (!processRunning) {
			processRunning = true;
			startTime = System.currentTimeMillis();
		}
	}

	public void finishProcess() {
		if (processRunning) {
			processRunning = false;
			stopTime = System.currentTimeMillis();
			calculateResult();
		}
	}

	public int getNumberOfProblems() {
		if (processRunning)
			return numberOfProblems;
		else
			return -1;
	}

	public int getNumberOfItems(int probl) {
		if (processRunning) {
			if (probl >= 0 && probl < numberOfProblems)
				return numberOfItems[probl];
			else {
				errorCount++;
				System.out
						.println("Array index out of bounds in getNumberOfItems");
				return -1;
			}
		} else
			return -1;
	}

	public int getCoordHorDigit(int probl, int item) {
		if (processRunning)
			return problem[probl][item][0];
		else
			return -1;
	}

	public int getCoordVertDigit(int probl, int item) {
		if (processRunning)
			return problem[probl][item][1];
		else
			return -1;
	}

	protected char int2char(int i) {
		return (char) (i + (int) 'A');
	}

	public char getCoordHorLetter(int probl, int item) {
		if (processRunning)
			return int2char(getCoordHorDigit(probl, item));
		else
			return '@';
	}

	public char getCoordVertLetter(int probl, int item) {
		if (processRunning)
			return int2char(getCoordVertDigit(probl, item));
		else
			return '@';
	}

	public void setOrder(int problem, int item, int robot, int sequence) {
		if (processRunning) {
			if (problem < 0 || problem >= numberOfProblems) {
				System.out.println("problem number out of bounds in setOrder");
				errorCount++;
			} else {
				if (item < 0 || item >= numberOfItems[problem]) {
					System.out.println("item number out of bounds in setOrder");
					errorCount++;
				} else {
					if (robot != 0 && robot != 1) {
						System.out
								.println("robot number out of bounds in setOrder");
						errorCount++;
					} else {
						if (sequence < 0 || sequence >= numberOfItems[problem]) {
							System.out
									.println("sequence number out of bounds in setOrder");
							errorCount++;
						} else {
							solution[problem][robot][sequence] = item;
						}
					}
				}
			}
		} else {
			System.out.println("Warning: process already stopped");
		}
	}

}
