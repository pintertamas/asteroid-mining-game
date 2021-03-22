import java.util.ArrayList;

public class Settler extends Figure {
    private final Inventory inventory;

    public Settler(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
        this.inventory = new Inventory();
    }

    @Override
    public void move() {
        TestLogger.functionCalled(this, "move", "void");
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        this.asteroid.removeFigure(this);
        if (neighbors.size() > 0) {
            neighbors.get(0).addFigure(this);
            this.setAsteroid(neighbors.get(0));
            this.setRoundFinished(true);
        } else TestLogger.errorMessage("No neighbors found!");
        TestLogger.functionReturned();
    }

    public boolean mine() {
        TestLogger.functionCalled(this, "mine", "boolean");
        if (asteroid.mined(this)) {
            setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    @Override
    public Inventory getInventory() {
        TestLogger.functionCalled(this, "getInventory", "inventory");
        TestLogger.functionReturned("inventory");
        return inventory;

    }

    public boolean buildPortal() {
        TestLogger.functionCalled(this, "buildPortal", "boolean");
        BillOfPortal billOfPortal = new BillOfPortal();
        if (billOfPortal.hasEnoughMaterial(this.inventory.getMaterials())) {
            billOfPortal.pay(billOfPortal.bill);
            Portal p1 = new Portal();
            Portal p2 = new Portal();
            p1.setPair(p2);
            p2.setPair(p1);
            this.inventory.addPortal(p1);
            this.inventory.addPortal(p2);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public boolean buildRobot() {
        TestLogger.functionCalled(this, "buildRobot", "void");
        TestLogger.functionReturned();
        Robot r = new Robot(this.asteroid, false);
        //TODO Van-e elég pénz és ha igen akkor levonni.
        return true;
    }

    public boolean buildBase() {
        TestLogger.functionCalled(this, "buildBase", "void");
        BillOfBase billOfBase = new BillOfBase();
        if (billOfBase.hasEnoughMaterial(this.asteroid.summarizeMaterials())) {
            //TODO: WIN!
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public boolean putPortalDown() {
        TestLogger.functionCalled(this, "putPortalDown", "boolean");
        ArrayList<Portal> p = inventory.getPortals();
        if (p.size() == 2 || p.size() == 1) {
            this.asteroid.addPortal(p.get(0));
            this.inventory.removePortal(p.get(0));
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }


    //Javítja, elvileg kész
    public boolean putMaterialBack(Material m) {
        TestLogger.functionCalled(this, "putMaterialBack", "boolean");
        if (this.asteroid.coreChanged(m)) {
            this.inventory.removeMaterial(m);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public Material chooseMaterial() {
        return new Iron();
    }

    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this, "onExplosion", "void");
        this.die();
        TestLogger.functionReturned();
    }

    @Override
    public void step() {
        TestLogger.functionCalled(this, "step", "void");
        TestLogger.functionReturned();
    }
}
