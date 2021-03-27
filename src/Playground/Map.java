package Playground;

import Bills.*;
import Entities.Figure;
import Entities.Settler;
import Materials.*;
import Test.TestLogger;

import java.util.*;

public class Map {

    final ArrayList<Asteroid> asteroids;
    GameState gameState;

    public Map() {
        this.asteroids = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
    }

    public void setGameState(GameState gs) {
        TestLogger.functionCalled(this, "setGameState", "Playground.GameState gs", "void");
        this.gameState = gs;
        TestLogger.functionReturned();
    }

    public void addAsteroid(Asteroid a) {
        TestLogger.functionCalled(this, "AddAsteroid", "Playground.Asteroid a", "void");
        this.asteroids.add(a);
        TestLogger.functionReturned();
    }

    public void removeAsteroid(Asteroid a) {
        TestLogger.functionCalled(this, "removeAsteroid", "Playground.Asteroid a", "void");
        this.asteroids.remove(a);
        TestLogger.functionReturned();
    }

    public ArrayList<Asteroid> getAsteroids() {
        return this.asteroids;
    }

    public void initGame(int numberOfPlayers) {
        TestLogger.functionCalled(this, "initGame", "int numberOfPlayers", "void");
        int minimumNumberOfAsteroids = 5;
        int maximumNumberOfAsteroids = 20;
        double numberOfAsteroids = Math.random() * (maximumNumberOfAsteroids - minimumNumberOfAsteroids + 1) + minimumNumberOfAsteroids;
        for (int i = 0; i < numberOfAsteroids; i++) {
            Random rand = new Random();
            int typeOfAsteroid = rand.nextInt(4);
            int numberOfLayers = rand.nextInt(5);
            Material m;
            switch (typeOfAsteroid) {
                case 0:
                    m = new Iron();
                    break;
                case 1:
                    m = new Coal();
                    break;
                case 2:
                    m = new Ice();
                    break;
                default:
                    m = new Uranium();
            }
            int boolOfSun = rand.nextInt(2);
            boolean sun;
            sun = boolOfSun != 0;
            Asteroid a = new Asteroid(m, numberOfLayers, sun, false);
            this.asteroids.add(a);
        }

        for (int i = 0; i < numberOfAsteroids; i++) {
            if (i < numberOfAsteroids - 1) {
                asteroids.get(i).addNeighbor(asteroids.get(i + 1));
                asteroids.get(i + 1).addNeighbor(asteroids.get(i));
            } else {
                asteroids.get(i).addNeighbor(asteroids.get(0));
                asteroids.get(0).addNeighbor(asteroids.get(i));
            }
        }

        //Kisorsol 20 random asteroida párt, felveszi egymásnak őket.
        for (int i = 0; i < 20; i++) {
            Random rand = new Random();
            int a1 = rand.nextInt((int) numberOfAsteroids);
            int a2 = rand.nextInt((int) numberOfAsteroids);
            if (a1 != a2 || asteroids.get(a1).getNeighbors().contains(asteroids.get(a2))) {
                asteroids.get(a1).addNeighbor(asteroids.get(a2));
                asteroids.get(a2).addNeighbor(asteroids.get(a1));
            }
        }

        for (int i = 0; i < numberOfPlayers; i++) {

            Settler s = new Settler(asteroids.get(0), false);
        }

        //Bence féle ronda hard code a Portálokhoz.
        Portal p1 = new Portal();
        Portal p2 = new Portal();
        p1.setPair(p2);
        p2.setPair(p1);
        p1.setAsteroid(asteroids.get(0));
        p2.setAsteroid(asteroids.get(1));
        asteroids.get(0).addPortal(p1);
        asteroids.get(1).addPortal(p2);


        Portal p3 = new Portal();
        Portal p4 = new Portal();
        p3.setPair(p4);
        p4.setPair(p3);
        p3.setAsteroid(asteroids.get(0));
        p4.setAsteroid(asteroids.get(3));
        asteroids.get(0).addPortal(p3);
        asteroids.get(3).addPortal(p4);



        TestLogger.functionReturned();
    }

    public void solarStorm() {
        TestLogger.functionCalled(this, "solarStorm", "void");
        for (Asteroid a : asteroids) {
            a.handleFigures();
        }
        TestLogger.functionReturned();
    }

    public boolean checkGameEnd() {
        TestLogger.functionCalled(this, "checkGameEnd", "boolean");
        if (!checkIfWinnable()) {
            gameState = GameState.LOST;
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public boolean hasAllMaterials() {
        TestLogger.functionCalled(this, "hasAllMaterials", "boolean");
        ArrayList<Material> allMaterials = new ArrayList<>();
        for (Asteroid asteroid : asteroids) {
            for (Figure f : asteroid.getFigures())
                allMaterials.addAll(f.getInventory().getMaterials());
            if (!asteroid.isHollow)
                allMaterials.add(asteroid.getMaterial());
        }
        boolean hasAll = new BillOfBase().hasEnoughMaterials(allMaterials);
        TestLogger.functionReturned(String.valueOf(hasAll));
        return hasAll;
    }

    private boolean hasAnyFigure() {
        for (Asteroid a : asteroids) {
            if (a.getFigures().size() > 0)
                return true;
        }
        return false;
    }

    public boolean checkIfWinnable() {
        TestLogger.functionCalled(this, "checkIfWinnable", "boolean");

        boolean hasAll = hasAllMaterials() && hasAnyFigure();
        TestLogger.functionReturned(String.valueOf(hasAll));
        return hasAll;
    }

    public void setupRound() {
        TestLogger.functionCalled(this, "setupRound", "void");
        if (stormComing()) {
            for (Asteroid a : asteroids) {
                a.handleFigures();
            }
        } else {
            for (Asteroid a : asteroids) {
                a.invokeFigures();
            }
        }
        TestLogger.functionReturned();
    }

    public void gameEnd(boolean b) {
        TestLogger.functionCalled(this, "gameEnd", "boolean b", "void");

        TestLogger.functionReturned();
    }

    public boolean stormComing() {
        TestLogger.functionCalled(this, "stormComing", "boolean");
        Random rand = new Random();
        int stormNumber = rand.nextInt(200);
        if (stormNumber == 100) {
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public void reset() {
        TestLogger.functionCalled(this, "reset", "void");
        for (Asteroid a : asteroids) {
            a.resetStep();
        }
        TestLogger.functionReturned();
    }
}

