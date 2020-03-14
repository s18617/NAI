import kNN.ClassifierKNN;
import kNN.Observation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Adam Woytowicz s18617
 */
public class Main {
    public static void main(String[] args) {
        int k = 1;  // hiperparametr k-NN (k najbliższych obserwacji do porównania)
        String trainSetPath = null;
        String testSetPath = null;

        if (args.length == 3) {
            k = Integer.parseInt(args[0]);
            trainSetPath = args[1];
            testSetPath = args[2];
        } else {
            try (Scanner sc = new Scanner(System.in)) {
                System.err.println("> Missing program arguments. " +
                        "Please enter the following: k, training set path, testing set path.");
                k = sc.nextInt();
                trainSetPath = sc.nextLine();
                testSetPath = sc.nextLine();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }

        File trainSetFile = new File(trainSetPath);
        File testSetFile = new File(testSetPath);

        if (!trainSetFile.isFile()) {
            System.err.println("> Train set does not exist or is not a file.");
            System.exit(1);
        }
        if (!testSetFile.isFile()) {
            System.err.println("> Test set does not exist or is not a file.");
            System.exit(1);
        }

        // Loading
        List<Observation> trainingObservations = new ArrayList<>();
        loadSet(trainSetFile, trainingObservations);

        List<Observation> testingObservations = new ArrayList<>();
        loadSet(testSetFile, testingObservations);


        ClassifierKNN classifier = new ClassifierKNN(k, trainingObservations, testingObservations);
        classifier.classifyTestingSetLoop();
        classifier.classifyLoop();
    }

    private static void loadSet(File setFile, List<Observation> observations) {
        List<String[]> setLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(setFile))) {
            String line = br.readLine();
            while (line != null) {
                setLines.add(line.split(","));
                line = br.readLine();
            }
        } catch (IOException ex) {
            System.err.println("> An error occured while reading files.");
            ex.printStackTrace();
            System.exit(1);
        }

        for (String[] line : setLines) {
            ArrayList<Double> dimensions = new ArrayList<>();
            for (int i = 0; i < line.length - 1; i++) {
                dimensions.add(Double.parseDouble(line[i]));
            }
            observations.add(new Observation(line[line.length - 1], dimensions));
        }
    }
}
