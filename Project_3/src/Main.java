import languageRecognition.Network;
import languageRecognition.Text;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // args
        final double alpha;
        final Path trainSetPath;

        if (args.length == 2) {
            alpha = Double.parseDouble(args[0]);
            trainSetPath = Paths.get(args[1]);
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.println("> Enter alpha:");
            alpha = sc.nextDouble();
            System.out.println("> Enter train set path:");
            trainSetPath = Paths.get(sc.next());
        }

        List<Text> texts = TextLoader.getTextList(trainSetPath);
        Collections.shuffle(texts);

        for (Text t : texts)
            System.out.println(t);

        Network network = new Network(alpha, texts);
    }
}
