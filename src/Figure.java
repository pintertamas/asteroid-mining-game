public abstract class Figure {
    private Asteroid asteroid;
    protected boolean roundFinished;

    public abstract void move();
    public boolean drill() { return true; }
    public abstract void onExplosion();
    public void die() {}
    public abstract void step();

}
