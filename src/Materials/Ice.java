package Materials;

/**
 * Vízjég osztály.
 * A Material osztály leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Ice extends Material {
    /**
     * Az utolsó réteg lefúrása után, ha napközelben van az aszteroida, a jég elolvad, eltűnik.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void readyToMine() {
        System.out.println("Ice sublimated");
        getAsteroid().setIsHollow(true);
    }
}
