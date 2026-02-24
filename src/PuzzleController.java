/** Joey Barton
 * PuzzleController
 * This class manages the GUI
 * This class builds the puzzle grid and category labels
 * Manages PuzzleSquareUI objects, their interactions, and states
 * Manages user actions such as hints, clear errors, resetting puzzle, and submitting the solution
 * Manages timer as well as penalties toward the user completion time
*/
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class PuzzleController {

    @FXML private Label Title_Label;

    //Category Labels
    @FXML private Label Cat1_Label;
    @FXML private Label Cat2_Label;
    @FXML private Label Cat3_Label;
    @FXML private Label Cat22_Label;

    //Category1 Value Labels
    @FXML private Label Cat1V1_Label;
    @FXML private Label Cat1V2_Label;
    @FXML private Label Cat1V3_Label;
    @FXML private Label Cat1V4_Label;

    //Category2 Value Labels
    @FXML private Label Cat2V1_Label;
    @FXML private Label Cat2V2_Label;
    @FXML private Label Cat2V3_Label;
    @FXML private Label Cat2V4_Label;

    //Category3 Value Labels
    @FXML private Label Cat3V1_Label;
    @FXML private Label Cat3V2_Label;
    @FXML private Label Cat3V3_Label;
    @FXML private Label Cat3V4_Label;

    //Category22 Value Labels
    @FXML private Label Cat22V1_Label;
    @FXML private Label Cat22V2_Label;
    @FXML private Label Cat22V3_Label;
    @FXML private Label Cat22V4_Label;

    //Clue Labels
    @FXML private Label Clue1_Label;
    @FXML private Label Clue2_Label;
    @FXML private Label Clue3_Label;
    @FXML private Label Clue4_Label;
    @FXML private Label Clue5_Label;

    //Hint Label
    @FXML private Label Hint_Label;

    //Timer Label
    @FXML private Label Timer_Label;
    
    //Buttons
    @FXML private Button Hint_Button;
    @FXML private Button ClearErrors_Button;
    @FXML private Button StartOver_Button;
    @FXML private Button Submit_Button;

    //Puzzle grid container
    @FXML private GridPane Puzzle_GridPane;
    
    //8x8 array of PuzzleSquareUI object (some unused)
    private PuzzleSquareUI[][] puzzleSquares = new PuzzleSquareUI[8][8];

    //Puzzle model
    private LogicPuzzle puzzle;
    private List<CategoryComparison> comparisons;

    //Tracks hint iteration
    private int hintIndex = 0;

    //Tracks the currently highlighted square
    private PuzzleSquareUI currentlyHighlightedSquare = null;

    //Timer management
    private int elapsedSeconds = 0;
    private Timeline timer;
    
    //Constants used for UI and timer
    private static final double SQUARE_SIZE = 48;
    private static final int HINT_PENALTY = 120;
    private static final int CLEAR_ERRORS_PENALTY = 120;

    //Required class for JavaFX
    //Sets up puzzle and GUI
    //Starts the timer
    @FXML
    private void initialize() {

        puzzle = LogicPuzzle.buildPuzzle1();
        comparisons = puzzle.getComparisons();

        populateLabels();
        printGrid();

        startTimer();

        Submit_Button.setVisible(false);
    }

    //Builds the grid
    //Three quadrants, Top Left, Top Right, and Bottom Left
    private void printGrid() {

        //Keep all columns and rows exactly the same size
        for(int i = 0; i < 8; i++) {
            javafx.scene.layout.ColumnConstraints cc = new javafx.scene.layout.ColumnConstraints();
            cc.setMinWidth(SQUARE_SIZE);
            cc.setMaxWidth(SQUARE_SIZE);
            cc.setPrefWidth(SQUARE_SIZE);
            Puzzle_GridPane.getColumnConstraints().add(cc);

            javafx.scene.layout.RowConstraints rc = new javafx.scene.layout.RowConstraints();
            rc.setMinHeight(SQUARE_SIZE);
            rc.setMaxHeight(SQUARE_SIZE);
            rc.setPrefHeight(SQUARE_SIZE);
            Puzzle_GridPane.getRowConstraints().add(rc);
        }

        //Top left. First / Third categories, 4 values.
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                addSquare(row, col);
            }
        }

        //Top right. Second category, 4 values
        for(int row = 0; row < 4; row++) {
            for(int col = 4; col < 8; col++) {
                addSquare(row, col);
            }
        }

        //Bottom left. Second category, 4 values
        for(int row = 4; row < 8; row++) {
            for(int col = 0; col < 4; col++) {
                addSquare(row, col);
            }
        }
    }

    //Creates a PuzzleSquareUI at the given position
    //Adds it to the GUI GridPane
    private void addSquare(int row, int col) {
        PuzzleSquareUI square = new PuzzleSquareUI(row, col, this);
        puzzleSquares[row][col] = square;
        Puzzle_GridPane.add(square, col, row);  
    }

    //Populates all labels using data from LogicPuzzle
    private void populateLabels() {
        //Category Labels
        Cat1_Label.setText(comparisons.get(0).getColCategory().getName());
        Cat2_Label.setText(comparisons.get(1).getColCategory().getName());
        Cat3_Label.setText(comparisons.get(0).getRowCategory().getName());
        Cat22_Label.setText(comparisons.get(2).getRowCategory().getName());

        //Category 1 Values
        List<String> cat1Values = comparisons.get(0).getColCategory().getCatValues();
        Cat1V1_Label.setText(cat1Values.get(0));
        Cat1V2_Label.setText(cat1Values.get(1));
        Cat1V3_Label.setText(cat1Values.get(2));
        Cat1V4_Label.setText(cat1Values.get(3));

        //Category 2 Values
        List<String> cat2Values = comparisons.get(1).getColCategory().getCatValues();
        Cat2V1_Label.setText(cat2Values.get(0));
        Cat2V2_Label.setText(cat2Values.get(1));
        Cat2V3_Label.setText(cat2Values.get(2));
        Cat2V4_Label.setText(cat2Values.get(3));

        //Category 3 Values
        List<String> cat3Values = comparisons.get(0).getRowCategory().getCatValues();
        Cat3V1_Label.setText(cat3Values.get(0));
        Cat3V2_Label.setText(cat3Values.get(1));
        Cat3V3_Label.setText(cat3Values.get(2));
        Cat3V4_Label.setText(cat3Values.get(3));

        //Category 2_2 Values
        List<String> cat22Values = comparisons.get(2).getRowCategory().getCatValues();
        Cat22V1_Label.setText(cat22Values.get(0));
        Cat22V2_Label.setText(cat22Values.get(1));
        Cat22V3_Label.setText(cat22Values.get(2));
        Cat22V4_Label.setText(cat22Values.get(3));

        //Clues
        //There should be atleast 4 clues
        //But there's space for 5 clues
        List<Clue> clues = puzzle.getClues();
        Clue1_Label.setText("1: " + clues.get(0).getContent());
        Clue2_Label.setText("2: " + clues.get(1).getContent());
        Clue3_Label.setText("3: " + clues.get(2).getContent());
        Clue4_Label.setText("4: " + clues.get(3).getContent());

        if(clues.size() >= 5) {
            Clue5_Label.setText("5: " + clues.get(4).getContent()); 
        }
        
        
        
    }
    
    //Presents the user with a hint
    //Advances to next unused hint
    //Skips hints that are already correctly filled in
    //Adds a time penalty to the Timer
    @FXML
    private void HintButton_clicked() {

        elapsedSeconds += HINT_PENALTY;

        //Back to back Hint requests
        //Clear the first highlight
        if(currentlyHighlightedSquare != null) {
            currentlyHighlightedSquare.setHighlight(false);
            currentlyHighlightedSquare = null;
        }

        List<Hint> hints = puzzle.getHints();

        while(hintIndex < hints.size()) {
            Hint hint = hints.get(hintIndex);
            hintIndex++;

            PuzzleSquareUI square = puzzleSquares[hint.getRow()][hint.getCol()];
            
            //Determine if the square is already correct
            boolean isCorrect = (hint.shouldBeSelected() && square.isSelected() 
                                || !hint.shouldBeSelected() && square.isExcluded());

            
            if(!isCorrect && square.isEmpty()) {
                square.setHighlight(true);
                currentlyHighlightedSquare = square;
                Hint_Label.setText("HINT: " + hint.getMessage());
                Hint_Label.setVisible(true);
                return;
            }
        }

        Hint_Label.setText("HINT: No more hints available!");
        Hint_Label.setVisible(true);
    }   

    //Checks all squares that conflict with the solution
    //Highlights, and clears the incorrect squares
    //Displays number of errors
    //Adds a time penalty to the Timer
    @FXML
    private void ClearErrorsButton_clicked() {

        boolean foundErrors = false;
        int numOfErrors = 0;

        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(puzzleSquares[row][col] == null) { 
                    continue;
                }

                String gridKey = getGridKey(row, col);
                if(gridKey == null) {
                    continue;
                }
                String rowValue = getRowValue(row, col);
                String colValue = getColValue(row, col);

                boolean shouldBeSelected = puzzle.getSolution().checkGuess(gridKey, rowValue, colValue);

                PuzzleSquareUI square = puzzleSquares[row][col];

                if(shouldBeSelected && square.isExcluded()) {
                    square.clearErrorSquare();
                    foundErrors = true;
                    numOfErrors++;
                } else if(!shouldBeSelected && square.isSelected()) {
                    square.clearErrorSquare();
                    foundErrors = true;
                    numOfErrors++;
                }
            }
        } 

        
        

        if(!foundErrors) {
            Hint_Label.setText("No errors found!");
        } else {
            int penalty = CLEAR_ERRORS_PENALTY * numOfErrors;
            elapsedSeconds += penalty;
            Hint_Label.setText(numOfErrors + " errors: " + "+" + penalty + " sec. penalty");
            checkCompletion();
        }

        Hint_Label.setVisible(true);
    }
    
    //Rests all puzzle squares to initial, empty state
    //Resets timer to zero
    @FXML
    private void StartOverButton_clicked() {
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(puzzleSquares[row][col] != null) {
                    puzzleSquares[row][col].resetSquare();
                }
            }
        }

        Submit_Button.setVisible(false);
        hintIndex = 0;
        clearHintLabel();
        clearAllErrorHighlights();
        currentlyHighlightedSquare = null;
    }

    //Checks the user solution against official solution
    @FXML
    private void SubmitButton_clicked() {
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(puzzleSquares[row][col] == null) {
                    continue;
                }
                String gridKey = getGridKey(row, col);
                if(gridKey == null) {
                    continue;
                }

                String rowValue = getRowValue(row, col);
                String colValue = getColValue(row, col);

                boolean shouldBeSelected = puzzle.getSolution().checkGuess(gridKey, rowValue, colValue);

                PuzzleSquareUI square = puzzleSquares[row][col];

                if(shouldBeSelected && !square.isSelected()) {
                    Hint_Label.setText("Incorrect!");
                    Hint_Label.setVisible(true);
                    return;
                }

                if(!shouldBeSelected && !square.isExcluded()) {
                    Hint_Label.setText("Incorrect!");
                    Hint_Label.setVisible(true);
                    return;
                }
            }
        }

        timer.stop();

        Hint_Label.setText("Correct! Solved in " + String.format("%02d:%02d",elapsedSeconds / 60, elapsedSeconds % 60));
        Hint_Label.setVisible(true);
        Submit_Button.setVisible(false);
    }

    //Checks for puzzle completion
    //All squares filled
    //Then shows Submit button on GUI
    //Submit button is hidden otherwise
    private void checkCompletion() {
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(puzzleSquares[row][col] != null && puzzleSquares[row][col].isEmpty()) {
                    Submit_Button.setVisible(false);
                    return;
                }
            } 
        }

        Submit_Button.setVisible(true);
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
    
    //Resets Hint label back to empty and hides it
    public void clearHintLabel() {
        Hint_Label.setVisible(false);
        Hint_Label.setText("");
    }

    //Determines which quadrant a square belongs to
    //Returns 0 - top-left
    //Returns 1 - top-right
    //Returns 2 - bottom-left
    //Returns -1 - unused bottom-right
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

        //Bottom right is not needed.
        return -1;
    }

    //Returns the solution grid key for the square's quadrant
    private String getGridKey(int row, int col) {
        int gridIndex = getGridIndex(row, col);
        if(gridIndex == -1) {
            return null;
        }
        return comparisons.get(gridIndex).getGridKey();
    }

    //Returns the row category value for the given square's position
    private String getRowValue(int row, int col) {
        int gridIndex = getGridIndex(row, col);
        if(gridIndex == -1) {
            return null;
        }

        //% 4 to convert from complete grid to local 4x4 grid
        return comparisons.get(gridIndex).getRowValue(row % 4);
    }

    //Returns the column category value for the given square's position
    private String getColValue(int row, int col) {
        int gridIndex = getGridIndex(row, col);
        if(gridIndex == -1) {
            return null;
        }

        //% 4 to convert from complete grid to local 4x4 grid
        return comparisons.get(gridIndex).getColValue(col % 4);
    }
    
    //Automatically excludes all other squares in the same row and column quadrant
    //Used when a square is selected
    public void onSquareSelected(int row, int col) {

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
    
    //Called when a square changes state
    public void squareStateChange() {
        checkCompletion();
    }
    
    //Starts the timer
    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            elapsedSeconds++;
            updateTimerLabel();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    //Formats elapsedSeconds to mm:ss and updates the Timer_Label
    private void updateTimerLabel() {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        Timer_Label.setText(String.format("%02d:%02d", minutes, seconds));
    }
    
    


    
}
