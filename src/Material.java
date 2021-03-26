public abstract class Material {
    Asteroid asteroid;

    public Material() {
    }

    protected void readyToMine() {
        TestLogger.functionCalled(this, "readyToMine()", "void");
        TestLogger.functionReturned();
    }

    public void addToInventory(Settler s) {
        TestLogger.functionCalled(this, "addToInventory", "Settler s", "void");
        s.getInventory().addMaterial(this);
        TestLogger.functionReturned();
    }

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }
}
