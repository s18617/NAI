import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(5, new double[][]{});
        System.out.println(Arrays.toString(tsp.getRandomTour()));
    }
}
