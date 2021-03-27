package Bills;
import Materials.*;

public class BillOfPortal extends BillOfMaterials {

    public BillOfPortal() {
        super();
        this.getBill().put(Iron.class, 2);
        this.getBill().put(Ice.class, 1);
        this.getBill().put(Uranium.class, 1);
    }
}


