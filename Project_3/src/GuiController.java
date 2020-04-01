import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import languageRecognition.Network;
import languageRecognition.Text;

public class GuiController {
    @FXML
    TextArea textArea;
    @FXML
    Label label;

    @FXML
    private void buttonAction() {
        label.setText(Main.getNetwork().classify(new Text("", textArea.getText())));
    }
}
