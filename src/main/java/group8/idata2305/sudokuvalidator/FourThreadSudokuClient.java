package group8.idata2305.sudokuvalidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A sudokuValidator client which runs in 4 threads - boxValidation, rowValidation, columnValidation, and user input
 */
public class FourThreadSudokuClient extends Thread {
    SudokuValidator sudokuValidator = new SudokuValidator();
    UserThread userThread = new UserThread();
    RowThread rowThread = new RowThread();
    BoxThread boxThread = new BoxThread();
    ColumnThread columnThread = new ColumnThread();
    CSVSolutionReader solutionReader = new CSVSolutionReader();

    /**
     * Main method, runs the parentThread UserThread.
     * @param args
     */
    public static void main(String[] args) {
        FourThreadSudokuClient client = new FourThreadSudokuClient();
        client.sudokuValidator.resetSudokuArray();
        client.userThread.run();
    }

    /**
     * The text-based user-interface of the project. Also works as the parent thread, ordering the other threads.
     */
    class UserThread implements Runnable {

        @Override
        public void run() {
            boolean running = true;
            Scanner scanner = new Scanner(System.in);

            //User activity happens inside this loop, as long as the user decides to continue.
            while(running) {
                System.out.print("Enter filename of comma-separated sudoku solution .csv file: ");
                //User provides a full filepath to the CSV-file via the console.
                String filePath = scanner.nextLine();
                File csvFile = new File(filePath);

                //If the provided file doesn't exist, make the user write a new filepath.
                while (!csvFile.exists()) {
                    System.out.print("Try another filepath: ");
                    filePath = scanner.nextLine();
                    csvFile = new File(filePath);
                }
                System.out.println("Filepath: " + filePath);
                //Attempts to read and print out the sudoku solution.
                    try {
                        sudokuValidator.inputStringArrayAsSolution(solutionReader.readFile(filePath));
                        sudokuValidator.printRows();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Starts a thread to validate the 3x3 boxes of the solution.
                    boxThread = new BoxThread();
                    boxThread.run();

                    //Starts a thread to validate the 1x9 columns of the solution.
                    columnThread = new ColumnThread();
                    columnThread.run();

                    //Starts a thread to validate the 9x1 rows of the solution.
                    rowThread = new RowThread();
                    rowThread.run();

                    //Checks if the solution is valid by checking if all threads are valid.
                    if(boxThread.allBoxesValid() && columnThread.allColumnsValid() && rowThread.allRowsValid()) {
                        System.out.println("The provided solution is valid!");
                    }

                    //Asks the user if they want to continue. If Y, continue, if N, exit.
                    System.out.println("Do you want to check another file? Y/N");
                    String response = scanner.nextLine().toUpperCase().replaceAll("[^YN]","");

                    //If user replies with N, close the application.
                    if (response.contains("N") && !response.contains("Y")){
                        System.out.println("Closing application...");
                        running = false;
                    }
            }
        }

        /**
         * Reads a .csv file and adds the solution to the sudokuvalidator
         * @param filePath The full path of the file, including the filename.
         * @throws IOException if the file can't be read.
         */
        public void readFileToSudokuValidator(String filePath) throws IOException {
            ArrayList<String> rowList = solutionReader.readFile(filePath);
            sudokuValidator.resetSudokuArray();
            for (int i = 0; i < rowList.size(); i++) {
                String row = rowList.get(i);
                sudokuValidator.insertRowFromCommaString(row, i);
            }
        }
    }

    /**
     * When a BoxThread thread is run, it checks all 3x3 boxes of the sudoku solution.
     * Contains true if valid, false if invalid.
     */
    class BoxThread implements Runnable {
        boolean isValid = true;

        @Override
        public void run() {
            for (int i = 0; i < 9; i++) {
                if(!sudokuValidator.isBoxValid(i)) {
                    isValid = false;
                    System.out.println("Box number " + i + " is invalid!");
                }
            }
        }
        public boolean allBoxesValid() {
            return isValid;
        }
    }
    /**
     * When a RowThread thread is run, it checks all 1x9 rows of the sudoku solution.
     * Contains true if valid, false if invalid.
     */
        class RowThread implements Runnable {
            boolean isValid = true;

            @Override
            public void run() {
                for (int i = 0; i < 9; i++) {
                    if(!sudokuValidator.isRowValid(i)) {
                        isValid = false;
                        System.out.println("Row number " + i + " is invalid!");
                    }
                }
            }
            public boolean allRowsValid() {
                return isValid;
            }
    }
    /**
     * When a ColumnThread thread is run, it checks all 9x1 columns of the sudoku solution.
     * Contains true if valid, false if invalid.
     */
        class ColumnThread implements Runnable {
            boolean isValid = true;

            @Override
            public void run() {
                for (int i = 0; i < 9; i++) {
                    if (!sudokuValidator.isColumnValid(i)) {
                        isValid = false;
                        System.out.println("Column number " + i + " is invalid!");
                    }
                }
            }
            public boolean allColumnsValid() {
                return isValid;
            }
    }
}
