import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InstructionsController {

    @FXML
    private Label Title_Label;

    @FXML
    private Label Instructions1_Label;

    @FXML
    private Label Instructions2_Label;

    @FXML
    private Button StartPuzzle_Button;

    @FXML
    private void initialize() {
        
    }

    @FXML
    private void StartPuzzleButton_clicked() throws IOException {
        Stage stage = (Stage) StartPuzzle_Button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Proj2_2.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
