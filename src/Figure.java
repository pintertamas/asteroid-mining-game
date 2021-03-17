public abstract class Figure {
    protected Asteroid asteroid;
    protected boolean roundFinished;

    public Figure(Asteroid asteroid, boolean roundFinished) {
        this.asteroid = asteroid;
        this.roundFinished = roundFinished;
    }

    public abstract void move();

    public boolean drill() {
        return true;
    }

    public abstract void onExplosion();

    public void die() {
    }

    public abstract void step();

    public void setAsteroid(Asteroid asteroid) {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "setAsteroid", asteroid.getClass().getName() + " " + asteroid, "void");
        TestLogger.functionReturned();
        this.asteroid = asteroid;
    }
}
