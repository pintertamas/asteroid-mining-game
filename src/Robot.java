public class Robot extends Figure {

    @Override
    public void move() {}

    @Override
    public void onExplosion() {}

    public Asteroid chooseNextDestination() { return asteroid; }

    @Override
    public void step() {}
}
