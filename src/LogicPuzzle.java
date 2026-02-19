/** Luke Johnson
 * LogicPuzzle
 * This class contains all the information each instance of a puzzle will have:
 * title:
 * Stores the literal values that will be used in each different puzzle
 */

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class LogicPuzzle {

    private String title;
    private List<Category> categories;
    private List<Clue> clues;
    private List<Hint> hints;
    private Solution solution;


    public LogicPuzzle(String title, List<Category> categories,
                       List<Clue> clues, List<Hint> hints, Solution solution) {
        this.title = title;
        this.categories = categories;
        this.clues = clues;
        this.hints = hints;
        this.solution = solution;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Clue> getClues() {
        return clues;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public Solution getSolution() {
        return solution;
    }


    // Categories and their inner values are initialized
    List<String> daysList1 = List.of("October 3", "October 4", "October 5", "October 6");
    Category days1 = new Category("Days", daysList1);
    List<String> requestsList1 = List.of("Clown", "Magician", "Photo Booth", "Rock Band");
    Category requests1 = new Category("Requests", requestsList1);
    List<String> familiesList1 = List.of("Benton", "Ford", "O'Connor", "Underwood");
    Category families1 = new Category("Families", familiesList1);
    List<Category> catList1 = List.of(days1, requests1, families1);


    // Clues are initialized with their content
    Clue clue1_1 = new Clue("The Underwood family party is 2 days " +
            "before the booking that requested the clown.");
    Clue clue1_2 = new Clue("The booking that requested the rock " +
            "band is for the Underwoods.");
    Clue clue1_3 = new Clue("The booking that requested the photo booth" +
            " is 1 day before the O'Connor family party.");
    Clue clue1_4 = new Clue("The Benton family event is sometime " +
            "after the event that requested the magician.");


    // Hints are initizalized with the row and column indexes of the highlighted
    // square, a boolean for type of hint, and the actual message it contains
    Hint hint1_1 = new Hint(4, 1, false, "If magician is" +
            " greater than Benton, then magician does not equal Benton. Mark the highlighted cell as FALSE");
    Hint hint1_2 = new Hint(3, 1, false, "If Benton is greater than magician" +
            ", then magician cannot equal the largest value (October 6) in the set." +
            " Mark the highlighted cell as FALSE.");
    Hint hint1_3 = new Hint(0, 4, false, "If Benton is greater than magician," +
            " then Benton cannot equal the smallest value (October 3) in the set." +
            " Mark the highlighted cell as FALSE.");
    Hint hint1_4 = new Hint(6, 2, false, "If photo booth is less than O'Connor by some specific amount," +
            " then photo booth does not equal O'Connor. Mark the highlighted cell as FALSE.");
    Hint hint1_5 = new Hint(2, 7, false " If Underwood is 2 steps less than clown, then Underwood cannot" +
            " equal October 5. Otherwise, clown would have to be larger than the largest item " +
            "in the set (October 6). Mark the highlighted cell as FALSE.");



    //Stuck here, solution is: October 3 -> Magician, Ford
    // October 4 -> Rock Band, Underwood
    // October 5 -> Photo Booth, Benton
    // October 6 -> Clown, O'Connor
    // The way I was thinking to map the correct solutions is to have the main key as a category value,
    // then the inner keys are the other two categories, mapped to the correct value in their respective categories
    HashMap<String, HashMap<Category, String>> puzzle1Pairs = new HashMap<>();
    HashMap<Category, String> inner1Pair = new HashMap<>();
    inner1Pair.put(requests1, "Magician");

    puzzle1Pairs.put("October 3", );
    Solution solution1 = new Solution(puzzle1Pairs);


    List<Clue> cluesList1 = List.of(clue1_1, clue1_2, clue1_3, clue1_4);

    List<Hint> hintsList1 = List.of(hint1_1, hint1_2, hint1_3, hint1_4, hint1_5);

    LogicPuzzle easyPuzzle1 = new LogicPuzzle("Easy3x4 #1", catList1, cluesList1, hintsList1, solution1);



    }

