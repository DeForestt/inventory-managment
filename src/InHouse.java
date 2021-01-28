/**
 * Child part class for any part that needs to be tracked by the machine that it was made with
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * constructor
     * @param id ID
     * @param name Name
     * @param price Product price or cost
     * @param stock Inventory Level
     * @param min minimum allowed inventory
     * @param max maximum allowed inventory
     * @param machineId Identifier for the machine used to produce the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the machineID
     * @param machineId INT ID to be set to
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Gets the machineID
     * @return MachineID
     */
    public int getMachineId() {
        return machineId;
    }
}
