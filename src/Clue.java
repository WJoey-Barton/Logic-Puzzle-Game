/** Bandana kadel
 * clue
 * This class represent the single clue given in the puzzle.
 * It stores the text of the clue that is shown to the player
 * in the clue section next to the puzzle.
 */


public class Clue {

    //The actual text of the clue 
    private String content;

    //Sets the clue text
    public Clue(String content) {
        this.content = content;
    }

    //Returns the clue text
    public String getContent() {
        return content;
    }

    //Updates the clue text if needed
    public void setContent(String content) {
        this.content = content;
    }

    //Returns the clue text when printed
    @Override
    public String toString() {
        return content;
    }
}
