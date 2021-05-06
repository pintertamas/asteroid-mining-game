package Views;

import Controllers.Map;
import Playground.Asteroid;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class MapView extends View {
    Map map;

    public MapView(Map map) {
        this.map = map;
    }

    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();
        ArrayList<Asteroid> alreadyConnected = new ArrayList<>();
        for (Asteroid asteroid : this.map.getAsteroids()) {
            for (Asteroid neighbor : asteroid.getNeighbors()) {
                if (alreadyConnected.contains(neighbor)) {
                    continue;
                }
                Line line = new Line();
                line.setStartX(neighbor.getPosition().getX());
                line.setStartY(neighbor.getPosition().getY());
                line.setEndX(asteroid.getPosition().getX());
                line.setEndY(asteroid.getPosition().getY());
                line.setStroke(Color.WHITE);
                this.getView().getChildren().add(line);
            }
            alreadyConnected.add(asteroid);
        }
        root.getChildren().add(this.getView());
    }
}
