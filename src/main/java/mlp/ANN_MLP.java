package mlp;

import java.util.List;
import artificialneuralnetwork.mlp.MultilayerPerceptron;

/**
 *
 * @author Amirul_Hafiz
 */
public class ANN_MLP {

    public static void main(String[] args) {
        
        //reading the dataset and save it into data variable
        Preprocess rd = new Preprocess();
        String csvPath = "C:\\Users\\amiru\\Documents\\NetBeansProjects\\ANN_MLP\\dataset\\dataset.csv";
        List<String[]> data = rd.readCSV(csvPath);
        rd.head(data, 5);
        
        //preprocessing the dataset
        System.out.println("\nTokenized dataset:");
        double[][] encodedData = rd.encodeData(data);
        String[] headers = {"Age", "Sex", "Bp", "Cholestrol", "Na_to_K"};
        rd.head(headers,encodedData,6);
        
        System.out.println("\nNormalized dataset:");
        double[][] normalizedData = rd.normalizeData(encodedData);
        rd.head(headers,normalizedData,6);
        
        double splitRatio = 0.7;
        List<double[][]> trainTestSplit = rd.splitTrainTest(normalizedData,splitRatio);
        
        //train dataset
        System.out.println("\nTrain dataset:");
        
        double[][] trainData = trainTestSplit.get(0);
        rd.head(headers,trainData,5);
        
        //test dataset
        System.out.println("\nTest dataset:");
        
        double[][] testData = trainTestSplit.get(1);
        rd.head(headers,testData,5);
        
        //initialize the MLP
        int inputSize = trainData[0].length - 1;  
        int hiddenSize = 3;
        int outputSize = 1;
        MultilayerPerceptron mlp = new MultilayerPerceptron(inputSize, hiddenSize, outputSize);
        
        
        // Train the MLP    
        double[][] inputs = rd.extractColumns(trainData, 0, inputSize - 1);  // Extract input columns
        double[] targets = rd.extractColumn(trainData, inputSize);  // Extract target column
        mlp.training(inputs, targets);
    }
}