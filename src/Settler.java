import java.util.ArrayList;

public class Settler extends Figure {
    private Inventory inventory;
    private BillOfMaterials billOfMaterials;

    public Settler(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    @Override
    public void move() {
        System.out.println("Settler moves to neighbor:");

        TestLogger.functionCalled(this, "move", "void");
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        this.asteroid.removeFigure(this);
        neighbors.get(0).addFigure(this);
        this.setAsteroid(neighbors.get(0));
        this.setRoundFinished(true);
        TestLogger.functionReturned();
    }

    public void setRoundFinished(boolean roundFinished) {
        TestLogger.functionCalled(this, "setRoundFinished", "void");
        TestLogger.functionReturned();

        this.roundFinished = roundFinished;
    }

    public boolean mine() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "mine", "", "boolean");
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public void buildPortal() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "buildPortal", "", "void");
        TestLogger.functionReturned();
        Portal p1 = new Portal();
        Portal p2 = new Portal();
        this.inventory.addPortal(p1);
        this.inventory.addPortal(p2);

        //TODO Levonni az építésért a költségeket meg csekkolni, hogy vane elég pénze.
    }

    public void buildRobot() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "buildRobot", "", "void");
        TestLogger.functionReturned();
        Robot r = new Robot(this.asteroid, false);
        //TODO Van-e elég pénz és ha igen akkor levonni.
    }

    public void buildBase() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "buildBase", "", "void");
        TestLogger.functionReturned();
        //TODO csekkolni van-e elég pénz és ha igen akkor nyertek.
    }

    public void putPortalDown() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "putPortalDown", "", "void");
        TestLogger.functionReturned();

    }

    public boolean putMaterialBack() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "putMaterialBack", "", "boolean");
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public Material chooseMaterial() {
        return new Iron();
    }

    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "onExplosion", "", "void");
        TestLogger.functionReturned();
    }

    @Override
    public void step() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "step", "", "void");
        TestLogger.functionReturned();
    }
}
