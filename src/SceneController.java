// /**
//  * SceneController
//  * 
//  * This class manages GUI and logic flow
//  * This class builds the puzzle grid and category labels
//  * Manages PuzzleSquareUI objects, their interactions, and states
//  * Manages user actions such as hints, clear errors, resetting puzzle, and submitting the solution
//  * Central hub that coordinates all puzzle behavior
//  */

// import java.util.List;

// import javafx.fxml.FXML;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.TextArea;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.Region;

// public class SceneController {

//     //Main grid where all puzzle squares and labels live
//     @FXML private GridPane squarePane1_GridPane;
//     @FXML private GridPane colLabels_GridPane;

//     //Buttons for user actions
//     @FXML private Button SubmitPuzzle_Button;
//     @FXML private Button Hint_Button;

//     @FXML private TextArea Clue_TextArea;

//     @FXML private Label Hint_Label;

//     //8x8 array of PuzzleSquareUI object (some unused)
//     private PuzzleSquareUI[][] puzzleSquares = new PuzzleSquareUI[8][8];

//     private LogicPuzzle puzzle;

//     private List<CategoryComparison> comparisons;

//     private int hintIndex = 0;
    
//     private PuzzleSquareUI currentlyHighlightedSquare = null;

//     private double SQUARE_SIZE = 48;

//     //Builds grids, labels, clues, and initializes temp solution
//     @FXML
//     private void initialize() {

//         puzzle = LogicPuzzle.buildPuzzle1();
//         comparisons = puzzle.getComparisons();

//         applyColumnConstraints();

//         buildCategoryLabels();

//         buildValueLabels();

//         //Build puzzle squares
//         printGrid();

//         debugGridMapping();

//         //Display clues in TextArea (on right)
//         setCluesUI();

//         SubmitPuzzle_Button.setVisible(false);

//     }

//     private void debugGridMapping() {
//         System.out.println("==== GRID MAPPING DEBUG ===");
//         for(int row = 0; row < 8; row++) {
//             for(int col = 0; col < 8; col++) {
//                 if(puzzleSquares[row][col] != null) {
//                     String gridKey = getGridKey(row, col);
//                     String rowValue = getRowValue(row, col);
//                     String colValue = getColValue(row, col);
//                     System.out.println("Square[" + row + "][" + col + "] gridKey: " + gridKey + " | row: " + rowValue + "  | col: " + colValue + " | correct: " + puzzle.getSolution().checkGuess(gridKey, rowValue, colValue));
//                 }
//             }
//         }

//         System.out.println("===SOLUTION PAIRS====");
//         String[] gridKeys = {
//             comparisons.get(0).getGridKey(),
//             comparisons.get(1).getGridKey(),
//             comparisons.get(2).getGridKey(),
//         };

//         for(String key : gridKeys) {
//             System.out.println("Grid: " + key);
//             List<String> rowVals = key.equals(comparisons.get(0).getGridKey()) ?
//                 comparisons.get(0).getRowCategory().getCatValues() : 
//                 key.equals(comparisons.get(1).getGridKey()) ?
//                 comparisons.get(1).getRowCategory().getCatValues() :
//                 comparisons.get(2).getRowCategory().getCatValues();
//             for(String rv : rowVals) {
//                 System.out.println(" " + rv + " -> " + puzzle.getSolution().checkGuess(key, rv, "test"));
//             }
            
//         }


//     }

//     private void applyColumnConstraints() {

//         javafx.scene.layout.RowConstraints rc1 = new javafx.scene.layout.RowConstraints();
//         rc1.setMinHeight(30);
//         colLabels_GridPane.getRowConstraints().add(rc1);

//         javafx.scene.layout.RowConstraints rc2 = new javafx.scene.layout.RowConstraints();
//         rc2.setMinHeight(40);
//         colLabels_GridPane.getRowConstraints().add(rc2);

//         addColConstraint(squarePane1_GridPane, 80);
//         addColConstraint(colLabels_GridPane, 80);

//         addColConstraint(squarePane1_GridPane, 80);
//         addColConstraint(colLabels_GridPane, 80);


        
//         for(int i = 0; i < 8; i++) {
//             addColConstraint(squarePane1_GridPane, SQUARE_SIZE);
//             addColConstraint(colLabels_GridPane, SQUARE_SIZE);
        
//         }
//     }

//     private void addColConstraint(GridPane grid, double width) {
//         javafx.scene.layout.ColumnConstraints cc = new javafx.scene.layout.ColumnConstraints();
//         cc.setMinWidth(width);
//         cc.setMaxWidth(width);
//         cc.setPrefWidth(width);
//         grid.getColumnConstraints().add(cc);
//     }

//     private void buildCategoryLabels() {

//         //Spacer
//         Label spacer = new Label("");
//         spacer.setMinWidth(70);
//         colLabels_GridPane.add(spacer, 0, 0);

//         CategoryComparison topLeft = comparisons.get(0);
//         CategoryComparison topRight = comparisons.get(1);

//         Label Category1Col = createCategoryLabel(topLeft.getColCategory().getName());
//         Label Category2Col = createCategoryLabel(topRight.getColCategory().getName());

//         colLabels_GridPane.add(Category1Col, 2, 0);
//         GridPane.setColumnSpan(Category1Col, 4);

//         colLabels_GridPane.add(Category2Col, 6, 0);
//         GridPane.setColumnSpan(Category2Col, 4);

//         Label Category1Row = createVerticalCategoryLabel(topLeft.getRowCategory().getName());
//         Label Category2Row = createVerticalCategoryLabel(comparisons.get(2).getRowCategory().getName());

//         squarePane1_GridPane.add(Category1Row, 0, 2);
//         GridPane.setRowSpan(Category1Row,4);

//         squarePane1_GridPane.add(Category2Row, 0, 6);
//         GridPane.setRowSpan(Category2Row,4);
//     }

//     private void buildValueLabels() {
//         List<String> Values1Col = comparisons.get(0).getColCategory().getCatValues();
//         List<String> Values2Col = comparisons.get(1).getColCategory().getCatValues();
        
//         for(int i = 0; i < 4; i++) {
//             colLabels_GridPane.add(createVerticalValueLabel(Values1Col.get(i)), i + 2, 1);
//             colLabels_GridPane.add(createVerticalValueLabel(Values2Col.get(i)), i + 6, 1);
//         }

//         List<String> Values1Row = comparisons.get(0).getRowCategory().getCatValues();
//         List<String> Values2Row = comparisons.get(2).getRowCategory().getCatValues();
        
//         for(int i = 0; i < 4; i++) {
//             squarePane1_GridPane.add(createLabel(Values1Row.get(i)), 1, i + 2);
//             squarePane1_GridPane.add(createLabel(Values2Row.get(i)), 1, i + 6);
//         }
//     }
    
//     //Creates and places all PuzzleSquareUI squares into the grid
//     //3 quadrants, Top Left, Top Right, Bottom Left
//     private void printGrid() {

//         //Top left. First / Third categories, 4 values.
//         for(int row = 0; row < 4; row++) {
//             for(int col = 0; col < 4; col++) {
//                 PuzzleSquareUI square = new PuzzleSquareUI(row, col, this);
//                 puzzleSquares[row][col] = square;
//                 squarePane1_GridPane.add(square, col + 2, row + 2);
//             }
//         }

//         //Top right. Second category, 4 values
//         for(int row = 0; row < 4; row++) {
//             for(int col = 4; col < 8; col++) {
//                 PuzzleSquareUI square = new PuzzleSquareUI(row, col, this);
//                 puzzleSquares[row][col] = square;
//                 squarePane1_GridPane.add(square, col + 2, row + 2);
//             }
//         }

//         //Bottom left. Second category, 4 values
//         for(int row = 4; row < 8; row++) {
//             for(int col = 0; col < 4; col++) {
//                 PuzzleSquareUI square = new PuzzleSquareUI(row, col, this);
//                 puzzleSquares[row][col] = square;
//                 squarePane1_GridPane.add(square, col + 2, row + 2);
//             }
//         }
//     }

//     private void addSquare(int row, int col) {
//         PuzzleSquareUI square = new PuzzleSquareUI(row, col, this);
//         puzzleSquares[row][col] = square;
//         squarePane1_GridPane.add(square, col + 2, row + 2);
//     }

//     //Displays all clues in TextArea
//     private void setCluesUI() {
//         int clueNum = 1;

//         for(Clue clue : puzzle.getClues()) {
//             if(!clue.getContent().isEmpty()) {
//                 Clue_TextArea.appendText(clueNum + ". " + clue.getContent() + "\n");
//                 clueNum++;
//             }
//         }

//         Clue_TextArea.setEditable(false);
//     }

//     //Determines which quadrant a square belongs to
//     private int getGridIndex(int row, int col) {
        
//         //Top left quadrant / grid
//         if(row < 4 && col < 4) {
//             return 0;
//         }

//         //Top right quadrant / grid
//         if(row < 4 && col >= 4) {
//             return 1;
//         }

//         //Bottom left quadrant / grid
//         if(row >= 4 && col < 4) {
//             return 2;
//         }

//         //Bottom right is not needed.
//         return -1;
//     }

//     private String getGridKey(int row, int col) {
//         int gridIndex = getGridIndex(row, col);
//         if(gridIndex == -1) {
//             return null;
//         }
//         return comparisons.get(gridIndex).getGridKey();
//     }

//     private String getRowValue(int row, int col) {
//         int gridIndex = getGridIndex(row, col);
//         if(gridIndex == -1) {
//             return null;
//         }
//         int localRow = row % 4;
//         return comparisons.get(gridIndex).getRowValue(localRow);
//     }

//     private String getColValue(int row, int col) {
//         int gridIndex = getGridIndex(row, col);
//         if(gridIndex == -1) {
//             return null;
//         }
//         int localCol = col % 4;
//         return comparisons.get(gridIndex).getColValue(localCol);
//     }

//     //Creates a standard label
//     private Label createLabel(String text) {
//         Label label = new Label(text);
//         label.setAlignment(Pos.CENTER);
//         label.setStyle("-fx-font-size: 10px; -fx-padding: 2;");
//         label.setPrefWidth(60);
//         label.setMaxWidth(Double.MAX_VALUE);
//         label.setMaxHeight(Double.MAX_VALUE);
//         return label;
//     }

//     //Creates a category label
//     private Label createCategoryLabel(String text) {
//         Label label = new Label(text);
//         label.setAlignment(Pos.CENTER);
//         label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 5;");
//         label.setMaxWidth(Double.MAX_VALUE);
//         return label;
//     }
    
//     //Creates a vertical category label
//     private Label createVerticalCategoryLabel(String text) {
//         Label label = new Label(text);
//         label.setAlignment(Pos.CENTER);
//         label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 5;");
//         label.setMaxWidth(Double.MAX_VALUE);
//         label.setMaxHeight(Double.MAX_VALUE);
//         label.setRotate(-90);
//         return label;
//     }

//     private Label createVerticalValueLabel(String text) {
//         Label label = new Label(text);
//         label.setAlignment(Pos.CENTER);
//         label.setStyle("-fx-font-size: 10px; -fx-padding: 2; ");
//         label.setMinWidth(Region.USE_PREF_SIZE);
//         label.setPrefWidth(Region.USE_COMPUTED_SIZE);
//         label.setMaxWidth(Double.MAX_VALUE);
//         label.setMaxHeight(Double.MAX_VALUE);
//         label.setRotate(-45);
//         return label;
//     }

//     //Automatically excludes all other squares in the same row and column quadrant
//     //Used when a square is selected
//     public void onSquareSelected(int row, int col) {

//         int rowStart;
//         int rowEnd;
//         int colStart;
//         int colEnd;

//         if(row < 4) {
//             rowStart = 0;
//             rowEnd = 4;
//         } else {
//             rowStart = 4;
//             rowEnd = 8;
//         }

//         if(col < 4) {
//             colStart = 0;
//             colEnd = 4;
//         } else {
//             colStart = 4;
//             colEnd = 8;
//         }

//         for(int i = colStart; i < colEnd; i++) {
//             if(i != col && puzzleSquares[row][i] != null) {
//                 puzzleSquares[row][i].autoExclude();
//             }
//         }

//         for(int i = rowStart; i < rowEnd; i++) {
//             if(i != row && puzzleSquares[i][col] != null) {
//                 puzzleSquares[i][col].autoExclude();
//             }
//         }

//         checkCompletion();
//     }

//     //Removes auto-exclusions in the same row and column quadrant
//     //Used when a square is deselected
//     public void onSquareDeselected(int row, int col) {

//         int rowStart;
//         int rowEnd;
//         int colStart;
//         int colEnd;

//         if(row < 4) {
//             rowStart = 0;
//             rowEnd = 4;
//         } else {
//             rowStart = 4;
//             rowEnd = 8;
//         }

//         if(col < 4) {
//             colStart = 0;
//             colEnd = 4;
//         } else {
//             colStart = 4;
//             colEnd = 8;
//         }

//         //Remove auto-exclusions from the same row
//         for(int i = colStart; i < colEnd; i++) {
//             if(i != col && puzzleSquares[row][i] != null) {
//                 puzzleSquares[row][i].removeAutoExclude();
//             }
//         }

//         // Remove auto-exclusions from the same column
//         for(int i = rowStart; i < rowEnd; i++) {
//             if(i != row && puzzleSquares[i][col] != null) {
//                 puzzleSquares[i][col].removeAutoExclude();
//             }
//         }

//         checkCompletion();
//     }

//     //Resets all squares and UI elements to initial state
//     @FXML
//     private void StartOverButton_clicked() {
//         for(int row = 0; row < 8; row++) {
//             for(int col = 0; col < 8; col++) {
//                 if(puzzleSquares[row][col] != null) {
//                     puzzleSquares[row][col].resetSquare();
//                 }
//             }
//         }
//         SubmitPuzzle_Button.setVisible(false);
//         hintIndex = 0;
//         clearHintLabel();
//         currentlyHighlightedSquare = null;
//     }

//     //Handles hint button logic
//     @FXML
//     private void HintButton_clicked() {

//         //TODO: Compare Solution to UserInput.
//         //First hint given should be equivalent to CheckErrors

//         //Back to back Hint requests
//         if(currentlyHighlightedSquare != null) {
//             currentlyHighlightedSquare.setHighlight(false);
//             currentlyHighlightedSquare = null;
//         }

//         List<Hint> hints = puzzle.getHints();

//         while(hintIndex < hints.size()) {
//             Hint hint = hints.get(hintIndex);
//             hintIndex++;

//             PuzzleSquareUI square = puzzleSquares[hint.getRow()][hint.getCol()];
//             //Determine if the square is already correct
//             boolean isCorrect = (hint.shouldBeSelected() && square.isSelected() 
//                                 || !hint.shouldBeSelected() && square.isExcluded());

//             //Only highlight if hint not already found
//             if(!isCorrect && square.isEmpty()) {
//                 square.setHighlight(true);
//                 currentlyHighlightedSquare = square;
//                 Hint_Label.setText("HINT: " + hint.getMessage());
//                 Hint_Label.setVisible(true);
//                 return;
//             }
//         }

//         //No more hints available
//         Hint_Label.setText("HINT: No more hints available!");
//         Hint_Label.setVisible(true);
        
//     }

//     //Clears all incorrect squares based on fakeSolution grid
//     @FXML
//     private void ClearErrorsButton_clicked() {
//         boolean foundErrors = false;

//         for(int row = 0; row < 8; row++) {
//             for(int col = 0; col < 8; col++) {
//                 if(puzzleSquares[row][col] == null) { 
//                     continue;
//                 }

//                 String gridKey = getGridKey(row, col);
//                 if(gridKey == null) {
//                     continue;
//                 }
//                 String rowValue = getRowValue(row, col);
//                 String colValue = getColValue(row, col);

//                 boolean shouldBeSelected = puzzle.getSolution().checkGuess(gridKey, rowValue, colValue);

//                 PuzzleSquareUI square = puzzleSquares[row][col];

//                 if(shouldBeSelected && square.isExcluded()) {
//                     square.clearErrorSquare();
//                     foundErrors = true;
//                 } else if(!shouldBeSelected && square.isSelected()) {
//                     square.clearErrorSquare();
//                     foundErrors = true;
//                 }
//             }
//         } 
        

//         if(!foundErrors) {
//             Hint_Label.setText("No errors found!");
//             Hint_Label.setVisible(true);
//         } else {
//             checkCompletion();
//         }
//     }

    
//     @FXML
//     private void SubmitPuzzleButton_clicked() {
//         for(int row = 0; row < 8; row++) {
//             for(int col = 0; col < 8; col++) {
//                 if(puzzleSquares[row][col] == null) {
//                     continue;
//                 }
//                 String gridKey = getGridKey(row, col);
//                 if(gridKey == null) {
//                     continue;
//                 }

//                 String rowValue = getRowValue(row, col);
//                 String colValue = getColValue(row, col);

//                 boolean shouldBeSelected = puzzle.getSolution().checkGuess(gridKey, rowValue, colValue);

//                 PuzzleSquareUI square = puzzleSquares[row][col];

//                 if(shouldBeSelected && !square.isSelected()) {
//                     Hint_Label.setText("Incorrect!");
//                     Hint_Label.setVisible(true);
//                     return;
//                 }

//                 if(!shouldBeSelected && !square.isExcluded()) {
//                     Hint_Label.setText("Incorrect!");
//                     Hint_Label.setVisible(true);
//                     return;
//                 }
//             }
//         }

//         Hint_Label.setText("Correct!");
//         Hint_Label.setVisible(true);
//         SubmitPuzzle_Button.setVisible(false);
//     }

//     //Removes all error highlights 
//     //Used after highlights have been acknowledged
//     public void clearAllErrorHighlights() {
//         for(int row = 0; row < 8; row++) {
//             for(int col = 0; col < 8; col++) {
//                 if(puzzleSquares[row][col] != null) {
//                     puzzleSquares[row][col].setError(false);
//                 }
//             }
//         }

//     }

//     //Resets Hint label back to initial and hides it
//     public void clearHintLabel() {
//         Hint_Label.setVisible(false);
//         Hint_Label.setText("");
//     }

//     //Checks for puzzle completion
//     //All squares filled
//     //Then enables Submit button
//     private void checkCompletion() {
//         for(int row = 0; row < 8; row++) {
//             for(int col = 0; col < 8; col++) {
//                 if(puzzleSquares[row][col] != null && puzzleSquares[row][col].isEmpty()) {
//                     SubmitPuzzle_Button.setVisible(false);
//                     return;
//                 }
//             } 
//         }

//         SubmitPuzzle_Button.setVisible(true);
//     }

//     //Called when a square changes state
//     public void squareStateChange() {
//         checkCompletion();
//     }
// }
