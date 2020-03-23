import Perceptron.Observation;
import Perceptron.Perceptron;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Adam Woytowicz s18617
 */
public class Main {
    public static void main(String[] args) {
        final double alpha;
        final Path trainSetPath;
        final Path testSetPath;

        // initializing constants
        if (args.length == 3) {
            alpha = Float.parseFloat(args[0]);
            trainSetPath = Paths.get(args[1]);
            testSetPath = Paths.get(args[2]);
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.println("> Enter alpha:");
            alpha = sc.nextFloat();
            System.out.println("> Enter training set path:");
            trainSetPath = Paths.get(sc.nextLine());
            System.out.println("> Enter testing set path:");
            testSetPath = Paths.get(sc.nextLine());
        }

        // checking paths
        if (!Files.isRegularFile(trainSetPath)) {
            System.err.println("> Train set does not exist or is not a file.");
            System.exit(-1);
        }
        if (!Files.isRegularFile(testSetPath)) {
            System.err.println("> Test set does not exist or is not a file.");
            System.exit(-1);
        }

        // loading files
        List<Observation> trainSet = new ArrayList<>();
        loadCsv(trainSetPath, trainSet);
        List<Observation> testSet = new ArrayList<>();
        loadCsv(testSetPath, testSet);

        Perceptron perceptron = new Perceptron(alpha, trainSet, testSet);

        classifyLoop(perceptron);
    }

    private static void loadCsv(Path filepath, List<Observation> observations) {
        try {
            Files.lines(filepath).forEach(line -> {
                String[] split = line.split(",");
                double[] dimensions = new double[split.length - 1];
                for (int i = 0; i < split.length - 1; i++) {
                    dimensions[i] = Double.parseDouble(split[i]);
                }
                observations.add(new Observation(split[split.length - 1], dimensions));
            });
        } catch (IOException ex) {
            System.err.println("> An error occured while reading '" + filepath.getFileName() + "'.");
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    private static void classifyLoop(Perceptron perceptron) {
        Scanner sc = new Scanner(System.in);

        System.out.println("> Entered classyfing loop, divide dimensions using coma ',', enter 'q' to quit.");
        System.out.println("> Input dimensions:");
        String input = sc.nextLine();

        while (!input.equals("q")) {
            String[] split = input.split(",");
            double[] dimensions = new double[split.length];
            try {
                for (int i = 0; i < split.length; i++) {
                    dimensions[i] = Double.parseDouble(split[i].trim());
                }
            } catch (NumberFormatException ex) {
                System.err.println("> An error occured while converting input.");
            }

            try {
                Observation o = new Observation(dimensions);
                System.out.println("> Classification: " + perceptron.classify(o));
            } catch (Exception ex) {
                System.err.println("> En error occured while converting input.");
                ex.printStackTrace();
            }

            System.out.println("> Input dimensions:");
            input = sc.nextLine();
        }

        sc.close();
    }
}
