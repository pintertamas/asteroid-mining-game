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

        TestLogger.functionCalled(this, "move", "", "void");
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        this.asteroid.removeFigure(this);
        neighbors.get(0).addFigure(this);
        this.setAsteroid(neighbors.get(0));
        this.setRoundFinished(true);
        TestLogger.functionReturned();
    }

    public void setRoundFinished(boolean roundFinished) {
        TestLogger.functionCalled(this, "setRoundFinished", "", "void");
        TestLogger.functionReturned();

        this.roundFinished = roundFinished;
    }

    public boolean mine() {
        return true;
    }

    public void buildPortal() {
    }

    public void buildRobot() {
    }

    public void buildBase() {
    }

    public void putPortalDown() {
    }

    public boolean putMaterialBack() {
        return true;
    }

    public Material chooseMaterial() {
        return new Iron();
    }

    @Override
    public void onExplosion() {
    }

    @Override
    public void step() {
    }
}
