package Maths;

import javafx.geometry.Rectangle2D;

/**
 * Ez az osztály felel a a mozgásért
 */
public class Drawable {
    private double x, y;
    private double size = 200;

    public Drawable() {
        this.x = 0;
        this.y = 0;
    }

    public Drawable(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Ez a függvény valósítja meg a mozgást
     * @param v
     */
    public void move(Drawable v) {
        this.x += v.x;
        this.y += v.y;
    }

    /**
     * Ez a függvény megállapítja, hogy mely aszteroidákat kell kirajzolni
     * @param screenBounds
     */
    public boolean isInside(Rectangle2D screenBounds) {
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();
        if (this.getX() + this.getSize() < 0)
            return false;
        else if (this.getX() > width)
            return false;
        else if (this.getY() + this.getSize() < 0)
            return false;
        else return !(this.getY() - this.getSize() > height);
    }

    public double distance(Drawable other) {
        double xDist = this.x - other.x;
        double yDist = this.y - other.y;
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    /**
     * Getter az x koordinátához
     */
    public double getX() {
        return x;
    }

    /**
     * Getter az y koordinátához
     */
    public double getY() {
        return y;
    }

    /**
     * Getter a mérethez
     */
    public double getSize() {
        return size;
    }

    /**
     * Setter a a méret változtatásához
     * @param size
     */
    public void setSize(double size) {
        this.size = size;
    }
}
