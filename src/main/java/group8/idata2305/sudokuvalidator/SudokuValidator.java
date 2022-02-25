package group8.idata2305.sudokuvalidator;

/**
 * Validates 9x9 sudoku solutions.
 */
public class SudokuValidator {
    private int[][] sudokuArray;
    private final int sudokuSize = 9;
    private int areaSum;

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

        if(rowSum == areaSum) {
            isValid = false;
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
       // sudokuArray[0][0] = 1;
        //sudokuArray[0][1] = 2;
    }

    /**
     * Fills the array with a valid sudoku solution.
     */
    public void fillSudokuArrayWithValidData() {

    }

    /**
     * Fills the array with an illegal sudoku solution, such as invalid integers like 10 or 0.
     */
    public void fillSudokuArrayWithIllegalData() {

    }

    /**
     * Empties the array.
     */
    public void resetSudokuArray() {
        sudokuArray = new int[sudokuSize][sudokuSize];
    }

    /**
     * Prints out the sudoku game for easier viewing.
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
}
