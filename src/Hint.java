/** Bandana kadel
 * Hint
 * This class represents the hint provided to players in the puzzle.
 * Stores the square hint is located at and what mark is expected
 * and messages shown to players
 */


public class Hint {

    private int row;  //Row index for square
    private int col; //Column index for square

    // True = Selected (O) , False= Excluded (X)
    private boolean shouldBeSelected;

   
    private String message; // Display the hint message to the player. 

    //Constructor sets all information about hint
    public Hint(int row, int col, boolean shouldBeSelected, String message) {
        this.row = row;
        this.col = col;
        this.shouldBeSelected = shouldBeSelected;
        this.message = message;
    }

    //Returns the row of the hint square
    public int getRow() {
        return row;
    }

    //Returns the column of the hint square
    public int getCol() {
        return col;
    }

    //Returns the X or O whatever is in the square
    public boolean shouldBeSelected() {
        return shouldBeSelected;
    }

    //Returns the message
    public String getMessage() {
        return message;
    }

    //Returns the hint object
    public Hint getHint() {
        return this;
    }

    //Updates the hint message if needed.
    public void setMessage(String message) {
        this.message = message;
    }
}
