package group8.idata2305.sudokuvalidator;

import java.io.*;
import java.util.ArrayList;

/**
 * Reads .CSV files containing comma separated inputs of a sudoku solution.
 * These files can then be processed and changed to a string of ints.
 */
public class CSVSolutionReader{

    BufferedReader reader;

    /**
     *
     * @param fileName The absolute path to a file.
     * @return CSVLineList An arrayList of strings, each being a line of a CSV-file.
     * @throws Exception IOException
     */
    public ArrayList<String> readFile(String fileName) throws IOException {
        ArrayList CSVLineList = new ArrayList<String>();

        //Attempts to find the file based on the filename input.
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        }

        String lineString = "";
        while (lineString != null) {
            lineString = reader.readLine();

            if (lineString != null) {
                CSVLineList.add(lineString);
            }
        }
        return CSVLineList;
    }
}

