/**Joey Barton
 * InstructionsController
 * Entry point for the program.
 * Displays a simple set of instructions for the game
 * Displays a button for continuing to the Logic Puzzle
 */
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InstructionsController {

    @FXML private Label Title_Label;
    @FXML private Label Instructions1_Label;
    @FXML private Label Instructions2_Label;

    @FXML private Button StartPuzzle_Button;

    //Nothing to initialize here
    @FXML
    private void initialize() {
        
    }

    //Button handles loading the fxml and Controller for the game
    @FXML
    private void StartPuzzleButton_clicked() throws IOException {
        Stage stage = (Stage) StartPuzzle_Button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Proj2_3.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
