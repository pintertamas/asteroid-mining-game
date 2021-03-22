public abstract class Material {
    Asteroid asteroid;

    public Material() {
    }

    protected void readyToMine() {
    }

    public void addToInventory(Settler s) {
        TestLogger.functionCalled(this, "addToInventory", "Settler s", "void");
        s.getInventory().addMaterial(this);
        TestLogger.functionReturned();
    }

    protected abstract boolean isCompatibleWith();

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }
}
