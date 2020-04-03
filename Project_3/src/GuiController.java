import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import languageRecognition.Network;
import languageRecognition.Observation;
import languageRecognition.Text;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GuiController {
    @FXML
    TextArea textArea;
    @FXML
    Label label;

    private Network network;

    @FXML
    private void buttonAction() {
        if (network == null) {
            network = Main.getNetwork();
        }
        label.setText("Language: " + network.classify(new Text("", textArea.getText())));
    }

    @FXML
    private void chooseDir() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(Main.getPrimaryStage());
        if (selectedDirectory != null) {
            if (selectedDirectory.isDirectory()) {
                Path path = Paths.get(selectedDirectory.getAbsolutePath());
                success(path);
            } else {
                failure();
            }
        } else {
            failure();
        }
    }

    private void success(Path path) {
        TextInputDialog dialog = new TextInputDialog("1.0");
        dialog.setHeaderText("Alpha");
        dialog.setContentText("Please choose learning rate: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            double alpha = Double.parseDouble(result.get());

            List<Observation> texts = TextLoader.getTextList(path);
            for (Observation o : texts)
                System.out.println(o);
            Collections.shuffle(texts);

            network = new Network(alpha, texts);
            network.learn(1000);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Loading successful");
            alert.setContentText("New network was created.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failure");
            alert.setHeaderText("Loading failed");
            alert.setContentText("No alpha was given.");
        }
    }

    private void failure() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failure");
        alert.setHeaderText("Loading failed");
        alert.setContentText("No directory was given, or given file wasn't a directory.");
    }
}
