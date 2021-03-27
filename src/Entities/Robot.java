package Entities;
import Materials.*;
import Playground.Asteroid;
import Test.TestLogger;

import java.util.ArrayList;
import java.util.Random;

public class Robot extends Figure {

    public Robot(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    public void move() {
        TestLogger.functionCalled(this, "move", "void");
        Asteroid a = chooseNextDestination();
        //if the current asteroid has no neighbors the robot can't move
        if(a == null){
            System.out.println("Robot move NOT done, no neighbors");
            TestLogger.functionReturned();
            this.setRoundFinished(true);
            return;
        }
        this.asteroid.removeFigure(this);
        setAsteroid(a);
        a.addFigure(this);
        this.setRoundFinished(true);
        System.out.println("Robot move done");
        TestLogger.functionReturned();
    }

    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this, "onExplosion", "void");
        this.move();
        TestLogger.functionReturned();
    }

    public Asteroid chooseNextDestination() {
        TestLogger.functionCalled(this, "chooseNextDestination", "Asteroid");
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        if (neighbors.size() == 0) {
            return null;
        }
        int nextDestination = 0;
        Random rand = new Random();
        nextDestination = rand.nextInt(neighbors.size());
        Asteroid nextAsteroid = neighbors.get(nextDestination);
        TestLogger.functionReturned(nextAsteroid.toString());
        return nextAsteroid;
    }

    public boolean moveThroughPortal() {
        return false;
    }

    public void step() {
        TestLogger.functionCalled(this, "step", "void");
        if(this.asteroid.getLayers() == 0) {
            move();
        }
        else {
            drill();
        }
        TestLogger.functionReturned();
    }
}
