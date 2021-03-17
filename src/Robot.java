public class Robot extends Figure {

    public Robot(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    @Override
    public void move() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "move", "", "void");
        TestLogger.functionReturned();
    }

    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "onExplosion", "", "void");
        TestLogger.functionReturned();
    }

    public Asteroid chooseNextDestination() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "chooseNextDestination", "", "void");
        TestLogger.functionReturned(asteroid.toString());
        return asteroid;
    }

    @Override
    public void step() {
        TestLogger.functionCalled(this.getClass().getName(), this.toString(), "step", "", "void");
        TestLogger.functionReturned();
    }
}
