package Bills;
import Materials.*;

public class BillOfRobot extends BillOfMaterials {

    public BillOfRobot() {
        super();
        this.getBill().put(Iron.class, 1);
        this.getBill().put(Uranium.class, 1);
        this.getBill().put(Coal.class, 1);
    }
}
