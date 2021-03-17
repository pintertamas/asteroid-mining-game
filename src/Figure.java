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

    public void setAsteroid(Asteroid asteroid, int index) {
        index++;
        for (int i = 0; i < index; i++)
            System.out.print("\t");

        System.out.print(index + " ");
        System.out.println(this.getClass().getName() + " " + this + " setAsteroid(" + asteroid.getClass().getName() + " " + asteroid + "): void");

        for (int i = 0; i < index; i++)
            System.out.print("\t");
        System.out.print(index + " ");

        System.out.println(this.getClass().getName() + " " + this + " setAsteroid(" + asteroid.getClass().getName() + " " + asteroid + ") returned");
        this.asteroid = asteroid;
    }
}
