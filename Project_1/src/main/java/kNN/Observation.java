package kNN;

import java.util.ArrayList;

/**
 * Class representing an observation
 */
public class Observation {
    private String classification;
    private ArrayList<Double> dimensions;

    public Observation(String classification, ArrayList<Double> dimensions) {
        this.classification = classification;
        this.dimensions = dimensions;
    }

    public Observation(ArrayList<Double> dimensions) {
        this("", dimensions);
    }

    public String getClassification() {
        return classification;
    }

    public ArrayList<Double> getDimensions() {
        return dimensions;
    }
}
