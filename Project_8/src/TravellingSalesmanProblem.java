import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TravellingSalesmanProblem {
    int numberOfCities;
    double[][] distances;

    public TravellingSalesmanProblem(int numberOfCities, double[][] distances) {
        this.numberOfCities = numberOfCities;
        this.distances = distances;
    }

    public int[] hillClimbing() {
        // time
        long start = System.currentTimeMillis();

        int[] bestTour = getRandomTour();
        double bestScore = getScore(bestTour);

        return null; // TODO return
    }

    // assisting methods
    public int[] getRandomTour() {
        int[] tour = new int[numberOfCities];

        for (int i = 0; i < numberOfCities; i++)
            tour[i] = i;

        Random rand = ThreadLocalRandom.current();
        for (int i = tour.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            // swap
            int tmp = tour[index];
            tour[index] = tour[i];
            tour[i] = tmp;
        }

        return tour;
    }

    public double getScore(int[] tour) {
        double score = 0;

        for (int i = 0; i < tour.length - 1; i++)
            score += distances[i][i + 1];

        return score;
    }


    public static TravellingSalesmanProblem loadFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int numberOfCities = Integer.parseInt(br.readLine().trim());
            double[][] distances = new double[numberOfCities][numberOfCities];

            for (int i = 0; i < numberOfCities; i++) {
                String[] split = br.readLine().split(" ", numberOfCities);
                for (int j = 0; j < numberOfCities; j++)
                    distances[i][j] = Double.parseDouble(split[j].trim());
            }

            return new TravellingSalesmanProblem(numberOfCities, distances);
        } catch (IOException ex) {
            System.err.println("> Error occurred while loading from file");
            ex.printStackTrace();
        }

        return null;
    }
}
