public abstract class Figure {
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

    public boolean getRoundFinished(){return this.roundFinished;}

    public abstract void move();

    public boolean drill() {
        TestLogger.functionCalled(this, "drill", "boolean");
        if (asteroid.drilled()) {
            setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            System.out.println("Drill done");
            return true;
        }

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

    public abstract void step();

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
