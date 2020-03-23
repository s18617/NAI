package Perceptron;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Adam Woytowicz s18617
 */
public class Perceptron {
    // params
    private double alpha;
    private double t = ThreadLocalRandom.current().nextDouble(0, 1 + 1); // theta
    private double[] w; // weights

    private String[] classes = new String[2];

    // observations
    private List<Observation> trainObserv;
    private List<Observation> testObserv;


    public Perceptron(double alpha, List<Observation> trainObserv, List<Observation> testObserv) {
        this.alpha = alpha;
        this.trainObserv = trainObserv;
        this.testObserv = testObserv;

        w = new double[trainObserv.get(0).getDimensions().length];
        Arrays.fill(w, 0);

        classes[0] = trainObserv.get(0).getClassification();

        for (int i = trainObserv.size() - 1; i > 0; i--) {
            if (!trainObserv.get(i).getClassification().equals(classes[0])) {
                classes[1] = trainObserv.get(i).getClassification();
                break;
            }
        }

        train();
        test();
    }

    public int calcNet(Observation o) {
        // net = x*w1 + y*w2 >= t
        double tmp = 0.0;
        for (int i = 0; i < o.getDimensions().length; i++) {
            tmp += w[i] * o.getDimensions()[i];
        }

        return (tmp >= this.t) ? 1 : 0;
    }

    public String classify(Observation o) {
        return classes[calcNet(o)];
    }


    private void learn(Observation o) {
        int y = calcNet(o); // real decision

        if (!classes[y].equals(o.getClassification())) {
            double[] _w = new double[w.length]; // [i] = w[i] + (d - y) * alpha * x[i]
            int d = (classes[0].equals(o.getClassification())) ? 0 : 1; // correct decision

            // w prim
            for (int i = 0; i < w.length; i++) {
                _w[i] = w[i] + (d - y) * alpha * o.getDimensions()[i];
            }
            w = _w;

            // t prim
            t = t + (d - y) * alpha * (-1);
        }
    }

    private void train() {
        for (Observation o : trainObserv) {
            learn(o);
        }
    }

    private void test() {
        for (Observation o : testObserv) {
            System.out.println(o + " : " + classify(o));
        }
    }
}
