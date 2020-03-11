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
        int k = 3;  // hiperparametr k-NN (k najbliższych obserwacji do porównania) z domyślną wartością 3
        String trainSetPath = null;
        String testSetPath = null;

        if (args.length == 3) {
            k = Integer.parseInt(args[0]);
            trainSetPath = args[1];
            testSetPath = args[2];
        } else {
            try (Scanner sc = new Scanner(System.in)) {
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
            System.err.println("Train set does not exist or is not a file.");
            System.exit(1);
        }
        if (!testSetFile.isFile()) {
            System.err.println("Test set does not exist or is not a file.");
            System.exit(1);
        }

        List<String[]> trainSetLines = new ArrayList<>();  // (x1, x2, ..., xn, result)
        List<String[]> testSetLines = new ArrayList<>();  // (x1, x2, ..., xn)
        try (BufferedReader trainSetReader = new BufferedReader(new FileReader(trainSetFile));
             BufferedReader testSetReader = new BufferedReader(new FileReader(testSetFile))) {
            String line = trainSetReader.readLine();
            while (line != null) {
                trainSetLines.add(line.split(","));
                line = trainSetReader.readLine();
            }
            line = testSetReader.readLine();
            while (line != null) {
                testSetLines.add(line.split(","));
                line = testSetReader.readLine();
            }
        } catch (IOException ex) {
            System.err.println("An error occured while reading files.");
            ex.printStackTrace();
            System.exit(1);
        }

        // Training set
        List<Observation> trainingObservations = new ArrayList<>();
        for (String[] line : trainSetLines) {
            ArrayList<Double> dimensions = new ArrayList<>();
            for (int i = 0; i < line.length - 1; i++) {
                dimensions.add(Double.parseDouble(line[i]));
            }
            trainingObservations.add(new Observation(line[line.length - 1], dimensions));
        }

        // Testing set
        List<Observation> testingObservations = new ArrayList<>();
        for (String[] line : testSetLines) {
            ArrayList<Double> dimensions = new ArrayList<>();
            for (int i = 0; i < line.length; i++) {
                dimensions.add(Double.parseDouble(line[i]));
            }
            testingObservations.add(new Observation(dimensions));
        }

        ClassifierKNN classifier = new ClassifierKNN(k, trainingObservations, testingObservations);
    }
}
