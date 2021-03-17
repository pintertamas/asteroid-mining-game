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

        int index = 0;
        System.out.print(index + " ");
        System.out.println(this.getClass().getName() + " " + this + " move(): void");
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors(index);
        this.asteroid.removeFigure(this, index);
        neighbors.get(0).addFigure(this, index);
        this.setAsteroid(neighbors.get(0), index);
        this.setRoundFinished(true, index);
        System.out.println(index + " " + this.getClass().getName() + " " + this + " move() returned");
    }

    public void setRoundFinished(boolean roundFinished, int index) {
        index++;
        for (int i = 0; i < index; i++)
            System.out.print("\t");

        System.out.print(index + " ");
        System.out.println(this.getClass().getName() + " " + this + " setRoundFinished(" + roundFinished +  "): void");

        for (int i = 0; i < index; i++)
            System.out.print("\t");
        System.out.print(index + " ");

        System.out.println(this.getClass().getName() + " " + this + " setRoundFinished(" + roundFinished + ") returned");
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
