import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // args
        final double k;
        final Path datasetPath;

        if (args.length == 2) {
            k = Double.parseDouble(args[0]);
            datasetPath = Paths.get(args[1]);
        } else {
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("> Enter k:");
                k = sc.nextDouble();
                System.out.println("> Enter dataset path:");
                datasetPath = Paths.get(sc.next());
            } catch (Exception ex) {
                System.err.println("> Error occured while initializing");
                ex.printStackTrace();
                System.exit(-1);
            }
        }
    }
}
