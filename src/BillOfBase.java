public class BillOfBase extends BillOfMaterials {

    public BillOfBase() {
        super();
        this.bill.put(new Iron(), 3);
        this.bill.put(new Uranium(), 3);
        this.bill.put(new Ice(), 3);
        this.bill.put(new Coal(), 3);
    }
}
