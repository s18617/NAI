package naive_bayes_classifier;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class Classifier {
    private final HashMap<String, Integer>[][] valueCounts; // [classificationIndex][columnIndex]
    private final int[] totalCounts; // [classificationIndex]
    private final int totalCount;

    private final ArrayList<String> classifications = new ArrayList<>();

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

                        // adding to other classifications
                        for (HashMap<String, Integer>[] valueCount : valueCounts) {
                            valueCount[i].putIfAbsent(key, 0);
                        }
                    }
                }
            }
        }

        // totalCount
        totalCount = IntStream.of(totalCounts).sum();
    }

    public String classify(String[] observation) {
        if (observation.length != valueCounts[0].length) {
            throw new IllegalArgumentException("Wrong number of columns");
        }

        double[] results = new double[classifications.size()];

        // i - classification index
        for (int i = 0; i < results.length; i++) {
            results[i] = (totalCounts[i] * 1.0) / totalCount;
            // j - column index
            for (int j = 0; j < observation.length; j++) {
                String value = observation[j];

                int numerator = valueCounts[i][j].get(value) + 1;
                int denominator = totalCounts[i] + valueCounts[i][j].size();

                results[i] = results[i] * ((numerator * 1.0) / denominator);
            }
        }

        int classificationIndex = 0;
        for (int i = 1; i < results.length; i++) {
            if (results[i] > results[classificationIndex]) {
                classificationIndex = i;
            }
        }

        return classifications.get(classificationIndex);
    }

    public void test(List<String[]> trainingSet) {
        for (String[] observation : trainingSet) {
            System.out.println(Arrays.toString(observation) + " = " + classify(observation));
        }
    }

    public void printValueCounts() {
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
