public abstract class Material {
    Asteroid asteroid;

    public Material() {
    }

    protected void readyToMine() {
    }

    public void addToInventory(Settler s) {
    }

    protected abstract boolean isCompatibleWith();

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }
}
