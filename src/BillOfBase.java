public class BillOfBase extends BillOfMaterials {

    public BillOfBase() {
        super();
        this.bill.put(Iron.class, 3);
        this.bill.put(Uranium.class, 3);
        this.bill.put(Ice.class, 3);
        this.bill.put(Coal.class, 3);
    }
}
