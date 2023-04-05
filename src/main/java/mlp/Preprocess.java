package mlp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Preprocess {
    
    public static List<String[]> readCSV(String csvFilePath) {
    String line = "";
    String delimiter = ",";
    List<String[]> data = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
        br.readLine();

        while ((line = br.readLine()) != null) {
            // Split the line into an array of values
            String[] row = line.split(delimiter);
            // Add the row to the data list
            data.add(row);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return data;
}
    public static void head(List<String[]> data, int n) {
    // Print the header row
    String[] headers = data.get(0);
    for (String header : headers) {
        System.out.print(header + "\t");
    }
    System.out.println();

    // Print the first n-1 rows of data
    int numRowsToPrint = Math.min(n - 1, data.size() - 1);
    for (int i = 0; i < numRowsToPrint; i++) {
        String[] row = data.get(i + 1);
        for (String cell : row) {
            System.out.print(cell + "\t");
        }
        System.out.println();
        }
    }
    public static void head(String[] headers, double[][] data, int n) {
    // Print the header row
    for (String header: headers) {
        System.out.print(header + "\t");
    }
    System.out.println();

    // Print the first n-1 rows of data
    int numRowsToPrint = Math.min(n - 1, data.length - 1);
    for (int i = 0; i < numRowsToPrint; i++) {
        for (int j = 0; j < data[i].length; j++) {
            System.out.print(data[i][j] + "\t");
        }
        System.out.println();
    }
}
    public static double[][] encodeData(List<String[]> data) {
    int numRows = data.size() - 1;
    int numCols = data.get(0).length - 1;
    double[][] encodedData = new double[numRows][numCols];

    for (int i = 0; i < numRows; i++) {
        String[] row = data.get(i + 1);
        for (int j = 0; j < numCols; j++) {
            if (j == 1) { // Encode Sex column
                encodedData[i][j] = (row[j].equals("M")) ? 1.0 : 0.0;
            } else if (j == 2) { // Encode BP column
                if (row[j].equals("LOW")) {
                    encodedData[i][j] = 0.0;
                } else if (row[j].equals("NORMAL")) {
                    encodedData[i][j] = 0.5;
                } else if (row[j].equals("HIGH")) {
                    encodedData[i][j] = 1.0;
                }
            } else if (j == 3) { // Encode Cholesterol column
                encodedData[i][j] = (row[j].equals("HIGH")) ? 1.0 : 0.0;
            } else { // Encode numeric columns
                encodedData[i][j] = Double.parseDouble(row[j]);
            }
        }
    }
    return encodedData;
}
    public static double[][] normalizeData(double[][] data) {
    int numRows = data.length;
    int numCols = data[0].length;
    double[][] normalizedData = new double[numRows][numCols];

    for (int j = 0; j < numCols; j++) {
        double maxVal = Double.MIN_VALUE;
        double minVal = Double.MAX_VALUE;
        for (int i = 0; i < numRows; i++) {
            maxVal = Math.max(maxVal, data[i][j]);
            minVal = Math.min(minVal, data[i][j]);
        }
        double range = maxVal - minVal;
        for (int i = 0; i < numRows; i++) {
            normalizedData[i][j] = (data[i][j] - minVal) / range;
        }
    }
    return normalizedData;
}
    public static List<double[][]> splitTrainTest(double[][] data, double splitRatio) {
    int numRows = data.length;
    int numTrainRows = (int) Math.round(numRows * splitRatio);
    int numTestRows = numRows - numTrainRows;
    
    double[][] trainData = new double[numTrainRows][data[0].length];
    double[][] testData = new double[numTestRows][data[0].length];
    
    // Shuffle the data randomly
    List<double[]> dataList = Arrays.asList(data);
    Collections.shuffle(dataList);
    
    for (int i = 0; i < numTrainRows; i++) {
        trainData[i] = dataList.get(i);
    }
    for (int i = 0; i < numTestRows; i++) {
        testData[i] = dataList.get(numTrainRows + i);
    }
    
    List<double[][]> trainTestSplit = new ArrayList<>();
    trainTestSplit.add(trainData);
    trainTestSplit.add(testData);
    
    return trainTestSplit;
}
    public static double[][] extractColumns(double[][] data, int startIndex, int endIndex) {
    double[][] result = new double[data.length][endIndex - startIndex + 1];
    for (int i = 0; i < data.length; i++) {
        for (int j = startIndex; j <= endIndex; j++) {
            result[i][j - startIndex] = data[i][j];
        }
    }
    return result;
}
    public static double[] extractColumn(double[][] data, int columnIndex) {
    double[] columnData = new double[data.length];
    for (int i = 0; i < data.length; i++) {
        columnData[i] = data[i][columnIndex];
    }
    return columnData;
}
}