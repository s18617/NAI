import k_means.Vector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // args
        double k = 1;
        String datasetPath = "";

        if (args.length == 2) {
            k = Double.parseDouble(args[0]);
            datasetPath = args[1];
        } else {
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("> Enter k:");
                k = sc.nextDouble();
                System.out.println("> Enter dataset path:");
                datasetPath = sc.next();
            } catch (Exception ex) {
                System.err.println("> Error occured while initializing");
                ex.printStackTrace();
                System.exit(-1);
            }
        }

        List<Vector> vectors = loadFromCsv(datasetPath);
    }

    private static List<Vector> loadFromCsv(String filepath) {
        List<Vector> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");

                double[] vector = new double[split.length];
                for (int i = 0; i < split.length; i++) {
                    vector[i] = Double.parseDouble(split[i]);
                }

                list.add(new Vector(vector));
            }
        } catch (IOException ex) {
            System.err.println("> Error occured while loading a file");
            ex.printStackTrace();
            System.exit(-1);
        }

        return list;
    }
}
