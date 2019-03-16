package structures;

public class InputLayer extends Layer {
    /** FIELDS:-------------------------------------------------------------------------------------------------------*/






    /** METHODS:------------------------------------------------------------------------------------------------------*/
    /**
     * Input Layer Constructor:
     *
     * Constructs an Input Layer Object.
     */
    public InputLayer() {}

    /**
     * Set Output Vector Method:
     * @param inputVector
     *
     * Sets the outputVector as the argument. The columnDimensionality is set to the length of the argument.
     */
    public void setOutputVector(double[] inputVector) {
        this.outputVector = inputVector;
    }






    /** GETTERS (INPUT LAYER ONLY):-----------------------------------------------------------------------------------*/
    /**
     * Get Output Vector Method:
     * @return returns the outputVector.
     */
    public double[] getOutputVector() {
        return outputVector;
    }
    /** SETTERS (INPUT LAYER ONLY):-----------------------------------------------------------------------------------*/
    public void setColumnDimensionality(int columnDimensionality) {
        this.columnDimensionality = columnDimensionality;
    }
}
