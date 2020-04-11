package languageRecognition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Perceptron network using local architecture
 */
public class Network {
    private double alpha;
    private int K; // number of classes

    private List<Observation> observations;
    private HashMap<String, int[]> names;  // names with codes (ex. "Polish":[0, 1, 0]])
    private Perceptron[] perceptrons; // sorted perceptrons


    public Network(double alpha, List<Observation> observations) {
        this.alpha = alpha;
        this.observations = observations;

        ArrayList<String> namesTmp = new ArrayList<>();

        for (Observation o : observations) {
            if (!namesTmp.contains(o.getName())) {
                namesTmp.add(o.getName());
            }
        }

        K = namesTmp.size();
        names = new HashMap<>();
        perceptrons = new Perceptron[K];

        for (int i = 0; i < K; i++) {
            // create a "code" - array is implicitly filled with 0s, so when we create new one and assign 1 to
            // element with index [i], for K=3 and i starting at 0 it will be {1,0,0}, {0,1,0}, {0,0,1}
            int[] code = new int[K];
            code[i] = 1;

            // assigning the unique code to a name
            names.put(namesTmp.get(i), code);

            // creating new perceptron passing alpha, number of dimensions and name for which it will train itself
            // (it should return 1 only for this name)
            perceptrons[i] = new Perceptron(alpha, observations.get(0).getVector().length, namesTmp.get(i));
        }
    }

    public void learn(int iterations) {
        for (int i = 0; i < iterations; i++) {
            for (Observation o : observations) {
                for (Perceptron p : perceptrons) {
                    p.learn(o);
                }
            }
        }
    }

    public String classify(Observation o) {
        int[] code = new int[K];
        for (int i = 0; i < K; i++) {
            code[i] = perceptrons[i].calcNet(o);
        }
        for (String s : names.keySet()) {
            if (Arrays.equals(names.get(s), code)) {
                return s;
            }
        }

        return "None " + Arrays.toString(code);
    }
}
