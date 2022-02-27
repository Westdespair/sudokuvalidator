package group8.idata2305.sudokuvalidator;

/**
 * Validates 9x9 sudoku solutions.
 */
public class SudokuValidator {
    private int[][] sudokuArray;
    private final int sudokuSize = 9;

    public SudokuValidator(){
        sudokuArray = new int[sudokuSize][sudokuSize];
    }

    /**
     * Empties the array.
     */
    public void resetSudokuArray() {
        sudokuArray = new int[sudokuSize][sudokuSize];
    }

    public boolean isSudokuArrayValid() {
        boolean isValid = false;
        for (int i = 0; i < 9; i++) {
            if(isRowValid(i) && isBoxValid(i) && isColumnValid(i)) {
                isValid = true;
            }
        }
        return isValid;
    }

    /**
     *
     * @param rowNum the rownumber which will be edited.
     * @param numString A comma separated string
     */
    public void insertRowFromCommaString( String numString, int rowNum) {
        insertRowOnSudokuArray(rowStringToArray(numString,","), rowNum);
    }

    /**
     * Prints out the sudoku game for easier viewing. Each row is its own line, and each column is separated by double spaces.
     */
    public void printRows() {
        for (int rowNum = 0; rowNum < sudokuSize; rowNum++) {
            StringBuilder rowString = new StringBuilder();
            for (int colNum = 0; colNum < sudokuSize; colNum++) {
                int currentNum = sudokuArray[rowNum][colNum];
                rowString.append(currentNum).append("  ");
            }
            System.out.println(rowString);
        }
    }

    /**
     * Inserts the given array of integers in the given row.
     * @param rowArray
     * @param rowNum
     */
    public void insertRowOnSudokuArray(int[] rowArray, int rowNum) {
        for(int i = 0; i < rowArray.length; i++) {
            sudokuArray[rowNum][i] = rowArray[i];
        }
    }

    /**
     * TODO: Separator is currently hardcoded as comma ",", this is not ideal if files have differing separators.
     * @param rowString A string consisting of a sudoku row, separated by the separator variable, usually ",".
     * @param separator A separator string, the string which separates the numbers in the row.
     */
    public int[] rowStringToArray(String rowString, String separator) {
        int[] rowArray = new int[sudokuSize];

        //Cleans the strings so that only numbers and commas are left. Uses the commas to separate the numbers into a list.
        for(int i = 0; i < sudokuSize; i++) {
            int rowInt = Integer.parseInt(rowString.split(separator)[i].replaceAll("[^0-9,]", ""));
            rowArray[i] = rowInt;
        }
        return rowArray;
    }

    /**
     * Traverse a list of numbers, and check if any of the numbers are below or over the valid numbers for the puzzle.
     * For example, in a 9x9 sudoku, the numbers have to be from 1 to 9 inclusive.
     * @param list A list of integers, such as a converted column, box, or row.
     * @return whether the list contains valid numbers or not.
     */
    public boolean intListIsContainsValidIntegers(int[] list) {
        boolean isValid = true;
        for (int j : list) {
            if (j < 1 || j > sudokuSize) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Checks if a row contains only a single of each value within the size of the sudoku scope.
     * @param rowNum the row of the sudoku puzzle, horizontal segments starting from the top at 0.
     * @return true if the row is valid, false if the row is invalid.
     */
    public boolean isRowValid(int rowNum) {
        int[] rowList = convertRowToIntList(rowNum);
        boolean isValid = false;
        if(intListIsContainsValidIntegers(rowList) && intListContainsNoDuplicateValues(rowList)) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Checks if a given box is valid, only containing valid numbers without any duplicates.
     * TODO: Used some nasty magic numbers to make box traversal possible, I'm sure could be more elegant.
     * @param boxNum
     * @return
     */
    private boolean isBoxValid(int boxNum){
        boolean isValid = false;
        int[] boxList = convertBoxToIntList(boxNum % 2,(int) ((boxNum+0.1)/3.1));
        // ^Logic that makes traversing the boxes change from this:
        // 00, 01, 02
        // 10, 11, 12
        // 20, 21, 22
        //
        // to this:
        // 0, 1, 2
        // 3, 4, 5
        // 6, 7, 8

        if(intListContainsNoDuplicateValues(boxList) && intListIsContainsValidIntegers(boxList)) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Checks if a given column contains only one of each value, and all values are within the range.
     * TODO: Ideally, the three validity checks column, box, row could be in one switch case.
     * @param columnNum
     * @return
     */
    private boolean isColumnValid(int columnNum) {
        boolean isValid = false;
        int[] columnList = convertColumnToIntList(columnNum);

        if(intListContainsNoDuplicateValues(columnList) && intListIsContainsValidIntegers(columnList)) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Converts a given row to a list of integers.
     * @param rowNum
     * @return
     */
    public int[] convertRowToIntList(int rowNum) {
        int[] rowList = new int[sudokuSize];

        for (int rowPosition = 0; rowPosition < sudokuSize; rowPosition++) {
            rowList[rowPosition] = sudokuArray[rowNum][rowPosition];
        }
        return rowList;
    }

    /**
     * Converts a given column to a list of integers.
     * @param columnNum
     * @return
     */
    public int[] convertColumnToIntList(int columnNum) {
        int[] columnList = new int[sudokuSize];

        for (int columnPosition = 0; columnPosition < sudokuSize; columnPosition++) {
            columnList[columnPosition] = sudokuArray[columnPosition][columnNum];
        }
        return columnList;
    }

    /**
     * Converts the contents of a given 3x3 box to an int array.
     * Example of a box:
     * 1  2  3
     * 4  5  6
     * 7  8  9
     *
     * Sudoku has 3x3 boxes like the one above, so the coordinates will look like this:
     * 0,0  0,1  0,2
     * 1,0  1,1  1,2
     * 2,0  2,1  2,2
     * TODO: Currently only working for a 9x9 sudokusize, potential for improvement here but it is beyond the assignment scope.
     * @param horizontalPos int the horizontal position of the box.
     * @param verticalPos int the vertical position of the box.
     * @return a list of integers containing the contents of a 3x3 box.
     */
    public int[] convertBoxToIntList(int horizontalPos, int verticalPos){
        int[] boxList = new int[sudokuSize];
        int boxListIndex = 0;
        int pOffsetCoef = 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boxList[boxListIndex] = sudokuArray[verticalPos * pOffsetCoef + i][horizontalPos * pOffsetCoef + j];
                boxListIndex++;
            }
        }
        return boxList;
    }

    /**
     * Compares every value of a list to every other value of the list. Returns false if a duplicate is found.
     * @param list A list of integers to check for duplicate values
     * @return True if the list has no duplicates, false if the list contains duplicates.
     */
    public boolean intListContainsNoDuplicateValues(int[] list) {
        boolean noDuplicates = true;
        for (int baseValue = 0; baseValue < sudokuSize; baseValue++) {
            for (int compareValue = 0; compareValue < sudokuSize; compareValue++) {
                if(list[baseValue] == list[compareValue] && baseValue != compareValue) {
                    noDuplicates = false;
                }
            }
        }
        return noDuplicates;
    }

    /**
     * Fills the array with an invalid sudoku solution.
     */
    public void fillSudokuArrayWithInvalidTestData() {
        resetSudokuArray();
        for (int rowNum = 0; rowNum < sudokuSize; rowNum++) {
            int fillNum = 1;
            for (int colNum = 0; colNum < sudokuSize; colNum++) {
                sudokuArray[rowNum][colNum] = fillNum;
                fillNum++;
            }
        }
    }

    /**
     * Fills the array with a valid sudoku solution.
     */
    public void fillSudokuArrayWithValidTestData() {
        resetSudokuArray();
        insertRowFromCommaString("6,3,9,5,7,4,1,8,2",0);
        insertRowFromCommaString("5,4,1,8,2,9,3,7,6",1);
        insertRowFromCommaString("7,8,2,6,1,3,9,5,4",2);

        insertRowFromCommaString("1,9,8,4,6,7,5,2,3",3);
        insertRowFromCommaString("3,6,5,9,8,2,4,1,7",4);
        insertRowFromCommaString("4,2,7,1,3,5,8,6,9",5);

        insertRowFromCommaString("9,5,6,7,4,8,2,3,1",6);
        insertRowFromCommaString("8,1,3,2,9,6,7,4,5",7);
        insertRowFromCommaString("2,7,4,2,5,1,6,9,8",8);

    }

    /**
     * Fills the array with an illegal sudoku solution, such as invalid integers like 10 or 0.
     */
    public void fillSudokuArrayWithIllegalTestData() {
        insertRowFromCommaString("11,3,9,5,15,4,1,8,2",0);
        insertRowFromCommaString("5,4,1,8,2,0,3,7,6",1);
        insertRowFromCommaString("7,8,2,6,1,3,9,5,4",2);

        insertRowFromCommaString("1,9,8,0,6,7,5,2,3",3);
        insertRowFromCommaString("3,6,5,9,8,0,4,1,7",4);
        insertRowFromCommaString("4,2,7,1,3,5,8,6,9",5);

        insertRowFromCommaString("9,5,6,0,4,8,2,3,1",6);
        insertRowFromCommaString("8,1,3,2,9,6,7,23,5",7);
        insertRowFromCommaString("2,7,4,2,5,0,6,9,8",8);

    }
}
