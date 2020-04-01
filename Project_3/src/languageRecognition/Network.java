package languageRecognition;

import java.util.HashMap;
import java.util.List;

/**
 * Perceptron network using TODO global (reusing perceptrons) / local (unique perceptrons)
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
        globalnie:
        "kod" dla kazdej klasy bedzie po kolei przydzielany jako liczby binare, np:
        2 perceptrony
        0. polish = 00 = [0, 0] = 0
        1. english = 01 = [0, 1] = 1
        2. italian = 10 = [1, 0] = 2

        lokalnie:
        3 perceptrony
        0. polish = [1, 0, 0]
        1. english = [0, 1, 0]
        2. italian = [0, 0, 1]
         */
    }
}
