public class BillOfBase extends BillOfMaterials{

    public BillOfBase() {
        super();
        this.materials.put(new Iron(), 3);
        this.materials.put(new Uranium(), 3);
        this.materials.put(new Ice(), 3);
        this.materials.put(new Coal(), 3);
    }
}
