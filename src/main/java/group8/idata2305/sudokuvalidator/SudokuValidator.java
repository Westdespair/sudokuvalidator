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
     *
     * @param rowNumber The number of the row to check, starting from 0 and from the top row.
     * @return Valid if the row only contains one of each number 1 to 9, and no invalid data such as 0.
     */
    public boolean isValidRow(int rowNumber) {
        int rowSum = 0;
        boolean isValid = true;

        //Compares every number on the row to every other number on the row.
        for (int i = 0; i < sudokuSize; i++) {
            int currentNum = sudokuArray[rowNumber][i];

            for (int j = 0; j < sudokuSize; j++) {
                int compareNum = sudokuArray[rowNumber][j];
                if (currentNum == compareNum && j != i) {
                    isValid = false;
                }
            }
        }

        return isValid;
    }

    /**
     * Fills the array with an invalid sudoku solution.
     */
    public void fillSudokuArrayWithInvalidData() {
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
    public void fillSudokuArrayWithValidData() {
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
    public void fillSudokuArrayWithIllegalData() {
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

    /**
     * Empties the array.
     */
    public void resetSudokuArray() {
        sudokuArray = new int[sudokuSize][sudokuSize];
    }

    /**
     *
     * @param rowNum the rownumber which will be edited.
     * @param numString A comma separated string
     */
    public void insertRowFromCommaString( String numString, int rowNum) {
        insertRow(rowStringToArray(numString,","), rowNum);
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
     *
     */

    public void insertRow(int[] rowArray, int rowNum) {
        for(int i = 0; i < rowArray.length; i++) {
            sudokuArray[rowNum][i] = rowArray[i];
        }
    }

    /**
     *
     * @param rowString A string consisting of a sudoku row, separated by the separator variable, usually ",".
     * @param separator A separator string, the string which separates the numbers in the row.
     */
    public int[] rowStringToArray(String rowString, String separator) {
        int[] rowArray = new int[sudokuSize];
        for(int i = 0; i < sudokuSize; i++) {
            rowArray[i] = Integer.parseInt(rowString.split(separator)[i]);
        }
        return rowArray;
    }
}
