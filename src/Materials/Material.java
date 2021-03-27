package Materials;
import Entities.Settler;
import Playground.Asteroid;
import Test.TestLogger;

public abstract class Material {
    Asteroid asteroid;

    public Material() {
    }

    public void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        TestLogger.functionReturned();
    }

    public void addToInventory(Settler s) {
        TestLogger.functionCalled(this, "addToInventory", "Entities.Settler s", "void");
        s.getInventory().addMaterial(this);
        TestLogger.functionReturned();
    }

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }
}
