package Bills;
import Materials.*;

/**
 * A robot megépítéséhez tartozó recept.
 * A BillOfMaterials leszármazottja.
 */
public class BillOfRobot extends BillOfMaterials {

    /**
     * Konstruktor.
     */
    public BillOfRobot() {
        super();
        this.addToBill(new Iron(), 1);
        this.addToBill(new Uranium(), 1);
        this.addToBill(new Coal(), 1);
    }
}
