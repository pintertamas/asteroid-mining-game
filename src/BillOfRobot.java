public class BillOfRobot extends BillOfMaterials {

    public BillOfRobot() {
        super();
        this.bill.put(Iron.class, 1);
        this.bill.put(Uranium.class, 1);
        this.bill.put(Coal.class, 1);
    }
}
