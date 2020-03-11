import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * k Nearest Neighbour classifier
 */
public class ClassifierKNN {
    private int k;
    private List<Observation> trainingObservations;
    private List<Observation> testingObservations;

    public ClassifierKNN(int k, List<Observation> trainingObservations, List<Observation> testingObservations) {
        this.k = k;
        this.trainingObservations = trainingObservations;
        this.testingObservations = testingObservations;
    }

    public void classifyLoop() {
        Scanner sc = new Scanner(System.in);

        System.out.println("> Entered classifying loop, divide dimensions using coma ','; enter 'q' to quit.");
        System.out.println("> Input dimensions:");
        String input = sc.nextLine();

        while (!input.equals("q")) {
            String[] split = input.split(",");
            ArrayList<Double> dimensions = new ArrayList<>();
            for (String s : split) {
                dimensions.add(Double.parseDouble(s));
            }
            try {
                System.out.println(classify(new Observation(dimensions)));
            } catch (Exception ex) {
                System.err.println("> Error occured while classifying.");
            }

            System.out.println("> Input new dimensions:");
            input = sc.nextLine();
        }
    }

    public void classifyTestingSet() {
        System.out.println("> Classifying testing set...");
        for (Observation o : testingObservations) {
            System.out.print(o.getDimensions() + "=");
            System.out.println(classify(o));
        }
        System.out.println("> Testing set classified.");
    }

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

    public void setK(int k) {
        if (k > 0) {
            this.k = k;
        } else {
            System.err.println("> K cannot be less than 1.");
        }
    }

    public List<Observation> getTrainingObservations() {
        return trainingObservations;
    }

    public List<Observation> getTestingObservations() {
        return testingObservations;
    }
}
