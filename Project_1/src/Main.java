import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int k;  // hiperparametr k-NN
        String trainSet = null;
        String testSet = null;

        if (args.length == 3) {
            k = Integer.parseInt(args[0]);
            trainSet = args[1];
            testSet = args[2];
        } else {
            try (Scanner sc = new Scanner(System.in)) {
                k = sc.nextInt();
                trainSet = sc.nextLine();
                testSet = sc.nextLine();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }

        File trainSetFile = new File(trainSet);
        File testSetFile = new File(testSet);

        if (!trainSetFile.isFile()) {
            System.err.println("Train set does not exist or is not a file.");
            System.exit(1);
        }
        if (!testSetFile.isFile()) {
            System.err.println("Test set does not exist or is not a file.");
            System.exit(1);
        }

        List<String[]> trainSetLines = new ArrayList<>();
        List<String[]> testSetLines = new ArrayList<>();
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

        // TODO do something with the fucking data
    }
}
