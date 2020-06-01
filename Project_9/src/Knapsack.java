import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Knapsack {
    private final double capacity;
    private final double startingTemp; // ex. 10
    private final double decreaseTemp; // ex. 0.999 (multiplier)
    private final ArrayList<Item> items;

    public Knapsack(double capacity, double startingTemp, double decreaseTemp, ArrayList<Item> items) {
        this.capacity = capacity;
        this.startingTemp = startingTemp;
        this.decreaseTemp = decreaseTemp;
        this.items = items;
    }

    public static Knapsack loadKnapsack(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            ArrayList<Item> items = new ArrayList<>();

            String[] split = br.readLine().split(" ", 4);
            double capacity = Double.parseDouble(split[0]);
            double startingTemp = Double.parseDouble(split[2]);
            double decreaseTemp = Double.parseDouble(split[3]);

            String[] values = br.readLine().split(",");
            String[] weights = br.readLine().split(",");

            if (values.length != weights.length)
                throw new Exception("> Invalid file format");

            for (int i = 0; i < values.length; i++) {
                double value = Double.parseDouble(values[i]);
                double weight = Double.parseDouble(weights[i]);
                items.add(i, new Item(value, weight));
            }

            return new Knapsack(capacity, startingTemp, decreaseTemp, items);

        } catch (IOException ex) {
            System.err.println("> Error occurred while reading a file containing knapsack info");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

    public byte[] bruteForce() {
        // timing
        long start = System.currentTimeMillis();

        byte[] bestSelection = null;
        double bestValue = -1;

        for (int i = 0; i < Math.pow(2, this.items.size()); i++) {
            // generating selection
            String[] bin = Integer.toBinaryString(i).split("");
            byte[] selection = new byte[this.items.size()];
            for (int j = bin.length - 1; j >= 0; j--)
                selection[j + (selection.length - bin.length)] = Byte.parseByte(bin[j]);

            // checking selection
            double value = 0;
            double weight = 0;

            for (int j = 0; j < selection.length; j++) {
                value += items.get(j).getValue();
                weight += items.get(j).getWeight();
            }

            if (weight <= capacity && value > bestValue) {
                bestValue = value;
                bestSelection = selection;
                System.out.println("New best selection " + Arrays.toString(bestSelection));
            }
        }

        // timing
        long msElapsed = System.currentTimeMillis() - start;
        System.out.println("Checked all the possible selections in " + (msElapsed / 1000.0) + " s, or " + msElapsed + " ms");

        return bestSelection;
    }

    public byte[] simulatedAnnealing() {
        final double absoluteTemp = 0.00001;
        /*
        funkcja wyrażająca prawdopodobieństwo przejścia do gorszego sąsiada:
        P(curr, candidate, f, temp) = e^(-( |f(curr) - f(candidate)|/temp )) gdzie f: funkcja celu
         */

        // timing
        long start = System.currentTimeMillis();

        double currentTemp = startingTemp;
        while (currentTemp > absoluteTemp) {
            // TODO
            currentTemp *= decreaseTemp;
        }

        return null; // TODO return something you moron
    }

    private double countProbability(double currentValue, double candidateValue, double currentTemp) {
        return Math.exp((-1) * ((currentValue - candidateValue) / currentTemp)); // TODO replace values with energies
    }

    /**
     * 1. If there are objects that fit in the empty space, then it always puts the biggest one in.<br>
     * 2. If no objects fit in the empty space, then pick an object to swap out -- but prefer to swap objects of similar sizes
     *
     * @param current current selection
     * @return next selection (neighbour)
     */
    private byte[] nextNeighbour(byte[] current) {
        double value = 0;
        double weight = 0;

        byte[] neighbour = new byte[current.length];
        for (int i = 0; i < current.length; i++)
            neighbour[i] = current[i];

        for (int i = 0; i < current.length; i++) {
            value += current[i] * items.get(i).getValue();
            weight += current[i] * items.get(i).getWeight();
        }

        // adding new item if any fit
        int biggestItemIndex = -1; // index of the biggest fitting item (-1 if no fitting item)
        for (int i = 0; i < items.size(); i++) {
            if ((items.get(i).getWeight() + weight) <= capacity) {

                if (biggestItemIndex == -1) {
                    biggestItemIndex = i;
                } else {
                    if (items.get(i).getWeight() > items.get(biggestItemIndex).getWeight())
                        biggestItemIndex = i;
                }

            }
        }

        if (biggestItemIndex != -1) {
            neighbour[biggestItemIndex] = 1;
            return neighbour;
        }

        // no item fit, replacing one item

    }

    @Override // TODO include temperatures
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Capacity=").append(capacity)
                .append("; ItemsCount=").append(items.size())
                .append("; Items={");

        for (int i = 0; i < items.size(); i++) {
            sb.append(i).append("=(v=").append(items.get(i).getValue()).append(", w=").append(items.get(i).getWeight()).append(")");
            if (i < items.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("}");

        return sb.toString();
    }
}
