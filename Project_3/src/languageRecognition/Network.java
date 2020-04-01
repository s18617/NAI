package languageRecognition;

import java.util.HashMap;
import java.util.List;

/**
 * Perceptron network using local architecture
 */
public class Network {
    private double alpha;

    private List<Text> texts;

    private HashMap<String, int[]> languageCodes; // languages with codes (ex. "Polish":[0, 1]])
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
    }
}
