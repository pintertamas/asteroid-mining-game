package Views;

import Controllers.ClickEventHandler;
import Controllers.Map;
import Events.AsteroidClickEvent;
import Events.ClickEvent;
import Playground.Asteroid;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class AsteroidView extends View {
    private final Asteroid asteroid;
    private ArrayList<View> containedViews;
    private final Map map;

    public AsteroidView(Asteroid asteroid, Map map) {
        this.asteroid = asteroid;
        this.map = map;
        this.pos = asteroid.getPosition();
    }

    public String getImage() {
        if (asteroid.getLayers() > 0)
            return "asteroids/rock.png";
        else if (asteroid.isHollow())
            return "/asteroids/hollow.png";
        else return asteroid.getMaterial().getMaterialView().getImagePath();
    }

    public void draw(Group root, Rectangle2D screenBounds) {
        if (asteroid.getPosition().isInside(screenBounds)) {
            String img = getImage();
            ImageView imageView = ViewFunctions.image(img, asteroid.getPosition().getSize());
            imageView.setX(asteroid.getPosition().getX());
            imageView.setY(asteroid.getPosition().getY());

            this.getView().getChildren().clear();
            this.getView().getChildren().add(imageView);

            imageView.setOnMouseClicked((MouseEvent event) -> {
                imageView.fireEvent(new AsteroidClickEvent(asteroid));
            });

            imageView.addEventHandler(ClickEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
                @Override
                public void onAsteroidClicked(Asteroid asteroid) {
                    System.out.println(asteroid.getLayers());
                }
            });

            //imageView.setOnMouseClicked(map.getClickEventHandler());
            root.getChildren().remove(this.getView()); //TODO ez itt még lehet hogy baj lesz mert az első lefutáskor még nem lesz rajta a rooton a view
            root.getChildren().add(imageView);
        }
    }


}
