import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Knapsack {
    private final double capacity; // knapsack capacity
    private final ArrayList<Pair<Double, Double>> items; // <value, weight>

    public Knapsack(double capacity, ArrayList<Pair<Double, Double>> items) {
        this.capacity = capacity;
        this.items = items;
    }

    public static Knapsack loadKnapsack(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            ArrayList<Pair<Double, Double>> items = new ArrayList<>();

            String[] split = br.readLine().split(" ", 2);

            double capacity = Double.parseDouble(split[0]);

            String[] values = br.readLine().split(",");
            String[] weights = br.readLine().split(",");

            if (values.length != weights.length) {
                throw new Exception("Invalid file format");
            }

            for (int i = 0; i < values.length; i++) {
                items.add(i, new Pair<>(Double.parseDouble(values[i]), Double.parseDouble(weights[i])));
            }

            return new Knapsack(capacity, items);
        } catch (IOException ex) {
            System.err.println("> Error occurred while reading a file containing knapsack info");
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte[] bruteForce() {
        // timing
        long start = System.currentTimeMillis();

        byte[] bestSelection = null;
        double bestValue = -1;

        for (int i = 1; i < Math.pow(2, this.items.size()); i++) {
            // generating selection
            String[] bin = Integer.toBinaryString(i).split("");
            byte[] selection = new byte[this.items.size()];
            for (int j = bin.length - 1; j >= 0; j--) {
                selection[j + (selection.length - bin.length)] = Byte.parseByte(bin[j]);
            }

            // checking selection
            double value = 0;
            double weight = 0;

            for (int j = 0; j < selection.length; j++) {
                weight += items.get(j).getValue() * selection[j];
                value += items.get(j).getKey() * selection[j];
            }

            if (weight <= capacity) {
                if (value > bestValue) {
                    bestValue = value;
                    bestSelection = selection;
                    System.out.println("New best selection " + Arrays.toString(bestSelection));
                }
            }
        }

        // timing
        long finish = System.currentTimeMillis();
        long msElapsed = finish - start;
        System.out.println("Checked all the possible selections in " + (msElapsed / 1000.0) + " s, or " + msElapsed + " ms");

        return bestSelection;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Capacity=").append(capacity)
                .append("; ItemsCount=").append(items.size())
                .append("; Items={");

        for (int i = 0; i < items.size(); i++) {
            sb.append(i).append("=(v=").append(items.get(i).getKey()).append(", w=").append(items.get(i).getValue()).append(")");
            if (i < items.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("}");

        return sb.toString();
    }
}
