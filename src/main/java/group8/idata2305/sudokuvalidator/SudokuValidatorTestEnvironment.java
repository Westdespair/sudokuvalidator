package group8.idata2305.sudokuvalidator;

import java.io.IOException;
import java.util.ArrayList;

public class SudokuValidatorTestEnvironment {
    public static void main (String args[]) throws IOException {
        SudokuValidator sudokuValidator = new SudokuValidator();
        CSVSolutionReader solutionReader = new CSVSolutionReader();

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

        ArrayList<String> rowList = solutionReader.readFile("C:\\Users\\Sindre\\Documents\\Skole lokal\\Dataingeniør år 2\\Semester 4\\Operativsystemer\\sudokuvalidator\\importTest.csv");
        sudokuValidator.resetSudokuArray();
        for (int i = 0; i < rowList.size(); i++) {
            String row = rowList.get(i);
            sudokuValidator.insertRowFromCommaString(row, i);
        }

        System.out.println("Behold the line read from file!!");
        sudokuValidator.printRows();

    }
}
