import java.util.ArrayList;

public class BillOfPortal extends BillOfMaterials{

    public BillOfPortal() {
        super();
        this.materials.add(new Iron());
        this.materials.add(new Iron());
        this.materials.add(new Ice());
        this.materials.add(new Uranium());
    }
}
