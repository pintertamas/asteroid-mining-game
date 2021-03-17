public abstract class Material {
    Asteroid asteroid;

    protected void readyToMine(){}
    public void addToInventory(Settler s){}
    protected abstract boolean isCompatibleWith();
}
