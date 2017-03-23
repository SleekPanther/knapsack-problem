import java.util.*;

public class Knapsack {
	//Change numberOfItems, values & weights to run with different items
	private final int numberOfItems = 5;	//used to create arrays of correct size
	private int[] values;
	private int[] weights;
	private int[][] memoizationTable;
	private boolean[][] chosenItems;		//used for backtracking to see what items were included

	public static void main(String[] args) {
		Knapsack knapsackSolver = new Knapsack();
		knapsackSolver.solveKnapsack(11);
		System.out.println("\n\n");
		knapsackSolver.solveKnapsack(10);
	}


	public Knapsack(){	//constructor creates items & populates arrays for values[] & weights[]
		//add dummy item 0 in both arrays so arrays are indexed from 1
		int[] tempValues = {0, 1, 6, 18, 22, 28};	//values is already declared so make a temporary array with array shorthand
		values=tempValues;		//set values to the reference of the temp array

		int[] tempWeights = {0, 1, 2, 5, 6, 7};
		weights=tempWeights;
	}

	//check if arrays from constructor are the correct length
	public boolean verifyCorrectArrayLength(int[] array){
		return array.length == (numberOfItems+1);	//+1 since arrays are indexed from 1
	}

	public void solveKnapsack(final int knapsackWeightCapacity){		//final so it can't accidentally be changed
		memoizationTable = new int[numberOfItems+1][knapsackWeightCapacity+1];		//arrays are indexed from 1
		chosenItems = new boolean[numberOfItems+1][knapsackWeightCapacity+1];

		fill2DArray(memoizationTable, -1);		//set all values in table to -1

		//Initial step in algorithm: fill 0th row with 0's
		for(int i=0; i<memoizationTable[0].length ; i++){	//start @ 0, but [0][0] is ignored when printing
			memoizationTable[0][i]=0;
		}
		
		System.out.println("Solving knapsack weight capacity "+knapsackWeightCapacity+", with "+numberOfItems+" items"     +"\n-----------------------------------------------------------------------------------------------------");
		printTable(memoizationTable, 0);		//Print table for row 0

		for(int i=1; i<=numberOfItems; i++){		//start @ row 1
			for(int currentCapacity=0; currentCapacity<=knapsackWeightCapacity; currentCapacity++){	// <=
				if(weights[i] > currentCapacity){		//item is too heavy to fit
					memoizationTable[i][currentCapacity] = memoizationTable[i-1][currentCapacity];
				}
				//If 2nd argument in max() is greater than 1st: max(M[i-1, w], v[i]+M[i-1][W-w[i] ])
				else if( (values[i] + memoizationTable[i-1][currentCapacity-weights[i]]) > (memoizationTable[i-1][currentCapacity]) ){
					memoizationTable[i][currentCapacity] = values[i] + memoizationTable[i-1][currentCapacity-weights[i]];
					chosenItems[i][currentCapacity]=true;		//save item to keep table
				}
				else{	//if 1st argument of max() is greater
					memoizationTable[i][currentCapacity] = memoizationTable[i-1][currentCapacity];
				}
			}
			System.out.println();
			printTable(memoizationTable, i);
		}
		System.out.println("\nKnapsack with weight capacity "+knapsackWeightCapacity+" has optimal value:  "+memoizationTable[numberOfItems][knapsackWeightCapacity]);

		System.out.println();
		findOptimalKnapsackContents(knapsackWeightCapacity, memoizationTable, chosenItems);		//see what items were actually added
	}

	//Fill 2d Array with a value (passed by reference)
	public static void fill2DArray(int[][] matrix, int fillValue){
		for(int i=0; i<matrix.length ; i++){
			for(int j=0; j<matrix[i].length ; j++){
				matrix[i][j]= fillValue;
			}
		}
	}

	//String representation of an item, easier to print an item by just giving it's index
	//No array out of bounds checking
	private String getItemAsString(int itemIndex){
		return "Item "+itemIndex+" (Value="+values[itemIndex]+", Weight="+weights[itemIndex]+")";
	}

	private void printTable(int[][] memoTable, int lastRowCompleted){
		System.out.println("Memoization table, Row "+lastRowCompleted+" completed \t(view with fixed-width font for columns to line up)");
		int rows=memoTable.length;
		int columns=memoTable[0].length;

		String[] rowSetHeaders = new String[rows];		//create labels for y axis for items: {}, {1}, {1, 2}, {1, 2, 3}, {1, 2, 3, 4} ...
		rowSetHeaders[0]="";
		rowSetHeaders[1]="1";
		for(int i=2; i<rows; i++){
			rowSetHeaders[i] = rowSetHeaders[i-1]+ ", "+i;	//copy previous value & append new item to be considered
		}
		int widestRowSetHeader = findWidestElementInList(rowSetHeaders)+5;		//We add "{" and "}" on either side when printing, but it needs to be @ least +4 or the columns won't line up

		int widestNumber = findWidestNumberInTable(memoTable) + 3;	//Find number with most digits in table. All numbers are padded with @ least this many spaces to the left. Then add 3 to give more breathing room

		System.out.println(leftPad("Weights -->", widestRowSetHeader*4));		//"Center" this label by giving a bunch of left padding
		System.out.print(leftPad("", widestRowSetHeader));	//print empty spaces for formatted table output in upper left corner. Used widestRowSetHeader since must align with 0th column
		for(int j=0; j<columns; j++){		//Print weights from 0 up to knapsackWeightCapacity
			System.out.print("\t"+ leftPad(j, widestNumber));
		}
		System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------");

		//the actual memoization table
		for(int i=0; i< rows; i++){
			System.out.print( leftPad(("{" + rowSetHeaders[i] +"}"), widestRowSetHeader) );		//print row header: how many items were considered @ that row
			for(int j=0; j<columns; j++){		//print the value from the memoization table
				System.out.print("\t"+ leftPad(memoTable[i][j], widestNumber) );	//leftPad() adds spaces, also put tab separator BETWEEN each number (but not on the lat one in row)
			}
			System.out.println();
		}
		System.out.println();
	}

	//Loop through an entire 2d array & find the widest number (most digits)
	public static int findWidestNumberInTable(int[][] table){
		int widestNumber=1;		//assume every value is @ least 1 digit
		for(int[] row: table){
			for(int number: row){
				int numberWidth = ("" + number ).length();		//convert int to string & get string length
				widestNumber = Math.max(widestNumber, numberWidth);		//pick the larger
			}
		}
		return widestNumber;
	}

	public static <Type> int findWidestElementInList(Type[] array){
		int widestElement=0;	//Don't assume @ least 1 character or digit, could be array of empty strings
		for(Type element: array){
			int elementWidth = ("" + element ).length();		//convert to string & get string length
			widestElement = Math.max(widestElement, elementWidth);		//pick the larger
		}
		return widestElement;
	}

	//Return a string with padding spaces if the input is not as wide as the desired width. Works for number & strings (including empty string "")
	public static <Type> String leftPad(Type input, int desiredWidth){
		String paddedInput = input + "";		//convert to string.	Works even for empty string
		if(paddedInput.length() < desiredWidth){
			int paddingRequired = desiredWidth - paddedInput.length();	//loop adds an extra space to the left until it reaches the desired width
			for(int i=0; i<paddingRequired; i++){
				paddedInput = " " + paddedInput;		//add a space to the left
			}
		}
		return paddedInput;
	}

	//Must be called AFTER solveKnapsack()
	private void findOptimalKnapsackContents(final int knapsackWeightCapacity, final int[][] memoizationTable, final boolean[][] chosenItems){
		System.out.println("_____Knapsack Contains_____");
		
		int currentCapacity=knapsackWeightCapacity;	//start from bottom right corner of the matrix
		for(int i=numberOfItems; i>=1; i--){
			if(chosenItems[i][currentCapacity]){	//if it was included
				System.out.println(getItemAsString(i));
				currentCapacity -= weights[i];	//subtract the item's weight for next iteration since we've included an item
			}
		}
	}

	//mostly for testing purposes
	private void printChosenItems(boolean[][] chosenItemsTable){
		System.out.println("Chosen items table");
		int rows=chosenItemsTable.length;
		for(int i=0; i< rows; i++){
			System.out.println(Arrays.toString(chosenItemsTable[i]));
		}
		System.out.println();
	}
}