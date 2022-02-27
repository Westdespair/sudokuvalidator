package group8.idata2305.sudokuvalidator;

public class SudokuValidatorApp {
    public static void main (String args[]){
        SudokuValidator sudokuValidator = new SudokuValidator();

        sudokuValidator.fillSudokuArrayWithInvalidTestData();
        sudokuValidator.printRows();
        System.out.println("Should return false, game is invalid FALSE: " + sudokuValidator.isSudokuArrayValid());
        System.out.println();

        sudokuValidator.fillSudokuArrayWithIllegalTestData();
        System.out.println("Should return false again, game is invalid FALSE: " + sudokuValidator.isSudokuArrayValid());
        sudokuValidator.printRows();
        System.out.println();

        sudokuValidator.fillSudokuArrayWithValidTestData();
        System.out.println("Should return true here ,game is valid TRUE: " + sudokuValidator.isSudokuArrayValid());
        sudokuValidator.printRows();

        System.out.println("Should be true: " + sudokuValidator.isRowValid(0));

        int[] noDupeArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Test on list with no duplicates, should be true: " + sudokuValidator.intListContainsNoDuplicateValues(noDupeArray));


        int[] dupeArray = {1, 2, 3, 4, 1, 6, 7, 8, 9};
        System.out.println("Test on list with one duplicate, should be false: " + sudokuValidator.intListContainsNoDuplicateValues(dupeArray));

        int[] inValidArray = {100, 1, 2, 3, 4, 5, 6, 7};
        System.out.println("Tests on list with invalid numbers, should be false: " + sudokuValidator.intListIsContainsValidIntegers(inValidArray));

        int[] validArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Test on list with valid numbers, should be true: " + sudokuValidator.intListIsContainsValidIntegers(validArray));
        System.out.println();
    }
}
