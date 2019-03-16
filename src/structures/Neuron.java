package structures;

import exceptions.InvalidDotProductException;

public class Neuron {
    /** FIELDS:-------------------------------------------------------------------------------------------------------*/
    /**
     * Properties of this Neuron:
     */
    private double[] weights;
    private int dimensionality;
    private double bias;
    /**
     * Guard (asserts that dimensionality has been set only once, never more):
     */
    private boolean guard = false;






    /** METHODS:------------------------------------------------------------------------------------------------------*/
    /**
     * Neuron Constructor:
     *
     * Instantiates bias as a randomly chosen double.
     */
    public Neuron() {
        bias = (Math.random()*10)-5;
    }

    /**
     * Compute Neuron Result Method:
     * @param vector
     * @return returns the result of sigmoid-ing the dot product of the vector and the weights plus the bias.
     * @throws InvalidDotProductException
     *
     * Performs a linear transformation, followed by the non-linear transformation (vertical translation), followed by
     * another non-linear transformation (the Sigmoid Activation Function).
     */
    public double computeNeuronResult(double[] vector) throws InvalidDotProductException {
        double result = computeDotProduct(vector);
        result += bias;
        //        int j = 0;
//        for (double weight : weights) {
//            System.out.println("Weight "+j+": "+weight);
//            j++;
//        }
//        System.out.println("Bias:     "+bias);
//        System.out.println("Result:   "+result);
        return sigmoidActivationFunction(result);
    }

    /**
     * Compute Dot Product Method:
     * @param vector
     * @return returns the result of the dot product (linear combination) of the argument and the corresponding
     * number of weights.
     * @throws InvalidDotProductException if the length of the argument array doesn't equal this Neuron's
     * dimensionality.
     */
    public double computeDotProduct(double[] vector) throws InvalidDotProductException {
        if (vector.length!=dimensionality) throw new InvalidDotProductException();
        double sum = 0.0;
        for (int i=0; i<dimensionality; i++) {
            sum += weights[i]*vector[i];
        }
        return sum;
    }

    /**
     * Sigmoid Activation Function Method:
     * @param input
     * @return returns 1/(1+e^(-x)), where x is the argument.
     */
    public double sigmoidActivationFunction(double input) {
        double raised = 1+Math.exp(-input);
        return 1/raised;
    }






    /** GETTERS:------------------------------------------------------------------------------------------------------*/
    /**
     * Get Weights Method:
     * @return returns the Neuron's weights.
     */
    public double[] getWeights() {
        return weights;
    }
    /**
     * Get Bias Method:
     * @return returns this Neuron's bias.
     */
    public double getBias() {
        return bias;
    }
    /**
     * Get Dimensionality Method:
     * @return returns the dimensionality of this Neuron.
     */
    public double getDimensionality() {
        return dimensionality;
    }
    /** SETTERS:------------------------------------------------------------------------------------------------------*/
    /**
     * Set Weight Method:
     * @param index
     * @param newWeight
     * @throws IndexOutOfBoundsException if the given index is not within the bounds of the array of weights.
     *
     * Sets the weight of this Neuron at the given index as the newWeight if index is appropriate.
     */
    public void setWeight(int index, double newWeight) throws IndexOutOfBoundsException {
        if (index<0 || index>=weights.length) throw new IndexOutOfBoundsException();
        weights[index] = newWeight;
    }
    /**
     * Set Weights Method:
     * @param weights
     * @throws Exception if the length of the new array of weights do not equal the dimensionality of this Neuron.
     *
     * Sets the entire array of weights to the given array.
     */
    public void setWeights(double[] weights) throws Exception {
        if (weights.length != dimensionality) throw new Exception();
        this.weights = weights;
    }
    /**
     * Set Bias Method:
     * @param newBias
     *
     * Sets the bias of this Neuron as the argument.
     */
    public void setBias(double newBias) {
        bias = newBias;
    }
    /**
     * Set Dimensionality Method:
     * @param dimensionality
     *
     * Sets the dimensionality of this Neuron as the argument. Instantiates the size of the array of weights as the
     * argument. Randomly instantiates new weights and populates the array of weights in order. Random values of weights
     * can only be between -5 and 5. Sets Guard to true, indicating that the dimensionality of this Neuron has been set
     * and can never be changed.
     */
    public void setDimensionality(int dimensionality) {
        if (!guard) {
            this.dimensionality = dimensionality;
            weights = new double[dimensionality];
            for (int i = 0; i < dimensionality; i++) {
                weights[i] = (Math.random() * 10) - 5;
            }
            guard = true;
        } else System.out.println("Guard prevented the change of dimensionality.");
    }
}
