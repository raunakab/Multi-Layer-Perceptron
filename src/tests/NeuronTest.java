package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.Neuron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class NeuronTest {
    /** FIELDS:-------------------------------------------------------------------------------------------------------*/
    private Neuron neuron;






    /** METHODS:------------------------------------------------------------------------------------------------------*/
    /**
     * Run Before Setter:
     *
     * Instantiates Neuron. Sets the dimensionality to 5.
     */
    @BeforeEach
    public void runBefore() {
        neuron = new Neuron();
        neuron.setDimensionality(5);
    }

    /**
     * Instantiation Test:
     *
     * Tests to make sure the instantiation of the Neuron is correct
     */
    @Test
    public void testInstantiation() {
        assertEquals(5, neuron.getWeights().length);
        for (double elt : neuron.getWeights()) {
            System.out.println(elt);
        }
    }

    /**
     * Set Weight Test:
     *
     * Tests to make sure the index is within the appropriate bounds before changing weight[index].
     */
    @Test
    public void testSetWeight() {
        try {
            /** Invalid index (less than 0): */
            neuron.setWeight(-1,1);
            fail();
        } catch (IndexOutOfBoundsException ioobe) {}
        try {
            /** Invalid index (greater than length of the array of weights): */
            neuron.setWeight(5,1);
            fail();
        } catch (IndexOutOfBoundsException ioobe) {}
        try {
            /** Valid index (first index): */
            neuron.setWeight(0,1);
        } catch (IndexOutOfBoundsException ioobe) {
            fail();
        }
        try {
            /** Valid index (last index): */
            neuron.setWeight(4,1);
        } catch (IndexOutOfBoundsException ioobe) {
            fail();
        }
    }

    /**
     * Set Weights Test:
     *
     * Tests to make sure that weights are only set if they match the dimensionality of the neuron.
     */
    @Test
    public void testSetWeights() {
        /** Incorrect dimensionality (too short): */
        try {
            neuron.setWeights(new double[] {});
            fail();
        } catch (Exception e) {}

        /** Incorrect dimensionality (too long): */
        try {
            neuron.setWeights(new double[] {1,1,1,1,1,1,1,1,1});
            fail();
        } catch (Exception e) {}

        /** Correct dimensionality: */
        try {
            neuron.setWeights(new double[] {1,2,3,4,5});
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Set Bias Test:
     *
     * Tests to make sure the bias is correctly set when called with a double.
     */
    @Test
    public void testSetBias() {
        neuron.setBias(1.0);
        assertEquals(1.0, neuron.getBias());
    }

    /**
     * Set Dimensionality Test:
     *
     * Tests to make sure that the dimensionality is only set once, never more.
     */
    @Test
    public void testSetDimensionality() {
        assertEquals(neuron.getDimensionality(),5);
        neuron.setDimensionality(100);
        if (neuron.getDimensionality() != 5) {
            fail();
        }
    }

    /**
     * Compute Dot Product Test:
     *
     * Tests to make sure the dot product is properly computed.
     */
    @Test
    public void testComputeDotProduct() {
        double result;
        try {
            neuron.setWeights(new double[] {1,2,3,4,5});
        } catch (Exception e) {
            fail();
        }
        try {
            /** Invalid vector (vector is too short): */
            neuron.computeDotProduct(new double[] {1,2,3,4});
            fail();
        } catch (Exception e) {}
        try {
            /** Invalid vector (vector is too long): */
            neuron.computeDotProduct(new double[] {1,2,3,4,5,6});
            fail();
        } catch (Exception e) {}
        try {
            /** Valid vector: */
            result = neuron.computeDotProduct(new double[] {1,2,3,4,5});
            assertEquals(result, 1+4+9+16+25);
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Compute Neuron Result Test:
     *
     * Tests to make sure the function properly computes sig(v•w+b), where v and w are elements of R^m, v•w (the dot
     * product) and b are an element of R, and sig(x) = (1+e^(-x))^(-1) is a transformation from R -> R.
     */
    @Test
    public void testComputeNeuronResult() {
        double result;
        neuron.setBias(1.0);
        try {
            neuron.setWeights(new double[] {1,2,3,4,5});
        } catch (Exception e) {
            fail();
        }
        try {
            /** Invalid vector (vector is too short): */
            neuron.computeNeuronResult(new double[] {1,2,3,4});
            fail();
        } catch (Exception e) {}
        try {
            /** Invalid vector (vector is too long): */
            neuron.computeNeuronResult(new double[] {1,2,3,4,5,6});
            fail();
        } catch (Exception e) {}
        try {
            /** 1st Valid vector: */
            result = neuron.computeNeuronResult(new double[] {0,0,0,0,0});
            double x = 1.0;
            x = 1+Math.exp(-x);
            x = 1/x;
            assertEquals(result, x);
        } catch (Exception e) {
            fail();
        }
        try {
            /** 2nd Valid vector: */
            result = neuron.computeNeuronResult(new double[] {1,1,1,1,1});
            double x = 1+2+3+4+5+1;
            x = 1+Math.exp(-x);
            x = 1/x;
            assertEquals(result, x);
        } catch (Exception e) {
            fail();
        }
        try {
            /** 3rd Valid vector: */
            result = neuron.computeNeuronResult(new double[] {1,2,3,4,5});
            double x = 1+4+9+16+25+1;
            x = 1+Math.exp(-x);
            x = 1/x;
            assertEquals(result, x);
        } catch (Exception e) {
            fail();
        }
    }
}
