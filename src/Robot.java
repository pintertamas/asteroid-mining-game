import java.util.Random;

public class Robot extends Figure {

    public Robot(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    public void move() {
        TestLogger.functionCalled(this, "move", "void");
        Asteroid a = chooseNextDestination();
        asteroid.removeFigure(this);
        setAsteroid(a);
        TestLogger.functionReturned();
    }

    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this, "onExplosion", "void");
        this.move();
        TestLogger.functionReturned();
    }

    public Asteroid chooseNextDestination() {
        TestLogger.functionCalled(this, "chooseNextDestination", "void");
        Asteroid ast = new Asteroid(new Iron(), 3, false, true);
        TestLogger.functionReturned(ast.toString());
        return ast;
    }

    public void moveThroughPortal() {
        ;
    }

    public void step() {
        TestLogger.functionCalled(this, "step", "void");
        if(asteroid.getLayers() == 0) {
            move();
        }
        else {
            drill();
        }
        TestLogger.functionReturned();
    }
}
