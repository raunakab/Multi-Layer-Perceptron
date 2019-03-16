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
            new NeuralNetwork(2, 10, 1, new int[] {1,4});
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
    private int outputDimensionality;
    /**
     * Training Parameters:
     */
    private int iterations;
    private int learningRate;
    /**
     * Respective paths for external files:
     * Sources for:
     *      - Input Vector File
     *      - Expected Output Vector File
     */
    private final String inputVectorsFile = "src/inputVectors.txt";
    private final String expectedOutputVectorsFile = "src/expectedOutputVectors.txt";
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
     * @param depth The depth (number of layers) in the neural network.
     * @param iterations The number of times the Neural Network tests and trains to minimize loss.
     * @param learningRate The speed of Stochastic Descent.
     * @param respectiveColumnDimensionalities The number of Neurons in each layer.
     * @throws Exception
     *
     * Constructs a new Neural Network structure with the given parameters, tests and then trains it.
     */
    public NeuralNetwork(int depth, int iterations, int learningRate, int[] respectiveColumnDimensionalities) throws Exception {
        try {
            if (depth != respectiveColumnDimensionalities.length) throw new Exception();
            this.depth = depth;
            this.iterations = iterations;
            this.learningRate = learningRate;
            outputDimensionality = respectiveColumnDimensionalities[0];

            inputVectors = importInputVectorsFromMemory(inputVectorsFile);
            expectedOutputVectors = importExpectedOutputVectorsFromMemory(expectedOutputVectorsFile);
            buildNeuralNetwork(respectiveColumnDimensionalities);

            classificationVectors = new LinkedList<>();

            for (int i=0; i<inputVectors.size(); i++) {
                inputLayer.setOutputVector(inputVectors.get(i));
                test();
            }

            for (int i=0; i<classificationVectors.size(); i++) {
                System.out.println("Vector "+i+": ");
                for (double element : classificationVectors.get(i)) {
                    System.out.println(element);
                }
            }
        } catch (IOException ioe) {
            System.out.println("IOException at NeuralNetwork(int, int, int, int[])");
            ioe.printStackTrace();
        } catch (InvalidDotProductException idpe) {
            System.out.println("InvalidDotProductException at NeuralNetwork(int, int, int, int[])");
            idpe.printStackTrace();
        } catch (NumberFormatException nfe) {
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
    private void buildNeuralNetwork(int[] respectiveLayerColumnDimensionalities) {
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
     *
     */
    public void test() throws InvalidDotProductException {
        double[] result = layers[0].getOutputVector();
        double[] destination = new double[outputDimensionality];
        for (int i=0; i<outputDimensionality; i++) {
            destination[i] = result[i];
        }
        classificationVectors.add(destination);
    }
}
