package Playground;

import Interfaces.IMove;
import Test.TestLogger;

public class Portal implements IMove {
    private Portal pair;
    private Asteroid asteroid;

    public Portal getPair() {
        return pair;
    }

    public void setPair(Portal pair) {
        TestLogger.functionCalled(this, "setPair", "Playground.Portal pair", "void");
        this.pair = pair;
        TestLogger.functionReturned();
    }

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
        if (pair.asteroid != null) {
            this.asteroid.addNeighbor(pair.asteroid);
            pair.asteroid.addNeighbor(this.asteroid);
        }
    }

    public Asteroid getAsteroid() {
        return asteroid;
    }

    @Override
    public void move() {
        
    }
}
