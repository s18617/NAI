package Perceptron;

import java.util.List;

/**
 * @author Adam Woytowicz s18617
 */
public class Perceptron {
    private double alpha;
    private List<Observation> trainObserv;
    private List<Observation> testObserv;

    /**
     * @param alpha       learning constant
     * @param trainObserv list of training observations
     * @param testObserv  list of testing observations
     */
    public Perceptron(double alpha, List<Observation> trainObserv, List<Observation> testObserv) {
        this.alpha = alpha;
        this.trainObserv = trainObserv;
        this.testObserv = testObserv;
    }
}
