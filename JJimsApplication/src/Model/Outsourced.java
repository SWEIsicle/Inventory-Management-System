package Model;

/**
 * A class that inherits part, and further defines a part's properties
 *
 */
public class Outsourced extends Part {

    /**
     * declares a string companyName for outsourced parts
     */
    private String companyName;

    /**
     * declares a boolean to help classify part
     */
    private boolean isInHouse;//Auto generated but needed for same reason as comments below.

    /**
     * Constructor for Data parameters for InHouse parts.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     * @param isInHouse
     */
    //Constructor with call to superClass Model.Part
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName, boolean isInHouse) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;//This line is essential to passing companyName into ModifyPartsForm
        this.isInHouse = isInHouse;//See comment above
    }

    /**
     * method for retrieving company name
     *
     * @return companyName
     */
    //Method Declarations
    public String getCompanyName() {

        return companyName;
    }

    /**
     * method for setting company name
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}

