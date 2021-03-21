public class BillOfRobot extends BillOfMaterials{

    public BillOfRobot() {
        super();
        this.materials.put(new Iron(), 1);
        this.materials.put(new Uranium(), 1);
        this.materials.put(new Coal(), 1);
    }
}
