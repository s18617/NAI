package naive_bayes_classifier;

public class Observation {
    private final String[] vector;
    private final String classification;


    public Observation(String[] vector, String classification) {
        this.vector = vector;
        this.classification = classification;
    }
}
