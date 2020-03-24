package kNN;

/**
 * Class storing distance between testing observation and training observation
 */
public class Distance {
    private String classification;
    private double distance;

    public Distance(String classification, double distance) {
        this.classification = classification;
        this.distance = distance;
    }

    public String getClassification() {
        return classification;
    }

    public double getDistance() {
        return distance;
    }
}
