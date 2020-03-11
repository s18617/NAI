package kNN;

import java.util.ArrayList;

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

    public boolean isTraining() {
        return classification.isEmpty();
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        if (this.classification.isEmpty()) {
            this.classification = classification;
        } else {
            System.out.println("This observation is already classified (" + this.classification + ")");
        }
    }

    public ArrayList<Double> getDimensions() {
        return dimensions;
    }
}
