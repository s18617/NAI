package languageRecognition;

public class Perceptron {
    private double alpha;
    private double t; // theta
    private double[] w; // weights

    public Perceptron(double alpha) {
        this.alpha = alpha;
    }
}
