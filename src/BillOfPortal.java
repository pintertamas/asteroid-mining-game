public class BillOfPortal extends BillOfMaterials{

    public BillOfPortal() {
        super();
        this.bill.put(new Iron(), 2);
        this.bill.put(new Ice(), 1);
        this.bill.put(new Uranium(), 1);
    }
}
