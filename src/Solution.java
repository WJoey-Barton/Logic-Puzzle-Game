/** Bandana kadel
 * Solution
 * This class stores the correct answers for the puzzle
 * Tracks which row value matches which column value
 * for each grid in the puzzle.
 */
import java.util.HashMap;
import java.util.Map;

public class Solution {

    //Stores correct matches
    private Map<String, Map<String, String>> correctPairs = new HashMap<>();

    //Adds correct matches to the solution
    //gridkey identifies which grid the match is for. 
    public void putPair(String gridKey, String rowValue, String colValue) {
        correctPairs.computeIfAbsent(gridKey, k -> new HashMap<>())
                    .put(rowValue, colValue);
    }

    //returns true if the solution contains at least one correct pair
    public boolean isCorrect() {
        return !correctPairs.isEmpty();
    }

    //checks if the guess is correct.
    //Returns true if the row value matches the column value.
    public boolean checkGuess(String gridKey, String rowValue, String colValue) {
        if (!correctPairs.containsKey(gridKey)) return false;

        String expected = correctPairs.get(gridKey).get(rowValue);
        if (expected == null) return false;

        return expected.equals(colValue);
    }
}
