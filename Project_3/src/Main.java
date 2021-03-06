import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import languageRecognition.Network;
import languageRecognition.Observation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {
    private static Network network;
    private static Stage primaryStage;

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

        List<Observation> texts = TextLoader.getTextList(trainSetPath);
        for (Observation o : texts)
            System.out.println(o);
        Collections.shuffle(texts);

        network = new Network(alpha, texts);
        network.learn(1000);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        Main.primaryStage.setTitle("Language Recognition");
        Main.primaryStage.setScene(new Scene(root, 800, 350));
        Main.primaryStage.show();
    }

    public static Network getNetwork() {
        return network;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
