package Bills;
import Materials.*;

public class BillOfBase extends BillOfMaterials {

    public BillOfBase() {
        super();
        this.getBill().put(Iron.class, 3);
        this.getBill().put(Uranium.class, 3);
        this.getBill().put(Ice.class, 3);
        this.getBill().put(Coal.class, 3);
    }
}
