public class Portal {
    Portal pair;
    Asteroid asteroid;

    public Portal getPair() {
        return pair;
    }

    public void setPair(Portal pair) {
        this.pair = pair;
    }

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    public Asteroid getAsteroid() {
        return asteroid;
    }
}
