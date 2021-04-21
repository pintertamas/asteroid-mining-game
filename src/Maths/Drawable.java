package Maths;

import javafx.geometry.Rectangle2D;

public class Drawable {
    double x, y;
    double size = 1000.0f;

    public Drawable() {
        this.x = 0;
        this.y = 0;
    }

    public Drawable(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move(Drawable v) {
        this.x += v.x;
        this.y += v.y;
    }

    public boolean isInside(Rectangle2D screenBounds) {
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();
        if (this.getX() + this.getSize() < 0)
            return false;
        else if (this.getX() - this.getSize() > width)
            return false;
        else if (this.getY() + this.getSize() < 0)
            return false;
        else return !(this.getY() - this.getSize() > height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
