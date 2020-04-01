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

    private List<Text> texts;
    private HashMap<String, int[]> languages;  // languages with codes (ex. "Polish":[0, 1, 0]])
    private Perceptron[] perceptrons; // sorted perceptrons


    public Network(double alpha, List<Text> texts) {
        this.alpha = alpha;
        this.texts = texts;

        /*
        lokalnie:
        3 perceptrony
        0. polish = [1, 0, 0]
        1. english = [0, 1, 0]
        2. italian = [0, 0, 1]
         */

        ArrayList<String> languagesTmp = new ArrayList<>();

        for (Text text : texts) {
            if (!languagesTmp.contains(text.getName())) {
                languagesTmp.add(text.getName());
            }
        }

        K = languagesTmp.size();
        languages = new HashMap<>();
        perceptrons = new Perceptron[K];

        for (int i = 0; i < K; i++) {
            int[] code = new int[K];
            code[i] = 1;
            languages.put(languagesTmp.get(i), code);
            perceptrons[i] = new Perceptron(alpha, languagesTmp.get(i));
        }
    }

    public void learn(int iterations) {

    }
}
