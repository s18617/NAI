package naive_bayes_classifier;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class Classifier {
    private HashMap<String, Integer>[][] valueCounts; // [classificationIndex][columnIndex]
    private int totalCounts[]; // [classificationIndex]
    private int totalCount;

    private ArrayList<String> classifications = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public Classifier(List<Pair<String[], String>> observations) {
        final int columns = observations.get(0).getKey().length;


        // classifications
        classifications.add(0, observations.get(0).getValue());
        for (Pair<String[], String> observation : observations) {
            if (!classifications.contains(observation.getValue())) {
                classifications.add(classifications.size(), observation.getValue());
            }
        }


        // value counts
        valueCounts = new HashMap[classifications.size()][];
        for (int i = 0; i < valueCounts.length; i++) {
            valueCounts[i] = new HashMap[columns];
            for (int j = 0; j < valueCounts[i].length; j++) {
                valueCounts[i][j] = new HashMap<>();
            }
        }


        // counting
        totalCounts = new int[classifications.size()];
        for (Pair<String[], String> observation : observations) {
            // i - column index
            for (int i = 0; i < observation.getKey().length; i++) {
                // j - classification index
                for (int j = 0; j < classifications.size(); j++) {
                    if (classifications.get(j).equals(observation.getValue())) {
                        String key = observation.getKey()[i];
                        int count = valueCounts[j][i].getOrDefault(key, 0);
                        valueCounts[j][i].put(key, count + 1);
                        totalCounts[j]++;
                    }
                }
            }
        }

        // totalCount
        totalCount = IntStream.of(totalCounts).sum();
    }

    public void classify(String[] observation) {

    }

    public void test(List<String[]> observations) {

    }

    private void printValueCounts() {
        for (int i = 0; i < classifications.size(); i++) {
            System.out.println("<<< " + classifications.get(i) + " >>>");
            for (int j = 0; j < valueCounts[i].length; j++) {
                System.out.println("< col " + j + " >");
                for (String key : valueCounts[i][j].keySet()) {
                    System.out.println(key + " : " + valueCounts[i][j].get(key));
                }
            }
        }
    }
}
