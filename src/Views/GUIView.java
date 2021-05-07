package Views;

import Controllers.ClickEventHandler;
import Controllers.Map;
import Entities.Settler;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GUIView extends View {
    Map map;

    public GUIView(Map map) {
        this.map = map;
    }

    private GridPane drawSettlerInfo() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(0), Insets.EMPTY)));
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Text text = ViewFunctions.text("Current settler:\n" + map.getCurrentSettler(), 15);
        grid.getChildren().add(text);
        return grid;
    }

    private HBox drawSelectedMaterial(Rectangle2D screenBounds) {
        HBox selectedMaterialBox = new HBox();

        selectedMaterialBox.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(0), Insets.EMPTY)));
        selectedMaterialBox.setAlignment(Pos.CENTER_LEFT);
        Text text = ViewFunctions.text("Selected material: ", 15);
        selectedMaterialBox.getChildren().add(text);

        double imgSize = screenBounds.getWidth() / 30;
        String imagePath = map.getCurrentSettler().getInventory().getSelectedMaterial() == null
                ? "/asteroids/rock.png"
                : map.getCurrentSettler().getInventory().getSelectedMaterial().getMaterialView().getImagePath();
        ImageView imageView = ViewFunctions.image(imagePath, imgSize);

        selectedMaterialBox.getChildren().add(imageView);

        selectedMaterialBox.setPrefWidth(screenBounds.getWidth() / 7);
        selectedMaterialBox.setLayoutX(screenBounds.getWidth() - selectedMaterialBox.getPrefWidth());
        selectedMaterialBox.setLayoutY(0);
        selectedMaterialBox.setPadding(new Insets(10, 10, 10, 10));

        return selectedMaterialBox;
    }

    private VBox drawInventory(Group root, Rectangle2D screenBounds) {
        VBox inventoryBox = new VBox(10);
        inventoryBox.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(0), Insets.EMPTY)));
        inventoryBox.setAlignment(Pos.CENTER);

        Text text = ViewFunctions.text("\nInventory\n", 15);
        inventoryBox.getChildren().add(text);

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
        inventoryBox.getChildren().add(inventory);
        inventoryBox.setPrefWidth(screenBounds.getWidth() / 6);

        inventoryBox.setLayoutX(0);
        inventoryBox.setLayoutY(screenBounds.getHeight() * 7 / 12);

        return inventoryBox;
    }

    private HBox drawPortals(Rectangle2D screenBounds) {
        HBox portalBox = new HBox(20);
        portalBox.setAlignment(Pos.CENTER);
        portalBox.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(0), Insets.EMPTY)));

        portalBox.setAlignment(Pos.CENTER);
        portalBox.setPadding(new Insets(10, 10, 10, 10));

        ImageView portalImage = ViewFunctions.image("/portals/portal.png", 100);
        portalBox.getChildren().add(portalImage);

        Text text = ViewFunctions.text("No. Portals: " + map.getCurrentSettler().getInventory().getPortals().size(), 20);
        portalBox.getChildren().add(text);

        portalBox.setPrefHeight(screenBounds.getHeight() / 8);
        portalBox.setLayoutX(0);
        portalBox.setLayoutY(screenBounds.getHeight() * 1 / 6);

        return portalBox;
    }

    private FlowPane drawActions(Group root, Rectangle2D screenBounds) {
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
                map.getCurrentSettler().moveToSettler(root, screenBounds);
            }
        });

        Button moveButton = new Button("Move");
        moveButton.setFont(ViewFunctions.font(10));

        moveButton.setOnMouseClicked((MouseEvent event) -> moveButton.fireEvent(new AsteroidCustomEvent()));

        moveButton.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().move();
            }
        });

        Button moveThroughPortalButton = new Button("Move Through Portal");
        moveThroughPortalButton.setFont(ViewFunctions.font(10));

        moveThroughPortalButton.setOnMouseClicked((MouseEvent event) -> moveThroughPortalButton.fireEvent(new AsteroidCustomEvent()));

        moveThroughPortalButton.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().moveThroughPortal();
            }
        });

        Button drillButton = new Button("Drill");
        drillButton.setFont(ViewFunctions.font(10));

        drillButton.setOnMouseClicked((MouseEvent event) -> drillButton.fireEvent(new AsteroidCustomEvent()));

        drillButton.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().drill();
            }
        });

        Button mineButton = new Button("Mine");
        mineButton.setFont(ViewFunctions.font(10));

        mineButton.setOnMouseClicked((MouseEvent event) -> mineButton.fireEvent(new AsteroidCustomEvent()));

        mineButton.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().mine();
            }
        });

        Button putMaterialBack = new Button("Put Material Back");
        putMaterialBack.setFont(ViewFunctions.font(10));

        putMaterialBack.setOnMouseClicked((MouseEvent event) -> putMaterialBack.fireEvent(new AsteroidCustomEvent()));

        putMaterialBack.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().putMaterialBack(map.getCurrentSettler().getInventory().getSelectedMaterial());
            }
        });

        Button putPortalDown = new Button("Put Portal Down");
        putPortalDown.setFont(ViewFunctions.font(10));

        putPortalDown.setOnMouseClicked((MouseEvent event) -> putPortalDown.fireEvent(new AsteroidCustomEvent()));

        putPortalDown.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().putPortalDown();
            }
        });

        Button buildPortal = new Button("Build Portal");
        buildPortal.setFont(ViewFunctions.font(10));

        buildPortal.setOnMouseClicked((MouseEvent event) -> buildPortal.fireEvent(new AsteroidCustomEvent()));

        buildPortal.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().buildPortal();
            }
        });

        Button buildRobot = new Button("Build Robot");
        buildRobot.setFont(ViewFunctions.font(10));

        buildRobot.setOnMouseClicked((MouseEvent event) -> buildRobot.fireEvent(new AsteroidCustomEvent()));

        buildRobot.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().buildRobot();
            }
        });

        Button buildBase = new Button("Build Base");
        buildBase.setFont(ViewFunctions.font(10));

        buildBase.setOnMouseClicked((MouseEvent event) -> buildBase.fireEvent(new AsteroidCustomEvent()));

        buildBase.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new ClickEventHandler() {
            @Override
            public void onItemClicked() {
                map.getCurrentSettler().buildBase();
            }
        });

        actions.getChildren().addAll(moveToCenter, moveButton, moveThroughPortalButton, drillButton, mineButton, putMaterialBack, putPortalDown, buildPortal, buildRobot, buildBase);

        return actions;
    }

    private void drawPortalsAndActions(Group root, Rectangle2D screenBounds) {
        drawPortals(screenBounds);
        drawActions(root, screenBounds);
    }

    private HBox drawAsteroidDetails(Rectangle2D screenBounds, Asteroid asteroid) {
        HBox detailContainer = new HBox(20);
        detailContainer.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(0), Insets.EMPTY)));

        double imgSize = screenBounds.getWidth() / 10;
        try {
            String imgPath = asteroid.getAsteroidView().getImage();
            ImageView imageView = ViewFunctions.image(imgPath, imgSize);
            detailContainer.getChildren().add(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        VBox details = new VBox(10);
        details.setAlignment(Pos.CENTER_LEFT);
        Text asteroidName = ViewFunctions.text("Asteroid ID:\n" + asteroid.toString().replace("Playground.Asteroid@", ""), 15);
        Text asteroidLayers = ViewFunctions.text("Layers: " + asteroid.getLayers(), 15);
        Text asteroidCore = ViewFunctions.text("Material:\n" + asteroid.getMaterial().toString().replace("Materials.", ""), 15);

        details.getChildren().addAll(asteroidName, asteroidLayers, asteroidCore);

        detailContainer.getChildren().add(details);
        detailContainer.setPrefWidth(screenBounds.getWidth() / 6);

        return detailContainer;
    }

    private HBox drawAsteroidBlock(Rectangle2D screenBounds) {
        HBox asteroids = new HBox();
        asteroids.setPadding(new Insets(10, 10, 10, 10));
        asteroids.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(0), Insets.EMPTY)));
        asteroids.getChildren().add(drawAsteroidDetails(screenBounds, map.getCurrentSettler().getAsteroid()));
        asteroids.getChildren().add(drawAsteroidDetails(screenBounds, map.getCurrentAsteroid()));
        asteroids.setLayoutX(0);
        asteroids.setPrefHeight(screenBounds.getHeight() / 4);
        asteroids.setLayoutY(screenBounds.getHeight() * 3 / 4);
        return asteroids;
    }

    @Override
    public void draw(Group root, Rectangle2D screenBounds) {
        root.getChildren().remove(this.getView());
        this.getView().getChildren().clear();

        Group mainContainer = new Group();
        mainContainer.setLayoutX(0);
        mainContainer.setLayoutY(0);

        mainContainer.getChildren().add(drawSettlerInfo());
        mainContainer.getChildren().add(drawSelectedMaterial(screenBounds));

        mainContainer.getChildren().add(drawInventory(root, screenBounds));
        mainContainer.getChildren().add(drawPortals(screenBounds));

        drawPortalsAndActions(mainContainer, screenBounds);

        mainContainer.getChildren().add(drawAsteroidBlock(screenBounds));

        this.getView().getChildren().add(mainContainer);
        root.getChildren().add(this.getView());
    }
}
