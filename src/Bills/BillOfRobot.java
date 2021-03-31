package Bills;
import Materials.*;

/**
 * A robot megépítéséhez tartozó recept.
 * A BillOfMaterials leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class BillOfRobot extends BillOfMaterials {

    /**
     * Konstruktor.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public BillOfRobot() {
        super();
        this.addToBill(new Iron(), 1);
        this.addToBill(new Uranium(), 1);
        this.addToBill(new Coal(), 1);
    }
}
