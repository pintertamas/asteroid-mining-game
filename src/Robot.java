public class Robot extends Figure {

    public Robot(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    @Override
    public void move() {
        TestLogger.functionCalled(this, "move", "void");
        //TODO ezt megcsinálni
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
        asteroid.getNeighbors().size();

        TestLogger.functionReturned(asteroid.toString());
        return asteroid;
    }

    @Override
    public void step() {
        TestLogger.functionCalled(this, "step", "void");
        TestLogger.functionReturned();
    }
}
