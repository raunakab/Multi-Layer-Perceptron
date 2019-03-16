package structures;

import exceptions.InvalidDotProductException;

public class HiddenLayer extends Layer {
    /** FIELDS:-------------------------------------------------------------------------------------------------------*/
    /**
     * Architecture of this Hidden Layer:
     */
    private Neuron[] neurons;
    private Layer prevLayer = null;
    private double[] prevLayerOutputVector;
    /**
     * Properties of this Hidden Layer:
     */
    private int rowDimensionality;






    /** METHODS:------------------------------------------------------------------------------------------------------*/
    /**
     * Hidden Layer Constructor:
     * @param columnDimensionality
     *
     * Constructs a Layer : Hidden Layer object. Instantiates the columnDimensionality, the size of the array of
     * Neurons, and the size of the output array.
     */
    public HiddenLayer(int columnDimensionality) {
        this.columnDimensionality = columnDimensionality;
        neurons = new Neuron[this.columnDimensionality];
        outputVector = new double[this.columnDimensionality];
        for (int i=0; i<columnDimensionality; i++) {
            Neuron neuron = new Neuron();
            neurons[i] = neuron;
        }
    }

    /**
     * Compute Transformation Method:
     * @throws InvalidDotProductException
     *
     * Pass the previous layer's output vector to each Neuron and store all resulting doubles from each Neuron in an
     * outputVector array.
     */
    private void computeTransformation() throws InvalidDotProductException {
        prevLayerOutputVector = prevLayer.getOutputVector();
        int i = 0;
        for (Neuron neuron : neurons) {
            double result = neuron.computeNeuronResult(prevLayerOutputVector);
            outputVector[i] = result;
            i++;
        }
    }






    /** GETTERS (HIDDEN LAYER ONLY):----------------------------------------------------------------------------------*/
    /**
     * Get Neurons Method:
     * @return returns the array of Neurons associated with this Hidden Layer
     */
    public Neuron[] getNeurons() {
        return neurons;
    }
    /**
     * Get Row Dimensionality Method:
     * @return returns the rowDimensionality of this Hidden Layer's matrix transformation
     */
    public int getRowDimensionality() {
        return rowDimensionality;
    }
    /**
     * Get Prev Layer Method:
     * @return returns (a pointer to) the previous layer
     */
    public Layer getPrevLayer() {
        return prevLayer;
    }
    /**
     * Get Prev Layer Output Vector Method:
     * @return returns the previous Layer's outputVector.
     *
     * NOTE: The previous layer may be a Hidden Layer or an Input Layer.
     */
    public double[] getPrevLayerOutputVector() {
        return prevLayerOutputVector;
    }
    /**
     * Get Output Vector Method:
     * @return returns this Hidden Layer's outputVector
     * @throws InvalidDotProductException
     *
     * Calls the Compute Transformation Method and returns the saved outputVector.
     */
    public double[] getOutputVector() throws InvalidDotProductException {
        computeTransformation();
        return outputVector;
    }
    /** SETTERS (HIDDEN LAYER ONLY):----------------------------------------------------------------------------------*/
    /**
     * Set Prev Layer Method:
     * @param prevLayer
     *
     * Sets the (pointer to the) previous layer as the argument. Finds the columnDimensionality of the previous Layer
     * and sets this Hidden Layer's rowDimensionality to the same value. Sets the dimensionality of each Neuron in this
     * Hidden Layer to the same value. (Done in order to produce valid matrix dimensionalities.
     */
    public void setPrevLayer(Layer prevLayer) {
        this.prevLayer = prevLayer;
        rowDimensionality = prevLayer.getColumnDimensionality();
        for (Neuron neuron : neurons) {
            neuron.setDimensionality(rowDimensionality);
        }
    }
}
