public abstract class Figure {
    protected Asteroid asteroid;
    protected boolean roundFinished;

    public Figure(Asteroid asteroid, boolean roundFinished) {
        this.asteroid = asteroid;
        this.roundFinished = roundFinished;
    }

    public abstract void move();

    public boolean drill() {
        TestLogger.functionCalled(this, "drill", "boolean");
        if (asteroid.drilled()) {
            setRoundFinished(true);
            TestLogger.functionReturned();
            return false;
        }

        TestLogger.functionReturned();
        return true;
    }

    public abstract void onExplosion();

    public void die() {
        //TODO ezt hogy
    }

    protected Inventory getInventory() {
        return new Inventory();
    }

    public abstract void step();

    public void setAsteroid(Asteroid asteroid) {
        TestLogger.functionCalled(this, "setAsteroid", asteroid.getClass().getName() + " " + asteroid, "void");
        TestLogger.functionReturned();
        this.asteroid = asteroid;
    }

    protected void setRoundFinished(boolean roundFinished) {
        TestLogger.functionCalled(this, "setRoundFinished", "void");
        TestLogger.functionReturned();

        this.roundFinished = roundFinished;
    }
}
