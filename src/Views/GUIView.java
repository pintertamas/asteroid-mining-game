package Views;

import Controllers.ClickEventHandler;
import Controllers.Controller;
import Controllers.Map;
import Events.AsteroidCustomEvent;
import Events.CustomEvent;
import Materials.Material;
import Playground.Asteroid;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * A GUI view-ja
 *
 */
public class GUIView extends View {
    final Map map;

    /**
     * Konstruktor.
     *
     * @param map
     */
    public GUIView(Map map) {
        this.map = map;
    }

    /**
     * Current settler kirajzolása
     *
     * @param vBox
     */
    private void drawSettlerInfo(VBox vBox) {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(100), Insets.EMPTY)));
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Text text = ViewFunctions.text("Current settler:\n" + map.getCurrentSettler(), 15);
        grid.getChildren().add(text);
        vBox.getChildren().add(grid);
    }

    /**
     * A jelenleg kiválasztott nyersanyag kirajzolása.
     *
     * @param vBox
     * @param screenBounds
     */
    private void drawSelectedMaterial(VBox vBox, Rectangle2D screenBounds) {
        HBox hBox = new HBox();
        hBox.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(100), Insets.EMPTY)));
        hBox.setAlignment(Pos.CENTER);
        Text text = ViewFunctions.text("Selected\nmaterial:", 10);
        hBox.getChildren().add(text);

        double imgSize = screenBounds.getWidth() / 30;
        String imagePath = map.getCurrentSettler().getInventory().getSelectedMaterial() == null
                ? "/asteroids/rock.png"
                : map.getCurrentSettler().getInventory().getSelectedMaterial().getMaterialView().getImagePath();
        ImageView imageView = ViewFunctions.image(imagePath, imgSize);

        hBox.getChildren().add(imageView);

        vBox.getChildren().add(hBox);
    }

    /**
     * Inventory tartalmának kirajzolása.
     *
     * @param vBox
     * @param screenBounds
     */
    private void drawInventory(VBox vBox, Group root, Rectangle2D screenBounds) {
        FlowPane inventory = new FlowPane();
        inventory.setAlignment(Pos.CENTER);
        inventory.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(30.0), Insets.EMPTY)));
        for (Material material : map.getCurrentSettler().getInventory().getMaterials()) {
            double imgSize = screenBounds.getWidth() / 30;
            ImageView imageView = ViewFunctions.image(material.getMaterialView().getImagePath(), imgSize);

            imageView.setOnMouseClicked((MouseEvent event) -> imageView.fireEvent(new AsteroidCustomEvent()));

            imageView.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
                @Override
                public void onItemClicked() {
                    map.getCurrentSettler().getInventory().setSelectedMaterial(material);
                    map.getGuiView().draw(root, screenBounds);
                }
            });

            inventory.getChildren().add(imageView);
        }
        vBox.getChildren().add(inventory);
    }

    /**
     * Teleportkapuk kirajzolása.
     *
     * @param vBox
     */
    private void drawPortals(VBox vBox) {
        HBox portalBox = new HBox(20);
        portalBox.setAlignment(Pos.CENTER);
        portalBox.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(100), Insets.EMPTY)));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(100), Insets.EMPTY)));

        ImageView portalImage = ViewFunctions.image("/portals/portal.png", 100);
        portalBox.getChildren().add(portalImage);

        Text text = ViewFunctions.text("No. Portals: " + map.getCurrentSettler().getInventory().getPortals().size(), 20);
        portalBox.getChildren().add(text);

        grid.getChildren().add(portalBox);
        vBox.getChildren().add(grid);
    }

    /**
     * Végrehajtható akciók kirajzolása.
     *
     * @param vBox
     * @param root
     * @param screenBounds
     */
    private void drawActions(VBox vBox, Group root, Rectangle2D screenBounds) {
        FlowPane actions = new FlowPane();
        actions.setHgap(10);
        actions.setVgap(10);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(10, 10, 10, 10));
        actions.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(70.0), Insets.EMPTY)));

        Button moveToCenter = new Button("Move camera to settler");
        moveToCenter.setFont(ViewFunctions.font(10));

        moveToCenter.setOnMouseClicked((MouseEvent event) -> moveToCenter.fireEvent(new AsteroidCustomEvent()));

        moveToCenter.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                Controller.moveToSettler(root, screenBounds);
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        Button moveButton = new Button("Move");
        moveButton.setFont(ViewFunctions.font(10));

        moveButton.setOnMouseClicked((MouseEvent event) -> moveButton.fireEvent(new AsteroidCustomEvent()));

        moveButton.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().move();
                Controller.getController().drawAllViews(screenBounds);
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        Button moveThroughPortalButton = new Button("Move Through Portal");
        moveThroughPortalButton.setFont(ViewFunctions.font(10));

        moveThroughPortalButton.setOnMouseClicked((MouseEvent event) -> moveThroughPortalButton.fireEvent(new AsteroidCustomEvent()));

        moveThroughPortalButton.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().moveThroughPortal();
                Controller.getController().drawAllViews(screenBounds);
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        Button drillButton = new Button("Drill");
        drillButton.setFont(ViewFunctions.font(10));

        drillButton.setOnMouseClicked((MouseEvent event) -> drillButton.fireEvent(new AsteroidCustomEvent()));

        drillButton.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().drill();
                Controller.getController().redrawAsteroid(map.getCurrentSettler().getAsteroid(), screenBounds);
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        Button mineButton = new Button("Mine");
        mineButton.setFont(ViewFunctions.font(10));

        mineButton.setOnMouseClicked((MouseEvent event) -> mineButton.fireEvent(new AsteroidCustomEvent()));

        mineButton.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().mine();
                Controller.getController().redrawAsteroid(map.getCurrentSettler().getAsteroid(), screenBounds);
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        Button putMaterialBack = new Button("Put Material Back");
        putMaterialBack.setFont(ViewFunctions.font(10));

        putMaterialBack.setOnMouseClicked((MouseEvent event) -> putMaterialBack.fireEvent(new AsteroidCustomEvent()));

        putMaterialBack.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().putMaterialBack(map.getCurrentSettler().getInventory().getSelectedMaterial());
                Controller.getController().redrawAsteroid(map.getCurrentSettler().getAsteroid(), screenBounds);
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        Button putPortalDown = new Button("Put Portal Down");
        putPortalDown.setFont(ViewFunctions.font(10));

        putPortalDown.setOnMouseClicked((MouseEvent event) -> putPortalDown.fireEvent(new AsteroidCustomEvent()));

        putPortalDown.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().putPortalDown();
                Controller.getController().redrawAsteroidContainedViews(map.getCurrentSettler().getAsteroid(), screenBounds);
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        Button buildPortal = new Button("Build Portal");
        buildPortal.setFont(ViewFunctions.font(10));

        buildPortal.setOnMouseClicked((MouseEvent event) -> buildPortal.fireEvent(new AsteroidCustomEvent()));

        buildPortal.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().buildPortal();
                Controller.getController().redrawAsteroidContainedViews(map.getCurrentSettler().getAsteroid(), screenBounds);
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        Button buildRobot = new Button("Build Robot");
        buildRobot.setFont(ViewFunctions.font(10));

        buildRobot.setOnMouseClicked((MouseEvent event) -> buildRobot.fireEvent(new AsteroidCustomEvent()));

        buildRobot.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().buildRobot();
                Controller.getController().redrawAsteroidContainedViews(map.getCurrentSettler().getAsteroid(), screenBounds);
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        Button buildBase = new Button("Build Base");
        buildBase.setFont(ViewFunctions.font(10));

        buildBase.setOnMouseClicked((MouseEvent event) -> buildBase.fireEvent(new AsteroidCustomEvent()));

        buildBase.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().buildBase();
                Controller.getController().redrawGUI(root, screenBounds);
            }
        });

        actions.getChildren().add(moveToCenter);
        actions.getChildren().add(moveButton);
        actions.getChildren().add(moveThroughPortalButton);
        actions.getChildren().add(drillButton);
        actions.getChildren().add(mineButton);
        actions.getChildren().add(putMaterialBack);
        actions.getChildren().add(putPortalDown);
        actions.getChildren().add(buildPortal);
        actions.getChildren().add(buildRobot);
        actions.getChildren().add(buildBase);

        vBox.getChildren().add(actions);
    }

    /**
     * Teleportkapuk és a lehetséges akciók kirajzolása.
     *
     * @param vBox
     * @param root
     * @param screenBounds
     */
    private void drawPortalsAndActions(VBox vBox, Group root, Rectangle2D screenBounds) {
        drawPortals(vBox);
        drawActions(vBox, root, screenBounds);
    }

    /**
     * Információk megjelenítése az aktuális aszteroidáról.
     *
     * @param vBox
     * @param screenBounds
     * @param asteroid
     */
    private void drawAsteroidDetails(VBox vBox, Rectangle2D screenBounds, Asteroid asteroid) {
        HBox detailContainer = new HBox(15);
        detailContainer.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(50.0), Insets.EMPTY)));

        double imgSize = screenBounds.getWidth() / 15;
        try {
            String imgPath = asteroid.getAsteroidView().getImage();
            ImageView imageView = ViewFunctions.image(imgPath, imgSize);
            detailContainer.getChildren().add(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        VBox details = new VBox(5);
        details.setAlignment(Pos.CENTER_LEFT);
        Text asteroidName = ViewFunctions.text("Asteroid ID:\n" + asteroid.toString().replace("Playground.Asteroid@", ""), 10);
        Text asteroidLayers = ViewFunctions.text("Layers: " + asteroid.getLayers(), 10);
        Text asteroidCore = ViewFunctions.text(asteroid.isHollow() || asteroid.getMaterial() == null ? "Hollow" : "Material:\n" + asteroid.getMaterial().toString().replace("Materials.", ""), 10);
        Text asteroidIsNearSun = ViewFunctions.text(asteroid.getMaterial() == null ? "" : (asteroid.isNearSun() ? "!!!Near the SUN!!!" + (!asteroid.isHollow() ? "\t(counter: " + asteroid.getMaterial().getNearSunCount() + ")" : "") : ""), 10);
        asteroidIsNearSun.setStyle("-fx-fill: red");

        details.getChildren().addAll(asteroidName, asteroidLayers, asteroidCore, asteroidIsNearSun);

        detailContainer.getChildren().add(details);

        vBox.getChildren().add(detailContainer);
    }

    /**
     * Rajzolófüggvény.
     *
     * @param root
     * @param screenBounds
     */
    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        double width = 2 * screenBounds.getWidth() / 9;
        double height = screenBounds.getHeight();
        double posX = 7 * screenBounds.getWidth() / 9;
        double posY = 0;

        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setLayoutX(posX);
        mainContainer.setLayoutY(posY);
        mainContainer.setPrefWidth(width);
        mainContainer.setPrefHeight(height);
        mainContainer.setPadding(new Insets(10, 10, 10, 10));
        mainContainer.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));

        drawSettlerInfo(mainContainer);
        drawSelectedMaterial(mainContainer, screenBounds);
        drawInventory(mainContainer, root, screenBounds);
        drawPortalsAndActions(mainContainer, root, screenBounds);
        drawAsteroidDetails(mainContainer, screenBounds, map.getCurrentSettler().getAsteroid());
        drawAsteroidDetails(mainContainer, screenBounds, map.getCurrentAsteroid());

        this.getView().getChildren().add(mainContainer);
        root.getChildren().add(this.getView());
    }
}
