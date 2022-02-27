package group8.idata2305.sudokuvalidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A sudokuValidator client which runs in 4 threads - boxvalidation, rowvalidation, columnvalidation, and user input
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

            while(running) {
                System.out.print("Enter filename of comma-separated sudoku solution .csv file: ");
                //User provides a filepath.
                String filePath = scanner.nextLine();
                File csvFile = new File(filePath);

                while (!csvFile.exists()) {
                    System.out.print("Try another filepath: ");
                    filePath = scanner.nextLine();
                    csvFile = new File(filePath);
                }
                System.out.println("Filepath: " + filePath);
                    try {
                        sudokuValidator.inputStringArrayAsSolution(solutionReader.readFile(filePath));
                        sudokuValidator.printRows();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                boxThread = new BoxThread();
                boxThread.run();

                columnThread = new ColumnThread();
                columnThread.run();

                rowThread = new RowThread();
                rowThread.run();

                if(boxThread.allBoxesValid() && columnThread.allColumnsValid() && rowThread.allRowsValid()) {
                    System.out.println("The provided solution is valid!");
                }

                System.out.println("Do you want to check another file? Y/N");
                String response = scanner.nextLine().toUpperCase().replaceAll("[^YN]","");

                //If user replies with N, close the application.
                if (response.contains("N") && !response.contains("Y")){
                    System.out.println("Closing application...");
                    running = false;
                }
            }
        }

        public void readFileToSudokuValidator(String filePath) throws IOException {
            ArrayList<String> rowList = solutionReader.readFile(filePath);
            sudokuValidator.resetSudokuArray();
            for (int i = 0; i < rowList.size(); i++) {
                String row = rowList.get(i);
                sudokuValidator.insertRowFromCommaString(row, i);
            }
        }
    }

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