/**Joey Barton
 * PuzzleSquareUI 
 * 
 * This class represents a single interactive square.
 * This class handles rendering visual components, 
 * cycling between states, and updating visuals when states change
**/
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class PuzzleSquareUI extends StackPane {

    //All states of an individual PuzzleSquare
    private boolean isSelected;
    private boolean isEmpty;
    private boolean isExcluded;
    private boolean isAutoExcluded;
    private boolean isHighlighted;
    private boolean isError;

    //Visual components
    private Rectangle border;
    private Circle greenCircle;
    private Line line1;
    private Line line2;

    //Visual constants
    private static final double SQUARE_SIZE = 42;
    private static final double GREEN_CIRCLE_RADIUS = 18;
    private static final double X_SIZE = 20;

    //Position in puzzle grid
    private int row;
    private int col;

    private SceneController controller;

    //Constructor for puzzle square
    public PuzzleSquareUI(int row, int col, SceneController controller) {

        this.row = row;
        this.col = col;
        this.controller = controller;

        //Initializes states
        this.isEmpty = true;
        this.isExcluded = false;
        this.isSelected = false;
        this.isAutoExcluded = false;
        this.isHighlighted = false;
        this.isError = false;

        //Sets visual component's size
        setPrefSize(SQUARE_SIZE, SQUARE_SIZE);
        setMinSize(SQUARE_SIZE, SQUARE_SIZE);
        setMaxSize(SQUARE_SIZE, SQUARE_SIZE);

        //Builds visual components
        createBorder();
        createGreenCircle();
        createXMark();

        //Setup for click behavior
        squarePane_clicked();

        //Renders GUI
        updateVisual();

    }

    //Creates the square
    private void createBorder() {
        border = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
        border.setFill(Color.WHITE);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(1);
        getChildren().add(border);
    }

    //Create the green circle used when selecting a square
    private void createGreenCircle() {
        greenCircle = new Circle(GREEN_CIRCLE_RADIUS);
        greenCircle.setFill(Color.LIGHTGREEN);
        greenCircle.setStroke(Color.BLACK);
        greenCircle.setStrokeWidth(2);
        greenCircle.setVisible(false);
        getChildren().add(greenCircle);
    }

    //Create the red x used when excluding a square
    private void createXMark() {
        line1 = new Line(-X_SIZE/2, -X_SIZE/2, X_SIZE/2, X_SIZE/2);
        line2 = new Line(X_SIZE/2, -X_SIZE/2, -X_SIZE/2, X_SIZE/2);

        for(Line line : new Line[]{line1, line2}) {
            line.setStroke(Color.RED);
            line.setStrokeWidth(3);
            line.setStrokeLineCap(StrokeLineCap.ROUND);
            line.setVisible(false);
        }

        getChildren().addAll(line1, line2);
    }

    //Click handler for the square
    @FXML
    private void squarePane_clicked() {

        setOnMouseClicked(event -> {

            //Blocks user from changing AutoExcluded squares
            if(!isAutoExcluded) {
                boolean wasSelected = isSelected;

                //When user clicks any square, after clicking "Clear Errors" button,
                //the error square/s will no longer be highlighted
                controller.clearAllErrorHighlights();

                //When user clicks any square, after getting a hint, 
                //the hint square will no longer be highlighted
                if(isHighlighted) {
                    setHighlight(false);
                    controller.clearHintLabel();
                }

                //Cycles through the three user states
                //(Excluded, Selected, Empty)
                cycleState();
                updateVisual();

                //Sends signal to controller of a change
                if(isSelected && !wasSelected) {
                    controller.onSquareSelected(row, col);
                } else if(!isSelected && wasSelected) {
                    controller.onSquareDeselected(row, col);
                } else {
                    controller.squareStateChange();
                }
            }
            
        });
    }

    //The square can have three states, in a specific order
    //Empty -> Excluded -> Selected -> Empty
    private void cycleState() {
        if (isEmpty) {
            isEmpty = false;
            isExcluded = true;
            isSelected = false;
        } else if(isExcluded) {
            isEmpty = false;
            isExcluded = false;
            isSelected = true;
        } else if(isSelected) {
            isEmpty = true;
            isExcluded = false;
            isSelected = false;
        }

    }

    //Automatically excludes the square
    //Used multiple times per change, to flip each non-selected square
    //in the row and column to excluded.
    public void autoExclude() {
        if(isEmpty && !isSelected) {
            isAutoExcluded = true;
            isExcluded = true;
            isEmpty = false;
            updateVisual();
        }
    }

    //Removes the automatic exclusion
    //Square returns to empty state
    //Used when a different square is de-selected
    public void removeAutoExclude() {
        if(isAutoExcluded) {
            isAutoExcluded = false;
            isExcluded = false;
            isEmpty = true;
            updateVisual();
        }
    }

    //Updates the GUI based on the square's state
    private void updateVisual() {

        //Remove all possible marks first
        greenCircle.setVisible(false);
        line1.setVisible(false);
        line2.setVisible(false);

        //Highlighting a square
        //Used for Hint and Clear Errors
        if(isHighlighted) {
            border.setFill(Color.YELLOW);
        } else if(isError) {
            border.setFill(Color.rgb(255, 100, 100));
        } else {
            border.setFill(Color.WHITE);
        }

        //Display correct visual
        if(isEmpty) {
            //Nothing to draw
        } else if (isExcluded) {
            line1.setVisible(true);
            line2.setVisible(true);
        } else if(isSelected) {
            greenCircle.setVisible(true);
        }

    }

    //Resets the square to empty state
    public void resetSquare() {
        isEmpty = true;
        isExcluded = false;
        isSelected = false;
        isAutoExcluded = false;
        updateVisual();
    }
    
    //Highlight for hints
    public void setHighlight(boolean highlight) {
        this.isHighlighted = highlight;
        updateVisual();
    }

    //Error Highlight for Clear Errors
    public void setError(boolean error) {
        this.isError = error;
        updateVisual();
    }

    //Removes the incorrect visual and marks as error
    //Used by Clear Error button
    public void clearErrorSquare() {

        //Remove autoExclusions if this was autoExcluded
        if(isAutoExcluded) {
            isAutoExcluded = false;
        }

        isEmpty = true;
        isExcluded = false;
        isSelected = false;
        isError = true;
        updateVisual();
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isExcluded() {
        return isExcluded;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
