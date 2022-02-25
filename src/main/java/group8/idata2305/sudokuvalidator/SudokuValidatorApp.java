package group8.idata2305.sudokuvalidator;
public class SudokuValidatorApp {
    public static void main (String args[]){
        SudokuValidator sudokuValidator = new SudokuValidator();
        sudokuValidator.fillSudokuArrayWithInvalidData();
        sudokuValidator.printRows();

    }
}
