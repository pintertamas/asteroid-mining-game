package Entities;

import Interfaces.IMine;
import Playground.Asteroid;

import java.util.Random;

public class Ufo extends Figure implements IMine {

    public Ufo(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    @Override
    public void move() {
        this.asteroid.removeFigure(this);
        this.asteroid = getNextDestination();
        this.asteroid.addFigure(this);
    }

    @Override
    public boolean moveThroughPortal() {
       return false;
    }

    @Override
    public void step() {
        if(asteroid.getLayers()==0) {
            mine();
        }
        else {
            move();
        }
    }

    @Override
    public void onExplosion() {

    }

    @Override
    public boolean mine() {
        return false;
    }

    public Asteroid getNextDestination() {
        Random rand = new Random();
        int index = rand.nextInt(asteroid.getNeighbors().size());
        return asteroid.getNeighbors().get(index);
    }
}
