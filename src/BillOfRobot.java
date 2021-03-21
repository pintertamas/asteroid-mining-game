public class BillOfRobot extends BillOfMaterials {

    public BillOfRobot() {
        super();
        this.bill.put(new Iron(), 1);
        this.bill.put(new Uranium(), 1);
        this.bill.put(new Coal(), 1);
    }
}
