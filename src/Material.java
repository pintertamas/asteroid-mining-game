public abstract class Material {
    Asteroid asteroid;

    public Material() {
    }

    protected void readyToMine() {
    }

    public void addToInventory(Settler s) {
        TestLogger.functionCalled(this, "addToInventory", "Settler s", "void");
        try{
            s.getInventory().addMaterial(this);
        }catch (NullPointerException e){}

        TestLogger.functionReturned();

    }

    protected abstract boolean isCompatibleWith();

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    public abstract void addToList(Material coal, Material iron, Material uranium, Material Ice, Settler s);
}
