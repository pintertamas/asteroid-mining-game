import java.util.Random;

public class Robot extends Figure {

    public Robot(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    @Override
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
        Random rand = new Random();
        int a1 = rand.nextInt((asteroid.getNeighbors().size()));
        Asteroid ast = asteroid.getNeighbors().get(a1);
        TestLogger.functionReturned(asteroid.toString());
        return ast;
    }

    @Override
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
