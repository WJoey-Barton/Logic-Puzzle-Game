import java.util.List;

public class LogicPuzzle {

    private String title;
    private List<Category> categories;
    private List<CategoryComparison> comparisons;
    private List<Clue> clues;
    private List<Hint> hints;
    private Solution solution;

    private LogicPuzzle(String title, List<Category> categories, List<CategoryComparison> comparisons,
                    List<Clue> clues, List<Hint> hints, Solution solution) {

        this.title = title;
        this.categories = categories;
        this.comparisons = comparisons;
        this.clues = clues;
        this.hints = hints;
        this.solution = solution;
    }

    public String getTitle() {
        return title;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<CategoryComparison> getComparisons() {
        return comparisons;
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
        
    public static LogicPuzzle buildPuzzle1() {

        // Categories and their inner values are initialized
        List<String> daysList1 = List.of("October 3", "October 4", "October 5", "October 6");
        Category days1 = new Category("Days", daysList1);
        List<String> requestsList1 = List.of("Clown", "Magician", "Photo Booth", "Rock Band");
        Category requests1 = new Category("Requests", requestsList1);
        List<String> familiesList1 = List.of("Benton", "Ford", "O'Connor", "Underwood");
        Category families1 = new Category("Families", familiesList1);

        List<Category> categories = List.of(days1, requests1, families1);

        CategoryComparison daysVsFamilies = new CategoryComparison(days1, families1);
        CategoryComparison daysVsRequests = new CategoryComparison(days1, requests1);
        CategoryComparison requestsVsFamilies = new CategoryComparison(requests1, families1);

        List<CategoryComparison> comparisons = List.of(daysVsFamilies, daysVsRequests, requestsVsFamilies);

        List<Clue> clues = List.of(new Clue("The Underwood family party is 2 days before the booking that requested the clown."),
                                   new Clue("The booking that requested the rock band is for the Underwoods."), 
                                   new Clue("The booking that requested the photo booth is 1 day before the O'Connor family party."),
                                   new Clue("The Benton family event is sometime after the event that requested the magician.")
                                );

        List<Hint> hints = List.of(new Hint(4, 1, false, "If magician is" +
                                " greater than Benton, then magician does not equal Benton. Mark the highlighted cell as FALSE"),
                                    new Hint(3, 1, false, "If Benton is greater than magician" +
                                    ", then magician cannot equal the largest value (October 6) in the set." +
                                    " Mark the highlighted cell as FALSE."),
                                    new Hint(0, 4, false, "If Benton is greater than magician," +
                                    " then Benton cannot equal the smallest value (October 3) in the set." +
                                    " Mark the highlighted cell as FALSE."),
                                    new Hint(6, 2, false, "If photo booth is less than O'Connor by some specific amount," +
                                    " then photo booth does not equal O'Connor. Mark the highlighted cell as FALSE."),
                                    new Hint(2, 7, false, "If Underwood is 2 steps less than clown, then Underwood cannot" +
                                    " equal October 5. Otherwise, clown would have to be larger than the largest item " +
                                    "in the set (October 6). Mark the highlighted cell as FALSE.")
        );
        
        Solution solution = new Solution();
        String dvf = daysVsFamilies.getGridKey();
        String dvr = daysVsRequests.getGridKey();
        String rvf = requestsVsFamilies.getGridKey();

        solution.putPair(dvf, "October 3", "Ford");
        solution.putPair(dvf, "October 4", "Underwood");
        solution.putPair(dvf, "October 5", "Benton");
        solution.putPair(dvf, "October 6", "O'Connor");
        
        solution.putPair(dvr, "October 3", "Magician");
        solution.putPair(dvr, "October 4", "Rock Band");
        solution.putPair(dvr, "October 5", "Photo Booth");
        solution.putPair(dvr, "October 6", "Clown");
        
        solution.putPair(rvf, "Clown", "O'Connor");
        solution.putPair(rvf, "Magician", "Ford");
        solution.putPair(rvf, "Photo Booth", "Benton");
        solution.putPair(rvf, "Rock Band", "Underwood");
        

        return new LogicPuzzle("Easy Difficulty #1", categories, comparisons, clues, hints, solution);
    }
    
}
