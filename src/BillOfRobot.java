public class BillOfRobot extends BillOfMaterials{

    public BillOfRobot() {
        super();
        this.materials.add(new Iron());
        this.materials.add(new Uranium());
        this.materials.add(new Coal());
    }
}
