import java.util.ArrayList;

public class Asteroid {
    ArrayList<Asteroid> neighbors;
    ArrayList<Figure> figures;
    Material material;
    Portal portal;

    int layers;
    boolean isNearSun;
    boolean isHollow;

    public void explode(){}
    public void addFigure(Figure f){}
    public void removeFigure(Figure f){}
    public void setMaterial(Material m){}
    public boolean drilled(){return true;}
    public boolean mined(){return true;}
    public boolean coreChanged(){return true;}
    public void decreaseLayers(){}
    public void addPortal(Portal p){}
    public void addNeighbor(Asteroid a){}
    public void invokeFigures(){}
    public void resetStep(){}
}
