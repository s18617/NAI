package Perceptron;

import java.util.Arrays;

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
