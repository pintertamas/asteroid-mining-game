import java.util.ArrayList;

public class BillOfPortal extends BillOfMaterials{

    public BillOfPortal() {
        super();
        this.materials.put(new Iron(), 2);
        this.materials.put(new Ice(), 1);
        this.materials.put(new Uranium(), 1);
    }
}
