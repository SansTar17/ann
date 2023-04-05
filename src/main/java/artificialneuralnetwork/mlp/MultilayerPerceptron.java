package artificialneuralnetwork.mlp;

/**
 *
 * @author Amirul_Hafiz
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.Convert;

public class MultilayerPerceptron {
    public List<Layer> layers;
    public List<Double> inputs;
    public List<Double> outputs;
    private double input;
    private double error = 0.005d;

    public MultilayerPerceptron(int input, int hidden, int output) {
        this.input = input;
        this.layers = new ArrayList<Layer>();
        this.inputs = new ArrayList<Double>();
        this.outputs = new ArrayList<Double>();

        // hidden layer
        this.layers.add(new Layer(input, hidden));
        // output layer
        this.layers.add(new Layer(hidden, output));
    }
    public double[] run(double[] inputs) {
        for(Layer layer: this.layers)
            inputs = Convert.toArraydouble(calculateLayer(layer, Convert.toArrayDouble(inputs)));
        
        return inputs;
    }
    public void training(double[][] inputs, double[] outputs) {
        long iteration = 0;
        double errorTraining;
        do
        {
            errorTraining = 0;
            int numberTrain = inputs.length / inputs[0].length;
            for (int i = 0; i < numberTrain; i++)
            {
                double tagert = outputs[i];
                Double[] inputTest = new Double[inputs[0].length];
                for (int j = 0; j < inputs[0].length; j++)
                    inputTest[j] = inputs[i][j];

                double output = run(Convert.toArraydouble(inputTest))[0];
                backpropagation(inputTest, output, tagert);

                double delta = tagert - output;
                errorTraining += Math.pow(delta, 2);
            }
            // Display progress
            if (iteration % 10 == 0) {  // Update every 10 iterations
                double progress = 100.0 * (1 - (errorTraining / (inputs.length * outputs.length)));
                System.out.println("Training Progress: " + progress + "%");
        }
            iteration++;
        } while (errorTraining >= this.error);
    }
    private Double[] calculateLayer(Layer layer, Double[] input) {
        Double[] values = new Double[layer.getNeuronsSize()];
        for (int i = 0; i < layer.getNeuronsSize(); i++)
        {
            double valueNeuron = layer.getNeuron(i).loadNeuron(Arrays.asList(input));
            values[i] = layer.getNeuron(i).activate(valueNeuron);
        }
        return values;
    }
    private void backpropagation(Double[] input, Double output, Double target) {
        // Go layers
        for (int i = (this.layers.size() - 1); i >= 0; i--) {
            // Go neurons layer
            for (int j = 0; j < this.layers.get(i).getNeuronsSize(); j++) {
                List<Double> outputsLeft = new ArrayList<Double>();
                outputsLeft.addAll(i > 0 ? this.layers.get(i - 1).getOutputs() : Arrays.asList(input));

                if (i == this.layers.size() - 1) {
                    this.layers.get(i).getNeuron(j).updateWeightOutputLayer(output, outputsLeft, target);
                } else {
                    this.layers.get(i).getNeuron(j).updateWeightHiddenLayer(
                        this.layers.get(i).getNeuron(j).getOutput(),
                        outputsLeft,
                        this.layers.get(i + 1).getWeights(j),
                        this.layers.get(i + 1).getGradients());
                }
            }
        }
    }
}