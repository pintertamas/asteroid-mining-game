public class Ufo extends Figure implements IMine{

    public Ufo(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    @Override
    public void move() {

    }

    @Override
    public void moveThroughPortal() {

    }

    @Override
    public void step() {

    }

    @Override
    public void onExplosion() {

    }

    @Override
    public boolean mine() {
        return false;
    }
}
