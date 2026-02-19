/** Joey Barton
 * SceneController
 * 
 * This class manages GUI and logic flow
 * This class builds the puzzle grid and category labels
 * Manages PuzzleSquareUI objects, their interactions, and states
 * Manages user actions such as hints, clear errors, resetting puzzle, and submitting the solution
 * Central hub that coordinates all puzzle behavior
 */

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class SceneController {

    //Main grid where all puzzle squares and labels live
    @FXML private GridPane squarePane1_GridPane;

    //Buttons for user actions
    @FXML private Button SubmitPuzzle_Button;

    @FXML private Button Hint_Button;

    @FXML private TextArea Clue_TextArea;

    @FXML private Label Hint_Label;

    //8x8 array of PuzzleSquareUI object (some unused)
    private PuzzleSquareUI[][] puzzleSquares = new PuzzleSquareUI[8][8];

    //Setters for each category title
    private String category1 = "Category1";
    private String category2 = "Category2";
    private String category3 = "Category3";

    //Category 1's values
    private String cat1Value1 = "C1V1";
    private String cat1Value2 = "C1V2";
    private String cat1Value3 = "C1V3";
    private String cat1Value4 = "C1V4";

    //Category 2's values
    private String cat2Value1 = "C2V1";
    private String cat2Value2 = "C2V2";
    private String cat2Value3 = "C2V3";
    private String cat2Value4 = "C2V4";

    //Category 3's values
    private String cat3Value1 = "C3V1";
    private String cat3Value2 = "C3V2";
    private String cat3Value3 = "C3V3";
    private String cat3Value4 = "C3V4";

    //Clues will be filled in here, appearing in the TextArea on the right of the program.
    private String Clue1 = "The";
    private String Clue2 = "Quick";
    private String Clue3 = "Brown";
    private String Clue4 = "Fox";
    private String Clue5 = "";
    
    //The HintLimit is incremented each time the Hint Button is pressed.
    //We still need to implement a limit, based on the number of pre-loaded hints.
    private int HintLimit = 0;

    //Hint structure:
    //[row, col, shouldBeSelected (true=selected, false=excluded), hintString]
    private Object[][] hints = {
        {0, 0, true, "Row1 and Col1 match up!"},
        {1, 2, false, "Row2 and Col3 don't match up!"}
    };

    //TEMP TESTER
    private Boolean[][] fakeSolution = new Boolean[8][8];

    
    private PuzzleSquareUI currentlyHighlightedSquare = null;

    //Builds grids, labels, clues, and initializes temp solution
    @FXML
    private void initialize() {

        //Create top left and top right category labels
        Label topLeftCategory = createCategoryLabel(category1);
        Label topRightCategory = createCategoryLabel(category2);
       
        //Adds category labels to grid
        squarePane1_GridPane.add(topLeftCategory, 2, 0);
        GridPane.setColumnSpan(topLeftCategory, 4);

        squarePane1_GridPane.add(topRightCategory, 6, 0);
        GridPane.setColumnSpan(topRightCategory, 4);
        
        //Creates left side, vertical category labels
        Label leftCategory1 = createVerticalCategoryLabel(category3);
        Label leftCategory2 = createVerticalCategoryLabel(category2);

        //Adds left side category labels to grid
        squarePane1_GridPane.add(leftCategory1, 0, 2);
        GridPane.setRowSpan(leftCategory1, 4);

        squarePane1_GridPane.add(leftCategory2, 0, 6);
        GridPane.setRowSpan(leftCategory2, 4);

        //Add column labels
        String[] columnLabels = {cat1Value1, cat1Value2, cat1Value3, cat1Value4, cat2Value1, cat2Value2, cat2Value3, cat2Value4};
        for(int col = 0; col < 8; col++) {
            Label label = createLabel(columnLabels[col]);
            squarePane1_GridPane.add(label, col + 2, 1);
        }

        //Add row labels
        String[] rowLabels = {cat3Value1, cat3Value2, cat3Value3, cat3Value4, cat2Value1, cat2Value2, cat2Value3, cat2Value4};
        for(int row = 0; row < 8; row++) {
            Label label = createLabel(rowLabels[row]);
            squarePane1_GridPane.add(label, 1, row + 2);
        }

        //Load the temp solution
        initializeSolution();

        //Build puzzle squares
        printGrid();

        //Display clues in TextArea (on right)
        setCluesUI();

    }

    //TEMP TESTER. Hardcoded solution grid
    private void initializeSolution() {

        //Start fresh
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                fakeSolution[row][col] = null;
            }
        }

        //Top left grid
        fakeSolution[0][0] = true;
        fakeSolution[0][1] = false;
        fakeSolution[0][2] = false;
        fakeSolution[0][3] = false;

        fakeSolution[1][0] = false;
        fakeSolution[1][1] = false;
        fakeSolution[1][2] = true;
        fakeSolution[1][3] = false;

        fakeSolution[2][0] = false;
        fakeSolution[2][1] = true;
        fakeSolution[2][2] = false;
        fakeSolution[2][3] = false;

        fakeSolution[3][0] = false;
        fakeSolution[3][1] = false;
        fakeSolution[3][2] = false;
        fakeSolution[3][3] = true;


        //Top right grid
        fakeSolution[0][4] = true;
        fakeSolution[0][5] = false;
        fakeSolution[0][6] = false;
        fakeSolution[0][7] = false;

        fakeSolution[1][4] = false;
        fakeSolution[1][5] = false;
        fakeSolution[1][6] = true;
        fakeSolution[1][7] = false;

        fakeSolution[2][4] = false;
        fakeSolution[2][5] = true;
        fakeSolution[2][6] = false;
        fakeSolution[2][7] = false;

        fakeSolution[3][4] = false;
        fakeSolution[3][5] = false;
        fakeSolution[3][6] = false;
        fakeSolution[3][7] = true;


        //Bottom left grid
        fakeSolution[4][0] = true;
        fakeSolution[4][1] = false;
        fakeSolution[4][2] = false;
        fakeSolution[4][3] = false;

        fakeSolution[5][0] = false;
        fakeSolution[5][1] = false;
        fakeSolution[5][2] = true;
        fakeSolution[5][3] = false;

        fakeSolution[6][0] = false;
        fakeSolution[6][1] = true;
        fakeSolution[6][2] = false;
        fakeSolution[6][3] = false;

        fakeSolution[7][0] = false;
        fakeSolution[7][1] = false;
        fakeSolution[7][2] = false;
        fakeSolution[7][3] = true;



    }

    //Creates and places all PuzzleSquareUI squares into the grid
    //3 quadrants, Top Left, Top Right, Bottom Left
    private void printGrid() {

        //Top left. First / Third categories, 4 values.
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                PuzzleSquareUI square = new PuzzleSquareUI(row, col, this);
                puzzleSquares[row][col] = square;
                squarePane1_GridPane.add(square, col + 2, row + 2);
            }
        }

        //Top right. Second category, 4 values
        for(int row = 0; row < 4; row++) {
            for(int col = 4; col < 8; col++) {
                PuzzleSquareUI square = new PuzzleSquareUI(row, col, this);
                puzzleSquares[row][col] = square;
                squarePane1_GridPane.add(square, col + 2, row + 2);
            }
        }

        //Bottom left. Second category, 4 values
        for(int row = 4; row < 8; row++) {
            for(int col = 0; col < 4; col++) {
                PuzzleSquareUI square = new PuzzleSquareUI(row, col, this);
                puzzleSquares[row][col] = square;
                squarePane1_GridPane.add(square, col + 2, row + 2);
            }
        }
    }

    //Displays all clues in TextArea
    private void setCluesUI() {
        if(!Clue1.equals("")) {
            Clue_TextArea.appendText("1. " + Clue1 + "\n");
        }
        if(!Clue2.equals("")) {
            Clue_TextArea.appendText("2. " + Clue2 + "\n");
        }
        if(!Clue3.equals("")) {
            Clue_TextArea.appendText("3. " + Clue3 + "\n");
        }
        if(!Clue4.equals("")) {
            Clue_TextArea.appendText("4. " + Clue4 + "\n");
        }
        if(!Clue5.equals("")) {
            Clue_TextArea.appendText("5. " + Clue5 + "\n");
        }

        Clue_TextArea.setEditable(false);
    }

    //Determines which quadrant a square belongs to
    private int getGridIndex(int row, int col) {
        
        //Top left quadrant / grid
        if(row < 4 && col < 4) {
            return 0;
        }

        //Top right quadrant / grid
        if(row < 4 && col >= 4) {
            return 1;
        }

        //Bottom left quadrant / grid
        if(row >= 4 && col < 4) {
            return 2;
        }

        //Bottom right does not exist
        return -1;
    }

    //Creates a standard label
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 10px; -fx-padding: 2;");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        return label;
    }

    //Creates a category label
    private Label createCategoryLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 5;");
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }
    
    //Creates a vertical category label
    private Label createVerticalCategoryLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 5;");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setRotate(-90);
        return label;
    }

    //Automatically excludes all other squares in the same row and column quadrant
    //Used when a square is selected
    public void onSquareSelected(int row, int col) {

        int gridIndex = getGridIndex(row, col);
        int rowStart;
        int rowEnd;
        int colStart;
        int colEnd;

        if(row < 4) {
            rowStart = 0;
            rowEnd = 4;
        } else {
            rowStart = 4;
            rowEnd = 8;
        }

        if(col < 4) {
            colStart = 0;
            colEnd = 4;
        } else {
            colStart = 4;
            colEnd = 8;
        }

        for(int i = colStart; i < colEnd; i++) {
            if(i != col && puzzleSquares[row][i] != null) {
                puzzleSquares[row][i].autoExclude();
            }
        }

        for(int i = rowStart; i < rowEnd; i++) {
            if(i != row && puzzleSquares[i][col] != null) {
                puzzleSquares[i][col].autoExclude();
            }
        }

        checkCompletion();
    }

    //Removes auto-exclusions in the same row and column quadrant
    //Used when a square is deselected
    public void onSquareDeselected(int row, int col) {

        int rowStart;
        int rowEnd;
        int colStart;
        int colEnd;

        if(row < 4) {
            rowStart = 0;
            rowEnd = 4;
        } else {
            rowStart = 4;
            rowEnd = 8;
        }

        if(col < 4) {
            colStart = 0;
            colEnd = 4;
        } else {
            colStart = 4;
            colEnd = 8;
        }

        //Remove auto-exclusions from the same row
        for(int i = colStart; i < colEnd; i++) {
            if(i != col && puzzleSquares[row][i] != null) {
                puzzleSquares[row][i].removeAutoExclude();
            }
        }

        // Remove auto-exclusions from the same column
        for(int i = rowStart; i < rowEnd; i++) {
            if(i != row && puzzleSquares[i][col] != null) {
                puzzleSquares[i][col].removeAutoExclude();
            }
        }

        checkCompletion();
    }

    //Resets all squares and UI elements to initial state
    @FXML
    private void StartOverButton_clicked() {
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(puzzleSquares[row][col] != null) {
                    puzzleSquares[row][col].resetSquare();
                }
            }
        }
        SubmitPuzzle_Button.setVisible(false);
        clearHintLabel();
        currentlyHighlightedSquare = null;
    }

    //Handles hint button logic
    @FXML
    private void HintButton_clicked() {

        //TODO: Compare Solution to UserInput.
        //First hint given should be equivalent to CheckErrors

        //Back to back Hint requests
        if(currentlyHighlightedSquare != null) {
            currentlyHighlightedSquare.setHighlight(false);
            currentlyHighlightedSquare = null;
        }

        //Iterate through all hints
        for(int i = 0; i < hints.length; i++) {

            //Parse Hint object
            int hintRow = (int) hints[i][0];
            int hintCol = (int) hints[i][1];
            boolean shouldBeSelected = (boolean) hints[i][2];
            String hintText = (String) hints[i][3];

            PuzzleSquareUI square = puzzleSquares[hintRow][hintCol];

            //Determine if the square is already correct
            boolean isCorrect = (shouldBeSelected && square.isSelected() || !shouldBeSelected && square.isExcluded());

            //Only highlight if hint not already found
            if(!isCorrect && square.isEmpty()) {
                square.setHighlight(true);
                currentlyHighlightedSquare = square;
                Hint_Label.setText("HINT: " + hintText);
                Hint_Label.setVisible(true);
                HintLimit++;
                return;
            }
        }

        //No more hints available
        Hint_Label.setText("HINT: No more hints available!");
        Hint_Label.setVisible(true);
        
    }

    //Clears all incorrect squares based on fakeSolution grid
    @FXML
    private void ClearErrorsButton_clicked() {
        boolean foundErrors = false;

        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(puzzleSquares[row][col] != null && fakeSolution[row][col] != null) {
                    PuzzleSquareUI square = puzzleSquares[row][col];
                    boolean correctAnswer = fakeSolution[row][col];

                    boolean isIncorrect = false;

                    if(correctAnswer && !square.isSelected()) {
                        //Should be selected but is excluded
                        if(square.isExcluded()) {
                            isIncorrect = true;
                        }
                    } else if(!correctAnswer && !square.isExcluded()) {
                        //Should be excluded but is selected
                        if(square.isSelected()) {
                            isIncorrect = true;
                        }
                    }

                    if(isIncorrect) {
                        square.clearErrorSquare();
                        foundErrors = true;
                    }
                }
            }
        }

        if(!foundErrors) {
            Hint_Label.setText("No errors found!");
            Hint_Label.setVisible(true);
        } else {
            checkCompletion();
        }
    }

    //Placeholder for final puzzle submission logic
    @FXML
    private void SubmitPuzzleButton_clicked() {
        //Will compare user input to official Solution
    }

    //Removes all error highlights 
    //Used after highlights have been acknowledged
    public void clearAllErrorHighlights() {
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(puzzleSquares[row][col] != null) {
                    puzzleSquares[row][col].setError(false);
                }
            }
        }

    }

    //Resets Hint label back to initial and hides it
    public void clearHintLabel() {
        Hint_Label.setVisible(false);
        Hint_Label.setText("");
    }

    //Checks for puzzle completion
    //All squares filled
    //Then enables Submit button
    private void checkCompletion() {
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(puzzleSquares[row][col] != null && puzzleSquares[row][col].isEmpty()) {
                    SubmitPuzzle_Button.setVisible(false);
                    return;
                }
            } 
        }

        SubmitPuzzle_Button.setVisible(true);
    }

    //Called when a square changes state
    public void squareStateChange() {
        checkCompletion();
    }
}
