package Entities;

import Interfaces.IMine;
import Playground.Asteroid;
import Playground.Portal;

import java.util.ArrayList;
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
        if(asteroid.getPortals().size() != 0) {
            Random rand = new Random();
            ArrayList<Portal> tmpArray = new ArrayList<>();
            for (Portal p : asteroid.getPortals()) {
                if (p.getPair().getAsteroid() != null) {
                    tmpArray.add(p);
                }
            }
            int nextAsteroid = rand.nextInt(tmpArray.size());
            asteroid.removeFigure(this);
            asteroid.getNeighbors().get(nextAsteroid).addFigure(this);
            setAsteroid(tmpArray.get(nextAsteroid).getAsteroid());
            setRoundFinished(true);
        }
        else {
            return false;
        }
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
