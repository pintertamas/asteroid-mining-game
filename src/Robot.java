public class Robot extends Figure {

    public Robot(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    @Override
    public void move() {}

    @Override
    public void onExplosion() {}

    public Asteroid chooseNextDestination() { return asteroid; }

    @Override
    public void step() {}
}
