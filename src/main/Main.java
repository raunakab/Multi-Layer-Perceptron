package main;

import exceptions.InvalidDotProductException;
import structures.HiddenLayer;
import structures.InputLayer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main {
    /** FIELDS:-------------------------------------------------------------------------------------------------------*/






    /** METHODS:------------------------------------------------------------------------------------------------------*/
    /**
     * Main:
     * @param args
     *
     * Instantiates the build of the Neural Network by:
     *      - Declaring the number of desired layers
     *      - Declaring the number of neurons in each layer
     * Instantiates input and expected output vectors from external .txt files.
     * Creates abstract Layers and connects them in order.
     * Sets the last abstract Layer to point to the InputLayer.
     * Calls getOutputVector() on the very last layer, which recursively calls previous layers until the base InputLayer
     *      is reached.
     */
    public static void main(String[] args) {
        try {
            NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {1,4}, "src/inputVectors.txt", "src/expectedOutputVectors.txt");
            neuralNetwork.runAndTrain(10,0.1);
        } catch (Exception e) {
            System.out.println("Exception at main(String[])");
            e.printStackTrace();
        }
        //        try {
//            inputVectors = importInputVectorsFromMemory(inputVectorsFile);
//            expectedOutputVectors = importExpectedOutputVectorsFromMemory(expectedOutputVectorsFile);
//            setProperties(2, 10, 1);
//            HiddenLayer pointer = buildNeuralNetwork(new int[] {1, 2});
//
//            EXECUTE2 EXECUTE2 = new EXECUTE2(pointer, inputVectors, expectedOutputVectors);
//
//            InputLayer inputLayer = new InputLayer();
//            inputLayer.setOutputVector(new double[] {9,9,9});
//            pointer.setPrevLayer(inputLayer);
//
//            for (double[] inputVector : inputVectors) {
//                inputLayer.setOutputVector(inputVector);
//                EXECUTE2();
//                predictedOutputVectors.add(i);
//                for (double[] vector : predictedOutputVectors) {
//                    for (double element : vector) {
//                        System.out.println(element);
//                    }
//                }
//            }
//
//            for (double[] inputVector : inputVectors) {
//                inputLayer.setOutputVector(inputVector);
//                EXECUTE2();
//                System.out.println(predictedOutputVectors.size());
//            }
//            int j = 0;
//            for (double[] prediction : predictedOutputVectors) {
//                System.out.println("Prediction "+j+": ");
//                j++;
//                for (double elt : prediction) {
//                    System.out.println(elt);
//                }
//            }
//        } catch (InvalidDotProductException idpe) {
//            System.out.println("InvalidDotProductException at main(String[])");
//            idpe.printStackTrace();
//        } catch (NumberFormatException nfe) {
//            System.out.println("NumberFormatException at main(String[])");
//            nfe.printStackTrace();
//        } catch (IOException ioe) {
//            System.out.println("IOException at main(String[])");
//            ioe.printStackTrace();
//        }
    }
}

class NeuralNetwork {
    /** FIELDS:-------------------------------------------------------------------------------------------------------*/
    /**
     * Properties of the Multi-Layer Perceptron Neural Net System:
     */
    private int depth;
    private HiddenLayer[] layers;
    private InputLayer inputLayer;
    private int[] respectiveColumnDimensionalities;
    /**
     * Fields for:
     *      - Input Vectors
     *      - Expected Output Vectors
     *      - Predicted Output Vectors (the classification of the Neural Network)
     */
    private List<double[]> inputVectors;
    private List<double[]> expectedOutputVectors;
    private List<double[]> classificationVectors;






    /** METHODS:------------------------------------------------------------------------------------------------------*/
    /**
     * Neural Network Constructor:
     * @param respectiveColumnDimensionalities The number of Neurons in each layer.
     * @throws Exception
     *
     * Constructs a new Neural Network structure with the given parameters, tests and then trains it.
     */
    public NeuralNetwork(int[] respectiveColumnDimensionalities, String ipSource, String opSource) throws Exception {
        try {
            this.depth = respectiveColumnDimensionalities.length;
            this.respectiveColumnDimensionalities = respectiveColumnDimensionalities;

            inputVectors = importInputVectorsFromMemory(ipSource);
            expectedOutputVectors = importExpectedOutputVectorsFromMemory(opSource);
            build(respectiveColumnDimensionalities);
        } catch (IOException ioe) {
            System.out.println("IOException at NeuralNetwork(int, int, int, int[])");
            ioe.printStackTrace();
        }
//        catch (InvalidDotProductException idpe) {
//            System.out.println("InvalidDotProductException at NeuralNetwork(int, int, int, int[])");
//            idpe.printStackTrace();
//        }
        catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException at NeuralNetwork(int, int, int, int[])");
            nfe.printStackTrace();
        }
    }

    /**
     * Build Neural Network Method:
     * @param respectiveLayerColumnDimensionalities
     * @return returns (a pointer to) the last Hidden Layer in the array of Hidden Layers.
     *
     * Builds a Multi-Layer Perceptron Neural Network. Instantiates a number of Hidden Layers corresponding to the
     * length of the arguments. Fills each Hidden Layer with a given number of Neurons in it according to the
     * corresponding indexed integer from the argument (only does so for the Hidden Layers [including the output
     * layer]). Sets up each Hidden Layer to point to the previous layer. A pointer to the last Hidden Layer is
     * returned.
     */
    private void build(int[] respectiveLayerColumnDimensionalities) {
        layers = new HiddenLayer[depth];
        for (int i=0; i<depth; i++) {
            HiddenLayer hiddenLayer = new HiddenLayer(respectiveLayerColumnDimensionalities[i]);
            layers[i] = hiddenLayer;
        }

        HiddenLayer pointer = layers[0];
        int i = 0;
        while (i<depth-1) {
            pointer.setPrevLayer(layers[i+1]);
            pointer = (HiddenLayer) pointer.getPrevLayer();
            i++;
        }
        inputLayer = new InputLayer();
        inputLayer.setColumnDimensionality(inputVectors.get(0).length);
        pointer.setPrevLayer(inputLayer);
    }

    /**
     * Import Input Vectors from Memory Method:
     * @param sourceFile
     * @return returns a (linked) list of arrays of doubles.
     * @throws IOException
     * @throws NumberFormatException
     *
     * Imports Input Vectors from the respective external file.
     */
    private static List<double[]> importInputVectorsFromMemory(String sourceFile) throws IOException, NumberFormatException {
        List<String> inputVectorsAsString;
        List<double[]> inputVectors = new LinkedList<>();

        inputVectorsAsString = Files.readAllLines(Paths.get(sourceFile), StandardCharsets.UTF_8);
        for (String vectorAsString : inputVectorsAsString) {
            String[] elementsAsString = vectorAsString.split(", ");
            double[] inputVector = new double[elementsAsString.length];
            for (int i=0; i<elementsAsString.length; i++) {
                inputVector[i] = Double.parseDouble(elementsAsString[i]);
            }
            inputVectors.add(inputVector);
        }

        int length = inputVectors.get(0).length;
        for (double[] vector : inputVectors) {
            if (vector.length != length) throw new IOException();
        }

        //        int j = 0;
//        for (double[] inputVector : inputVectors) {
//            System.out.println("Input Vector "+j+": ");
//            j++;
//            for (double elt : inputVector) {
//                System.out.println(elt);
//            }
//        }
//        System.out.println("Input Vectors Size: "+inputVectors.size());

        return inputVectors;
    }

    /**
     * Import Expected Output Vectors from Memory Method:
     * @param sourceFile
     * @return returns a (linked) list of arrays of doubles.
     * @throws IOException
     * @throws NumberFormatException
     *
     * Imports Expected Output Vectors from the respective external file.
     */
    private static List<double[]> importExpectedOutputVectorsFromMemory(String sourceFile) throws IOException, NumberFormatException {
        List<String> expectedOutputVectorsAsString;
        List<double[]> expectedOutputVectors = new LinkedList<>();

        expectedOutputVectorsAsString = Files.readAllLines(Paths.get(sourceFile), StandardCharsets.UTF_8);
        for (String vectorsAsString : expectedOutputVectorsAsString) {
            String[] elementsAsString = vectorsAsString.split(", ");
            double[] expectedOutputVector = new double[elementsAsString.length];
            for (int i=0; i<elementsAsString.length; i++) {
                expectedOutputVector[i] = Double.parseDouble(elementsAsString[i]);
            }
            expectedOutputVectors.add(expectedOutputVector);
        }

        int length = expectedOutputVectors.get(0).length;
        for (double[] vector : expectedOutputVectors) {
            if (vector.length != length) throw new IOException();
        }

        //        int j = 0;
//        for (double[] inputVector : inputVectors) {
//            System.out.println("Expected Output Vector "+j+": ");
//            j++;
//            for (double elt : inputVector) {
//                System.out.println(elt);
//            }
//        }
//        System.out.println("Expected Output Vectors Size: "+inputVectors.size());

        return expectedOutputVectors;
    }

    /**
     * Test Method:
     * @throws InvalidDotProductException
     *
     * Finds the output of the Neural Network for the given Input Layer's output vector, copies it into a destination,
     * and adds the result to the classificationVectors list.
     */
    private void test() throws InvalidDotProductException {
        double[] result = layers[0].getOutputVector();
        double[] destination = new double[respectiveColumnDimensionalities[0]];
        for (int i=0; i<respectiveColumnDimensionalities[0]; i++) {
            destination[i] = result[i];
        }
        classificationVectors.add(destination);
    }

    /**
     * Run Method:
     * @throws InvalidDotProductException If the Test Method (a method called from within this) throws it.
     *
     * For each vector in the inputVectors list, InputLayer is modified to output the selected vector. The Test Method
     * is then called on the (same) neural network (but with a different inputLayer output vector). The results are
     * stored in classificationVectors. After each iteration, each vector in classificationVectors is subtracted from
     * the corresponding vector (at the same index) in the expectedOutputVectors list. The difference is squared and
     * summed over the entire list.
     */
    private void run() throws Exception {
        int n = weightCount();
        classificationVectors = new LinkedList<>();
        for (double[] inputVector : inputVectors) {
            inputLayer.setOutputVector(inputVector);
            test();
        }
        if (classificationVectors.size() != expectedOutputVectors.size()) throw new Exception();
        double size = classificationVectors.get(0).length;
        double error = 0;
        for (int i=0; i<classificationVectors.size(); i++) {
            double[] vectorACT = classificationVectors.get(i);
            double[] vectorEXP = expectedOutputVectors.get(i);
            if (vectorACT.length!=size || vectorEXP.length!=size) throw new Exception();
            for (int j=0; j<vectorACT.length; j++) {
                error += (vectorACT[j]-vectorEXP[j])*(vectorACT[j]-vectorEXP[j]);
            }
        }
        error = error/(2*n);
        System.out.println(error);
    }

    /**
     * Run and Train Method:
     * @param iterations The number of times the Neural Network tests and trains to minimize loss.
     * @param learningRate The speed of Stochastic Descent.
     * @throws Exception
     *
     * Runs and then trains the Neural Network a number of times according to iterations. The Stochastic Descent
     * (training) of the Neural Network is processed at the learning rate.
     */
    public void runAndTrain(int iterations, double learningRate) throws Exception {
        for (int i=0; i<iterations; i++) {
            run();
        }
    }

    /**
     * Weight Count Method:
     * @return The number of weights in the Neural Network
     *
     * Calculates the number of weights in the Neural Network and returns the value.
     */
    private int weightCount() {
        int sum = 0;
        for (int i=0; i<respectiveColumnDimensionalities.length-1; i++) {
            sum += respectiveColumnDimensionalities[i]*respectiveColumnDimensionalities[i+1];
        }
        return sum;
    }
}
