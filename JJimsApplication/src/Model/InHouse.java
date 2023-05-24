package Model;

/**
 * A class that inherits part, and further defines a part's properties
 *
 */
public class InHouse extends Part {

    //Field Declarations
    /**
     * Machine id for parts
     */
    private int machineId;
    /**
     * A classification of part
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
     * @param machineId
     * @param isInHouse
     */
    //Constructor with call to superClass Model.Part
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId, boolean isInHouse) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;//This line is essential to passing companyName into ModifyPartsForm
        this.isInHouse = isInHouse;//See comment above
    }

    /**
     * method to retrieve machine Id
     *
     * @return machineId
     */
    public int getMachineId() {

        return machineId;
    }

    /**
     * method for setting machine Id
     *
     * @param machineId
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }
}