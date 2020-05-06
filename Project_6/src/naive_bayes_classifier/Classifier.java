package naive_bayes_classifier;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class Classifier {
    private final HashMap<String, Integer>[] valueCounts1;
    private final HashMap<String, Integer>[] valueCounts2;
    private int totalCount1 = 0; // number of observations classified as classification1
    private int totalCount2 = 0; // number of observations classified as classification2
    private int totalCount; // number of observations classified as classification1 or classification2

    private String classification1;
    private String classification2;

    @SuppressWarnings("unchecked")
    public Classifier(List<Pair<String[], String>> observations) {

        // classifications
        classification1 = observations.get(0).getValue();
        for (int i = observations.size() - 1; i > 0; i--) {
            if (!observations.get(i).getValue().equals(classification1)) {
                classification2 = observations.get(i).getValue();
                break;
            }
        }


        // value counts
        valueCounts1 = new HashMap[observations.get(0).getKey().length];
        valueCounts2 = new HashMap[observations.get(0).getKey().length];
        for (int i = 0; i < valueCounts1.length; i++) {
            valueCounts1[i] = new HashMap<>();
            valueCounts2[i] = new HashMap<>();
        }

        // counting
        for (int i = 0; i < observations.size(); i++) {
            for (int j = 0; j < observations.get(i).getKey().length; j++) {
                if (observations.get(i).getValue().equals(classification1)) {
                    String key = observations.get(i).getKey()[j];
                    int count = valueCounts1[j].getOrDefault(key, 0);
                    valueCounts1[j].put(key, count + 1);
                    totalCount1++;
                } else {
                    String key = observations.get(i).getKey()[j];
                    int count = valueCounts2[j].getOrDefault(key, 0);
                    valueCounts2[j].put(key, count + 1);
                    totalCount2++;
                }
            }
        }

        // totalCount
        totalCount = totalCount1 + totalCount2;
    }

    public void classify(String[] observation) {
        
    }

    public void test(List<String[]> observations) {

    }
}
