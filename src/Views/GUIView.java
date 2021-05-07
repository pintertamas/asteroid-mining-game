package Views;

import Controllers.Map;
import Entities.Settler;
import Materials.Material;
import Playground.Asteroid;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GUIView extends View {
    Map map;

    public GUIView(Map map) {
        this.map = map;
    }

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

    private void drawInventory(VBox vBox, Rectangle2D screenBounds) {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(100), Insets.EMPTY)));
        grid.setAlignment(Pos.CENTER);
        Text text = ViewFunctions.text("\nInventory\n", 15);
        grid.getChildren().add(text);
        vBox.getChildren().add(grid);

        FlowPane inventory = new FlowPane();
        inventory.setAlignment(Pos.CENTER);
        inventory.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(30.0), Insets.EMPTY)));
        for (Material material : map.getCurrentSettler().getInventory().getMaterials()) {
            double imgSize = screenBounds.getWidth() / 30;
            ImageView imageView = ViewFunctions.image(material.getMaterialView().getImagePath(), imgSize);
            inventory.getChildren().add(imageView);
        }
        vBox.getChildren().add(inventory);
    }

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

    private void drawActions(VBox vBox, Rectangle2D screenBounds) {
        FlowPane actions = new FlowPane();
        actions.setHgap(10);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(10, 10, 10, 10));
        actions.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(70.0), Insets.EMPTY)));

        Button moveButton = new Button("Move");
        moveButton.setFont(ViewFunctions.font(10));
        Button drillButton = new Button("Drill");
        drillButton.setFont(ViewFunctions.font(10));
        Button mineButton = new Button("Mine");
        mineButton.setFont(ViewFunctions.font(10));
        Button putMaterialBack = new Button("Put Material Back");
        putMaterialBack.setFont(ViewFunctions.font(10));
        Button putPortalDown = new Button("Put Portal Down");
        putPortalDown.setFont(ViewFunctions.font(10));
        Button buildPortal = new Button("Build Portal");
        buildPortal.setFont(ViewFunctions.font(10));
        Button buildRobot = new Button("Build Robot");
        buildRobot.setFont(ViewFunctions.font(10));
        Button buildBase = new Button("Build Base");
        buildBase.setFont(ViewFunctions.font(10));

        actions.getChildren().add(moveButton);
        actions.getChildren().add(drillButton);
        actions.getChildren().add(mineButton);
        actions.getChildren().add(putMaterialBack);
        actions.getChildren().add(putPortalDown);
        actions.getChildren().add(buildPortal);
        actions.getChildren().add(buildRobot);
        actions.getChildren().add(buildBase);

        vBox.getChildren().add(actions);
    }

    private void drawPortalsAndActions(VBox vBox, Rectangle2D screenBounds) {
        drawPortals(vBox);
        drawActions(vBox, screenBounds);
    }

    private void drawAsteroidDetails(VBox vBox, Rectangle2D screenBounds, Asteroid asteroid) {
        HBox detailContainer = new HBox(20);
        detailContainer.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(50.0), Insets.EMPTY)));

        double imgSize = screenBounds.getWidth() / 10;
        try {
            String imgPath = asteroid.getAsteroidView().getImage(); //TODO random exceptiont dob ha sokáig fut a játék
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

        vBox.getChildren().add(detailContainer);
    }

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
        drawInventory(mainContainer, screenBounds);
        drawPortalsAndActions(mainContainer, screenBounds);
        drawAsteroidDetails(mainContainer, screenBounds, map.getCurrentSettler().getAsteroid());
        drawAsteroidDetails(mainContainer, screenBounds, map.getCurrentAsteroid());

        this.getView().getChildren().add(mainContainer);
        root.getChildren().add(this.getView());
    }
}
