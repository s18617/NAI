import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File[] files = new File[]{
                new File("knapsacks/knapsack.txt"),
                new File("knapsacks/PO1.txt"),
                new File("knapsacks/PO2.txt"),
                new File("knapsacks/PO3.txt"),
                new File("knapsacks/PO4.txt"),
                new File("knapsacks/PO5.txt"),
                new File("knapsacks/PO6.txt"),
                new File("knapsacks/PO7.txt"),
                new File("knapsacks/PO8.txt")
        };

        Knapsack[] knapsacks = new Knapsack[files.length];

        for (int i = 0; i < files.length; i++) {
            knapsacks[i] = Knapsack.loadKnapsack(files[i]);
        }

        boolean running = true;
        Scanner sc = new Scanner(System.in);

        System.out.println("> Enter index of chosen knapsack (or 'q' to quit)");
        printIndexedKnapsacks(knapsacks);
        String input = sc.nextLine();

        if (input.equals("q")) {
            System.exit(0);
        }

        while (running) {
            byte[] selection = knapsacks[Integer.parseInt(input)].bruteForce();
            System.out.println("> Best selection is " + Arrays.toString(selection));

            System.out.println("\r\n> Enter index of chosen knapsack (or 'q' to quit)");
            printIndexedKnapsacks(knapsacks);
            input = sc.nextLine();

            if (input.equals("q")) {
                running = false;
            }
        }


        sc.close();
    }

    private static void printIndexedKnapsacks(Knapsack[] knapsacks) {
        for (int i = 0; i < knapsacks.length; i++) {
            System.out.println("[" + i + "] " + knapsacks[i].toString());
        }
    }
}
