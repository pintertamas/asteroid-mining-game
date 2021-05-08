package Bills;
import Materials.*;

/**
 * A teleportkapu-pár megépítéséhez tartozó recept.
 * A BillOfMaterials leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class BillOfPortal extends BillOfMaterials {

    /**
     * Konstruktor.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public BillOfPortal() {
        super();
        this.addToBill(new Iron(), 2);
        this.addToBill(new Ice(), 1);
        this.addToBill(new Uranium(), 1);
    }
}
