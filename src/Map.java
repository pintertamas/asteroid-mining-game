import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Map {
    ArrayList<Asteroid> asteroids;

    public Map(){
        this.asteroids=new ArrayList<>();
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

        TestLogger.functionReturned();
    }

    public boolean hasAllMaterials(){
        TestLogger.functionCalled(this, "hasAllMaterials", "boolean");

        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public void initGame(int numberOfPlayers){
        TestLogger.functionCalled(this, "initGame","int numberOfPlayers", "void");

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
