package Perceptron;

import java.util.Arrays;
import java.util.List;

/**
 * @author Adam Woytowicz s18617
 */
public class Perceptron {
    // params
    private double alpha;
    private double t = 50; // theta
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
        Arrays.fill(w, -0.8);

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
        for (int i = 0; i < 10; i++) {
            for (Observation o : trainObserv) {
                learn(o);
            }
        }
    }

    private void test() {
        double accuracy = 0;

        for (Observation o : testObserv) {
            String classification = classify(o);
            System.out.println(o + " : " + classification);
            if (o.getClassification().equals(classification)) {
                accuracy++;
            }
        }

        accuracy = accuracy / testObserv.size();
        System.out.println("> Accuracy = " + accuracy);
        System.out.println("> w = " + Arrays.toString(w));
        System.out.println("> t = " + t);
    }
}
