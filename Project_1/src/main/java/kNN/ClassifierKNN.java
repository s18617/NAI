package kNN;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * k Nearest Neighbour classifier
 */
public class ClassifierKNN {
    private int k;
    private List<Observation> trainingObservations;
    private List<Observation> testingObservations;

    /**
     * @param k                    number of nearest neighbours to take into consideration in classification
     * @param trainingObservations list of training observations
     * @param testingObservations  list of testing observations
     */
    public ClassifierKNN(int k, List<Observation> trainingObservations, List<Observation> testingObservations) {
        this.k = k;
        this.trainingObservations = trainingObservations;
        this.testingObservations = testingObservations;
    }

    /**
     * Enteres a loop, where user can classify his single observation
     */
    public void classifyLoop() {
        Scanner sc = new Scanner(System.in);

        System.out.println("> Entered classifying loop, divide dimensions using coma ','; enter 'q' to quit.");
        System.out.println("> Input dimensions:");
        String input = sc.nextLine();

        while (!input.equals("q")) {
            String[] split = input.split(",");
            ArrayList<Double> dimensions = new ArrayList<>();
            try {
                for (String s : split) {
                    dimensions.add(Double.parseDouble(s.trim()));
                }
                try {
                    System.out.println(classify(new Observation(dimensions)));
                } catch (Exception ex) {
                    System.err.println("> Error occured while classifying.");
                }
            } catch (NumberFormatException ex) {
                System.err.println("> Error occured while converting input.");
            }

            System.out.println("> Input new dimensions:");
            input = sc.nextLine();
        }
    }

    /**
     * Enters a loop, where user can choose to classify list of testing observations with different k
     */
    public void classifyTestingSetLoop() {
        final int K_BACKUP = getK();

        classifyTestingSet();
        chartUserQuestion();
        System.out.println("> Do you want to test the testing set for different k? (y/n): ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();

        if (choice.equals("y")) {
            System.out.println("> Entered k loop, enter 'q' to quit.");
            System.out.println("> Input new k:");
            String input = sc.nextLine();
            while (!input.equals("q")) {
                try {
                    try {
                        setK(Integer.parseInt(input));
                        classifyTestingSet();
                    } catch (NumberFormatException ex) {
                        System.err.println("> Error occured while converting input.");
                    }
                } catch (IllegalArgumentException ex) {
                    System.err.println(ex.getMessage());
                }
                System.out.println("> Input new k:");
                input = sc.nextLine();
            }
        } else {
            System.out.println("> Ignored k loop.");
        }

        setK(K_BACKUP);
    }

    /**
     * Classifies list of testing observations
     */
    public double classifyTestingSet() {
        int counter = 0;

        System.out.println("> Classifying testing set...");
        for (Observation o : testingObservations) {
            String c = classify(o);  // saved to not classify two times
            System.out.println(o.getDimensions() + "=" + c + " [Correct: " + o.getClassification() + "]");
            if (c.equals(o.getClassification())) {
                counter++;
            }
        }

        double accuracy = (counter * 1.0) / testingObservations.size();
        System.out.println("> Overall classification accuracy for k=" + k + " is "
                + Math.round(accuracy * 10000.00) / 10000.00);

        return accuracy;
    }

    /**
     * Classifies single observation
     *
     * @param o observation to classify
     * @return classification name
     */
    public String classify(Observation o) {
        ArrayList<Distance> distances = new ArrayList<>();
        for (Observation tO : trainingObservations) {
            distances.add(
                    new Distance(tO.getClassification(), getDistanceBetween(o.getDimensions(), tO.getDimensions()))
            );
        }

        distances.sort((o1, o2) -> {
            if (o1.getDistance() > o2.getDistance()) {
                return 1;
            }
            if (o1.getDistance() == o2.getDistance()) { // random if they're equal
                return ThreadLocalRandom.current().nextInt(-1, 1 + 1);
            }
            // if o1.getDistance() < o2.getDistance()
            return -1;
        });


        HashMap<String, Integer> count = new HashMap<>();
        for (Observation observation : trainingObservations) {
            count.put(observation.getClassification(), 0);
        }

        for (int i = 0; i < k; i++) {
            String tmp = distances.get(i).getClassification();
            count.put(tmp, count.get(tmp) + 1);
        }

        String max = "";
        for (String key : count.keySet()) {
            if (max.isEmpty()) {
                max = key;
            } else {
                if (count.get(key) > count.get(max)) {
                    max = key;
                }
            }
        }

        return max;
    }

    /**
     * Finds the distance between two dimensions (Euclidean distance)
     * sqrt(    (x1 - x2)^2 + (y1 - y2)^2 + (z1 - z2)^2 + ...   )
     */
    private double getDistanceBetween(ArrayList<Double> dim1, ArrayList<Double> dim2) {
        if (dim1.size() == dim2.size()) {
            double distance = 0;

            for (int i = 0; i < dim1.size(); i++) {
                distance += Math.pow(dim1.get(i) - dim2.get(i), 2);
            }

            return Math.sqrt(distance);
        } else {
            System.err.println("> Different number of arguments between dimensions.");
            return -1;
        }
    }

    public int getK() {
        return k;
    }

    /**
     * @param k new k
     * @throws IllegalArgumentException if new k is lower than 1
     */
    public void setK(int k) {
        if (k > 0) {
            this.k = k;
        } else {
            throw new IllegalArgumentException("> K cannot be less than 1.");
        }
    }

    /**
     * Method creating JPEG image of an accuracy chart
     * Made using JFreeChart http://www.jfree.org/jfreechart/
     * Made with help of tutorialspoint.com https://www.tutorialspoint.com/jfreechart/jfreechart_line_chart.htm
     */
    public void generateChart(String path, int width, int height) {
        final int size = testingObservations.size();
        DefaultCategoryDataset lineChartDataset = new DefaultCategoryDataset();

        for (int i = 1; i <= size; i++) {
            final int K_BACKUP = getK();
            setK(i);
            double acc = classifyTestingSet();
            lineChartDataset.addValue(acc, "accuracy", Integer.toString(i));
            setK(K_BACKUP);
        }

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Accuracy", "k", "accuracy",
                lineChartDataset, PlotOrientation.VERTICAL,
                false, false, false
        );

        File lineChart = new File(path);
        try {
            ChartUtils.saveChartAsJPEG(lineChart, lineChartObject, width, height);
        } catch (IOException ex) {
            System.err.println("> Error occured while saving a chart.");
        }
    }

    public void chartUserQuestion() {
        Scanner sc = new Scanner(System.in);
        System.out.println("> Do you want to generate JPEG accuracy chart? (y/n):");
        String answer = sc.nextLine();

        if (answer.equals("y")) {
            try {
                System.out.println("> Enter path (chart.jpg):");
                String path = sc.nextLine();
                System.out.println("> Enter width (1920):");
                int width = sc.nextInt();
                System.out.println("> Enter height (1080):");
                int height = sc.nextInt();

                generateChart(path, width, height);
            } catch (NumberFormatException ex) {
                System.err.println("> Error occured while processing arguments.");
            }
        }
    }

    public List<Observation> getTrainingObservations() {
        return trainingObservations;
    }

    public List<Observation> getTestingObservations() {
        return testingObservations;
    }
}
