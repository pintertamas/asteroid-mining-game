package Bills;
import Materials.*;

public class BillOfRobot extends BillOfMaterials {

    public BillOfRobot() {
        super();
        this.addToBill(new Iron(), 1);
        this.addToBill(new Uranium(), 1);
        this.addToBill(new Coal(), 1);
    }
}
