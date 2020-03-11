import java.util.ArrayList;
import java.util.List;

/**
 * k Nearest Neighbour classifier
 */
public class ClassifierKNN {
    private int k;
    private List<Observation> trainingObservations;
    private List<Observation> testingObservations;

    public ClassifierKNN(int k, List<Observation> trainingObservations, List<Observation> testingObservations) {
        this.k = k;
        this.trainingObservations = trainingObservations;
        this.testingObservations = testingObservations;
    }

    /**
     * Finds the distance between two dimensions (Euclidean distance)
     */
    private double getDistanceBetween(ArrayList<Double> dim1, ArrayList<Double> dim2) {
        // sqrt(    (x1 - x2)^2 + (y1 - y2)^2 + (z1 - z2)^2 + ...   )
        if (dim1.size() == dim2.size()) {
            double distance = 0;

            for (int i = 0; i < dim1.size(); i++) {
                distance += Math.pow(dim1.get(i) - dim2.get(i), 2);
            }

            return Math.sqrt(distance);
        } else {
            System.err.println("Different number of arguments!");
            return -1;
        }
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        if (k > 0) {
            this.k = k;
        } else {
            System.err.println("K cannot be less than 1.");
        }
    }

    public List<Observation> getTrainingObservations() {
        return trainingObservations;
    }

    public List<Observation> getTestingObservations() {
        return testingObservations;
    }
}
