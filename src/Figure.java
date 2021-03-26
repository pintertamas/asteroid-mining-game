public abstract class Figure implements ISteppable, IPortalMove, IMove{
    protected Asteroid asteroid;
    protected boolean roundFinished;

    public Figure(Asteroid asteroid, boolean roundFinished) {
        this.asteroid = asteroid;
        asteroid.addFigure(this);
        this.roundFinished = roundFinished;
    }

    public Asteroid getAsteroid() {
        return this.asteroid;
    }

    public abstract void move();

    public abstract void moveThroughPortal();

    public abstract void step();

    public boolean getRoundFinished(){return this.roundFinished;}

    public boolean drill() {
        TestLogger.functionCalled(this, "drill", "boolean");
        if (asteroid.drilled()) {
            setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            System.out.println("Drill done");
            return true;
        }
        System.out.println("Drill NOT done");
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public abstract void onExplosion();

    //TODO Ez így jó?
    public void die() {
        TestLogger.functionCalled(this, "die", "void");
        //asteroid.removeFigure(this);
        //this. = null;
        TestLogger.functionReturned();
    }

    protected Inventory getInventory() {
        return new Inventory();
    }

    public void setAsteroid(Asteroid asteroid) {
        TestLogger.functionCalled(this, "setAsteroid", "Asteroid asteroid", "void");
        this.asteroid = asteroid;
        TestLogger.functionReturned();
    }

    protected void setRoundFinished(boolean roundFinished) {
        TestLogger.functionCalled(this, "setRoundFinished", "boolean roundFinished", "void");
        this.roundFinished = roundFinished;
        TestLogger.functionReturned(String.valueOf(roundFinished));
    }
}
