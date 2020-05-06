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
        List<Pair<String[], String>> trainingSet = null;
        List<String[]> testSet = null;

        if (args.length == 1) {
            trainingSet = readCsvTraining(args[0]);
        } else if (args.length == 2) {
            trainingSet = readCsvTraining(args[0]);
            testSet = readCsvTest(args[1]);
        } else {
            System.out.println("> Enter dataset path:");
            try (Scanner scanner = new Scanner(System.in)) {
                trainingSet = readCsvTraining(scanner.nextLine().trim());
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        }

        Classifier classifier = new Classifier(trainingSet);
        classifier.printValueCounts();
        if (testSet != null) {
            classifier.test(testSet);
        }
        classificationLoop(classifier);
    }

    private static void classificationLoop(Classifier classifier) {
        Scanner sc = new Scanner(System.in);
        System.out.println("> Enter values split with comas ('q' to quit)");
        String userInput = sc.nextLine();
        while (!userInput.equals("q")) {
            String[] split = userInput.split(",");
            System.out.println("< '" + classifier.classify(split) + "'");
            System.out.println("> Enter values split with comas ('q' to quit)");
            userInput = sc.nextLine();
        }
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
            System.err.println("> Error occurred while loading training set");
            ex.printStackTrace();
            System.exit(-1);
        }

        return list;
    }

    private static List<String[]> readCsvTest(String pathString) {
        List<String[]> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathString))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                list.add(split);
            }
        } catch (IOException ex) {
            System.err.println("> Error occurred while loading testing set");
            ex.printStackTrace();
            System.exit(-1);
        }

        return list;
    }
}
