/**Joey Barton
 * CategoryComparison
 * Represents a pairing between two categories
 * Generates a unique grid key used by the Solution class
 * Translates integer grid coordinates to the String values that
 * the Solution class expects
*/

public class CategoryComparison {

    private Category rowCategory;
    private Category colCategory;

    //Unique key for a comparison
    private String gridKey;

    //Constructs a CategoryComparison and generates a unique gridKey
    public CategoryComparison(Category rowCategory, Category colCategory) {
        this.rowCategory = rowCategory;
        this.colCategory = colCategory;
        this.gridKey = rowCategory.getName() + "Vs" + colCategory.getName();
    }

    public Category getRowCategory() {
        return rowCategory;
    }

    public Category getColCategory() {
        return colCategory;
    }

    public String getGridKey() {
        return gridKey;
    }

    //Get's a specific value from the row Category
    public String getRowValue(int index) {
        return rowCategory.getCatValues().get(index);
    }

    //Get's a specific value from the col Category
    public String getColValue(int index) {
        return colCategory.getCatValues().get(index);
    }
    
}
