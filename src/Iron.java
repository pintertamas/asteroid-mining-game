public class Iron extends Material {

    public Iron() {
        super();
    }

    @Override
    protected boolean isCompatibleWith(Material m) {
        return false;
    }
}
