/** Luke Johnson
 * Category
 * This class defines a category which has:
 * name: Label of a category
 * catValues: List of values that are stored in a category
 */

import java.util.List;

public class Category {

    private String name;
    private List<String> catValues;

    // Constructor to create and define new categories
    public Category(String name, List<String> catValues) {
        this.name = name;
        this.catValues = catValues;
    }

    // Getter method to access category name
    public String getName() {
        return name;
    }

    // Getter method to access the list of values within the category
    public List<String> getCatValues() {
        return catValues;
    }

}