package Perceptron;

import java.util.Arrays;

/**
 * Class representing an observation
 *
 * @author Adam Woytowicz s18617
 */
public class Observation {
    private String classification;
    private double[] dimensions;

    public Observation(String classification, double[] dimensions) {
        this.classification = classification;
        this.dimensions = dimensions;
    }

    public Observation(double[] dimensions) {
        this("", dimensions);
    }

    public String getClassification() {
        return classification;
    }

    public double[] getDimensions() {
        return dimensions;
    }

    @Override
    public String toString() {
        return "dimensions=" + Arrays.toString(dimensions)
                + ", classification='" + classification + '\'';
    }
}
