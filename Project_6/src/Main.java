import javafx.util.Pair;
import naive_bayes_classifier.Classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Pair<String[], String>> observations = null;

        if (args.length == 1) {
            observations = readCsvTraining(args[0]);
        } else {
            System.out.println("> Enter dataset path:");
            try (Scanner scanner = new Scanner(System.in)) {
                observations = readCsvTraining(scanner.nextLine().trim());
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        }

        Classifier classifier = new Classifier(observations);
    }

    private static List<Pair<String[], String>> readCsvTraining(String pathString) {
        List<Pair<String[], String>> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathString))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                String[] vector = Arrays.copyOfRange(split, 0, split.length - 1);
                String classification = split[split.length - 1];
                list.add(new Pair<>(vector, classification));
            }
        } catch (IOException ex) {
            System.err.println("> Error occurred while loading a file");
            ex.printStackTrace();
            System.exit(-1);
        }

        return list;
    }
    private static List<String[]> readCsvTest(String pathString){
        // TODO
        return null;
    }
}
