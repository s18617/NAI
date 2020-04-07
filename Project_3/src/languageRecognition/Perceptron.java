package languageRecognition;

import java.util.Arrays;

public class Perceptron {
    private double alpha;
    private double t; // theta
    private double[] w; // weights

    private String name; // language 'set' for this perceptron

    public Perceptron(double alpha, int paramsCount, String language) {
        this.alpha = alpha;
        this.w = new double[paramsCount];
        this.name = language;
    }

    public int calcNet(Observation o) {
        // net = x*w1 + y*w2 >= t
        double tmp = 0.0;
        for (int i = 0; i < o.getVector().length; i++) {
            tmp += w[i] * o.getVector()[i];
        }
        return (tmp >= this.t) ? 1 : 0;
    }

    /**
     * w[i] = w[i] + (d - y) * alpha * x[i]
     * y = 1 only when observation's name equals perceptron's name
     *
     * @param o observation to learn on
     */
    public void learn(Observation o) {
        int y = calcNet(o); // real decision
        int d = (o.getName().equals(name)) ? 1 : 0;

        if ((y == 0 && o.getName().equals(name)) || (y == 1 && !o.getName().equals(name))) {
            // w prim
            for (int i = 0; i < w.length; i++) {
                w[i] = w[i] + (d - y) * alpha * o.getVector()[i];
            }

            // t prim
            t = t + (d - y) * alpha * (-1);
        }
    }

    @Override
    public String toString() {
        return "Perceptron{" +
                "alpha=" + alpha +
                ", t=" + t +
                ", w=" + Arrays.toString(w) +
                ", language='" + name + '\'' +
                '}';
    }
}
