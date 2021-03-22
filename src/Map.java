import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

public class Map {

    ArrayList<Asteroid> asteroids;
    GameState gameState;

    public Map(){
        this.asteroids=new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
    }

    public void setGameState(GameState gs) {
        TestLogger.functionCalled(this, "setGameState","GameState gs", "void");
        this.gameState = gs;
        TestLogger.functionReturned();
    }

    public void addAsteroid(Asteroid a){
        TestLogger.functionCalled(this, "AddAsteroid","Asteroid a", "void");
        try{
            this.asteroids.add(a);
        }catch (NullPointerException e){}

        TestLogger.functionReturned();
    }

    public void removeAsteroid(Asteroid a){
        TestLogger.functionCalled(this, "removeAsteroid","Asteroid a", "void");
        this.asteroids.remove(a);
        TestLogger.functionReturned();
    }

    //TODO Ezt hogyan is kéne?
    public boolean hasAllMaterials(){
        TestLogger.functionCalled(this, "hasAllMaterials", "boolean");
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }


    public void initGame(int numberOfPlayers){
        TestLogger.functionCalled(this, "initGame","int numberOfPlayers", "void");
        int minimumNumberOfAsteroids = 5;
        int maximumNumberOfAsteroids = 20;
        double numberOfAsteroids = Math.random() * (maximumNumberOfAsteroids - minimumNumberOfAsteroids + 1) + minimumNumberOfAsteroids;
        for(int i = 0; i < numberOfAsteroids; i++) {
            Random rand = new Random();
            int typeOfAsteroid = rand.nextInt(3);
            int numberOfLayers = rand.nextInt(5);
            Material m;
            switch (typeOfAsteroid) {
                case 0:
                    m = new Iron();
                case 1:
                    m = new Coal();
                case 2:
                    m = new Ice();
                case 3:
                    m = new Uranium();
                default:
                    m = new Iron();
            }
            int boolOfSun = rand.nextInt(1);
            boolean sun;
            switch (boolOfSun) {
                case 0:
                    sun = false;
                case 1:
                    sun = true;
                default:
                    sun = true;
            }
            Asteroid a = new Asteroid(m, numberOfLayers, sun, false);
            this.asteroids.add(a);
        }

        for(int i=0; i<numberOfAsteroids; i++) {
            if (i != numberOfAsteroids - 1) {
                asteroids.get(i).addNeighbor(asteroids.get(i + 1));
                asteroids.get(i + 1).addNeighbor(asteroids.get(i));
            }
            else {
                asteroids.get(i).addNeighbor(asteroids.get(0));
                asteroids.get(0).addNeighbor(asteroids.get(i));
            }
        }

        //Kisorsol 20 random asteroida párt, felveszi egymásnak őket.
        for(int i=0; i < 20; i++) {
            Random rand = new Random();
            int a1 = rand.nextInt((int) numberOfAsteroids);
            int a2 = rand.nextInt((int) numberOfAsteroids);
            if (a1 != a2 || asteroids.get(a1).getNeighbors().contains(asteroids.get(a2))) {
                asteroids.get(a1).addNeighbor(asteroids.get(a2));
                asteroids.get(a2).addNeighbor(asteroids.get(a1));
            }
        }

        for(int i = 0; i < numberOfPlayers; i++) {

            Settler s = new Settler( asteroids.get(0),false);
        }
        TestLogger.functionReturned();
    }

    public void solarStorm(){
        TestLogger.functionCalled(this, "solarStorm", "void");
        ArrayList<Figure> toRemove = new ArrayList<>();
        for (Asteroid a: asteroids)
        {
            if (!a.isHollow){
                try{
                    for (Figure f:a.figures) {
                        a.removeFigure(f);
                        f.die();
                    }
                }catch (ConcurrentModificationException e){
                    //TODO Ez így jó lesz?
                }

            }
        }
        TestLogger.functionReturned();
    }

    public boolean checkGameEnd(){
        TestLogger.functionCalled(this, "checkGameEnd", "boolean");

        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public boolean checkIfWinnable(){
        TestLogger.functionCalled(this, "checkIfWinnable", "boolean");

        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public void SetupRound(){
        TestLogger.functionCalled(this, "SetupRound", "void");

        TestLogger.functionReturned();
    }

    public void gameEnd(boolean b){
        TestLogger.functionCalled(this, "gameEnd","boolean b", "void");

        TestLogger.functionReturned();
    }

    public boolean stormComing(){
        TestLogger.functionCalled(this, "stormComing", "boolean");

        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public void reset(){
        TestLogger.functionCalled(this, "reset", "void");

        TestLogger.functionReturned();
    }
}
