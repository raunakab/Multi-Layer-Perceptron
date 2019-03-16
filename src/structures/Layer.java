package structures;

import exceptions.InvalidDotProductException;

public abstract class Layer {
    //Fields for the Architecture of an Abstract Layer:
    /** FIELDS:-------------------------------------------------------------------------------------------------------*/
    /**
     * Properties of this Layer:
     */
    protected double[] outputVector;
    protected int columnDimensionality;






    /** METHODS:------------------------------------------------------------------------------------------------------*/






    /** GETTERS:------------------------------------------------------------------------------------------------------*/
    /**
     * Get Output Vector Method:
     * @return the outputVector of this Layer.
     * @throws InvalidDotProductException
     *
     * Hidden Layer:
     *      Retrieves the outputVector of the previous layer, performs a transformation on it, and returns it.
     * Input Layer:
     *      Returns outputVector.
     */
    public abstract double[] getOutputVector() throws InvalidDotProductException;
    /**
     * Get Column Dimensionality Method:
     * @return returns the columnDimensionality of this Layer.
     */
    public int getColumnDimensionality() {
        return columnDimensionality;
    }
    /** SETTERS:------------------------------------------------------------------------------------------------------*/
}
