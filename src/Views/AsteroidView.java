package Views;

import Controllers.ClickEventHandler;
import Events.AsteroidCustomEvent;
import Events.CustomEvent;
import Playground.Asteroid;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class AsteroidView extends View {
    private final Asteroid asteroid;
    private ArrayList<View> containedViews;

    public AsteroidView(Asteroid asteroid) {
        this.asteroid = asteroid;
        this.pos = asteroid.getPosition();
        containedViews = new ArrayList<>();
    }

    public String getImage() {
        if (asteroid.getLayers() > 0)
            return "asteroids/rock.png";
        else if (asteroid.isHollow())
            return "/asteroids/hollow.png";
        else return asteroid.getMaterial().getMaterialView().getImagePath();
    }

    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        if (asteroid.getPosition().isInside(screenBounds)) {
            String img = getImage();
            ImageView imageView = ViewFunctions.image(img, asteroid.getPosition().getSize());
            imageView.setX(asteroid.getPosition().getX() - asteroid.getPosition().getSize() / 2);
            imageView.setY(asteroid.getPosition().getY() - asteroid.getPosition().getSize() / 2);

            this.getView().getChildren().clear();
            this.getView().getChildren().add(imageView);

            imageView.setOnMouseClicked((MouseEvent event) -> imageView.fireEvent(new AsteroidCustomEvent()));

            imageView.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
                @Override
                public void onItemClicked() {
                    asteroid.getMap().setCurrentAsteroid(asteroid);
                    asteroid.getMap().getGuiView().draw(root, screenBounds);
                }
            });
            root.getChildren().add(this.getView());
        }
        drawContainedViews(root, screenBounds);
    }

    public void drawContainedViews(Group root, Rectangle2D screenBounds) {
        for (View view : containedViews)
            view.draw(root, screenBounds);
    }

    public void addContainedView(View view) {
        this.containedViews.add(view);
    }

    public void removeContainedView(View view) {
        this.containedViews.remove(view);
    }

    public ArrayList<View> getContainedViews() {
        return containedViews;
    }
}
