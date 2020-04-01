package languageRecognition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Perceptron network using local architecture
 */
public class Network {
    private double alpha;
    private int K; // number of classes

    private List<Observation> observations;
    private HashMap<String, int[]> languages;  // languages with codes (ex. "Polish":[0, 1, 0]])
    private Perceptron[] perceptrons; // sorted perceptrons


    public Network(double alpha, List<Observation> observations) {
        this.alpha = alpha;
        this.observations = observations;

        /*
        lokalnie:
        3 perceptrony
        0. polish = [1, 0, 0]
        1. english = [0, 1, 0]
        2. italian = [0, 0, 1]
         */

        ArrayList<String> languagesTmp = new ArrayList<>();

        for (Observation o : observations) {
            if (!languagesTmp.contains(o.getName())) {
                languagesTmp.add(o.getName());
            }
        }

        K = languagesTmp.size();
        languages = new HashMap<>();
        perceptrons = new Perceptron[K];

        for (int i = 0; i < K; i++) {
            int[] code = new int[K];
            code[i] = 1;
            languages.put(languagesTmp.get(i), code);
            perceptrons[i] = new Perceptron(alpha, observations.get(0).getVector().length, languagesTmp.get(i));
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
}
