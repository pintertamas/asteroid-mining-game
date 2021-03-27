package Bills;
import Materials.*;

public class BillOfBase extends BillOfMaterials {

    public BillOfBase() {
        super();
        this.addToBill(new Iron(), 3);
        this.addToBill(new Uranium(), 3);
        this.addToBill(new Ice(), 3);
        this.addToBill(new Coal(), 3);
    }
}
