import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Most data is from https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html
        File folder = new File("knapsacks/");
        File[] files = folder.listFiles();

        Knapsack[] knapsacks = new Knapsack[files.length];

        for (int i = 0; i < files.length; i++)
            knapsacks[i] = Knapsack.loadKnapsack(files[i]);

        boolean running = true;
        Scanner sc = new Scanner(System.in);

        System.out.println("> Enter index of chosen backpack (or q to quit)");
        printIndexedKnapsacks(knapsacks);
        String input = sc.nextLine();

        if (input.equals("q"))
            running = false;

        while (running) {
            byte[] selection = knapsacks[Integer.parseInt(input)].bruteForce();
            System.out.println("> Best selection is " + Arrays.toString(selection));

            System.out.println("\r\n> Enter index of chosen backpack (or q to quit)");
            printIndexedKnapsacks(knapsacks);
            input = sc.nextLine();

            if (input.equals("q"))
                running = false;
        }

        sc.close();
    }

    private static void printIndexedKnapsacks(Knapsack[] knapsacks) {
        for (int i = 0; i < knapsacks.length; i++)
            System.out.println("[" + i + "]" + knapsacks[i].toString());
    }
}
