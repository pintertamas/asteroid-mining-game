package Entities;

import Bills.*;
import Controllers.DrawFunctions;
import Interfaces.IDrill;
import Interfaces.IMine;
import Materials.*;
import Playground.*;
import Test.TestLogger;
import Test.UserIO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Telepes osztály, képes mozogni, fúrni és bányászni.
 * A Figure leszármazottja.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Settler extends Figure implements IMine, IDrill {
    private final Inventory inventory;

    /**
     * Konstruktor.
     *
     * @param asteroid
     * @param roundFinished
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Settler(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
        this.inventory = new Inventory();
        int settlerNumber = new Random().nextInt(5) + 1;
        this.imagePath = "/figures/spaceships/spaceship" + settlerNumber + ".png";
        //-----
        inventory.addMaterial(new Iron());
        inventory.addMaterial(new Coal());
        inventory.addMaterial(new Uranium());
        inventory.addMaterial(new Ice());
        //-----
    }

    /**
     * Mozgás.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void move() {
        TestLogger.functionCalled(this, "move", "void");
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        if (neighbors.size() == 0) {
            System.out.println("The current asteroid has no neighbors so it is not possible to move :(");
            UserIO.addToTemporaryOutput("unsuccessful");
            return;
        }
        System.out.println("Neighbors of the current asteroid: ");
        for (int i = 0; i < neighbors.size(); i++) {
            System.out.println(i + " - " + neighbors.get(i));
        }

        int neighborChoice;

        if (UserIO.readFromFile() || UserIO.currentLine().size() > 1)
            neighborChoice = Integer.parseInt(UserIO.currentLine().get(1));
        else {
            neighborChoice = UserIO.readInt();
        }

        if (neighborChoice < 0 || neighborChoice > neighbors.size() - 1) {
            System.out.println("Wrong neighbor number, could not move settler.");
            UserIO.addToTemporaryOutput("unsuccessful");
        } else {
            this.asteroid.removeFigure(this);
            neighbors.get(neighborChoice).addFigure(this);
            this.setAsteroid(neighbors.get(neighborChoice));
            this.setRoundFinished(true);
            TestLogger.functionReturned();
            System.out.println("Move done");
            UserIO.addToTemporaryOutput(Integer.toString(neighborChoice));
            UserIO.addToTemporaryOutput("successful");
        }
    }

    /**
     * Bányászás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean mine() {
        TestLogger.functionCalled(this, "mine", "boolean");
        if (asteroid.mined(this)) {
            setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            System.out.println("Mine done");
            UserIO.addToTemporaryOutput("successful");
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        System.out.println("Mine NOT done");
        UserIO.addToTemporaryOutput("unsuccessful");
        return false;
    }

    /**
     * Visszaadja az Inventory-t.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public Inventory getInventory() {
        TestLogger.functionCalled(this, "getInventory", "inventory");
        TestLogger.functionReturned("inventory");
        return inventory;

    }

    /**
     * Teleportkapu-pár építés.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void buildPortal() {
        TestLogger.functionCalled(this, "portal", "boolean");
        BillOfPortal billOfPortal = new BillOfPortal();
        if (billOfPortal.hasEnoughMaterials(this.inventory.getMaterials())
                && this.inventory.getPortals().size() < this.inventory.getPortalCapacity()) {
            billOfPortal.pay(inventory.getMaterials());
            Portal p1 = new Portal();
            Portal p2 = new Portal();
            p1.setPair(p2);
            p2.setPair(p1);
            this.inventory.addPortal(p1);
            this.inventory.addPortal(p2);
            this.setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            System.out.println("You built a portal!");
            UserIO.addToTemporaryOutput("successful");
        } else {
            TestLogger.functionReturned(String.valueOf(false));
            System.out.println("You could not build a portal!");
            UserIO.addToTemporaryOutput("unsuccessful");
        }
    }

    /**
     * Robot építés.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void buildRobot() {
        TestLogger.functionCalled(this, "robot", "void");
        BillOfRobot billOfRobot = new BillOfRobot();

        if (billOfRobot.hasEnoughMaterials(this.inventory.getMaterials())
                && this.inventory.getPortals().size() < this.inventory.getPortalCapacity()) {
            billOfRobot.pay(inventory.getMaterials());
            this.asteroid.addFigure(new Robot(this.asteroid, true));
            this.setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            System.out.println("You built a robot!");
            UserIO.addToTemporaryOutput("successful");
        } else {
            TestLogger.functionReturned(String.valueOf(false));
            System.out.println("You could not build a robot!");
            UserIO.addToTemporaryOutput("unsuccessful");
        }
    }

    /**
     * Bázis építés.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void buildBase() {
        TestLogger.functionCalled(this, "base", "void");
        BillOfBase billOfBase = new BillOfBase();
        if (billOfBase.hasEnoughMaterials(this.asteroid.summarizeMaterials())) {
            this.setRoundFinished(true);
            this.asteroid.getMap().gameEnd(true);
            this.asteroid.getMap().switchGameState(GameState.WON);
            TestLogger.functionReturned(String.valueOf(true));
            UserIO.addToTemporaryOutput("successful");
            return;
        }
        TestLogger.functionReturned(String.valueOf(false));
        System.out.println("You could not build a base!");
        UserIO.addToTemporaryOutput("unsuccessful");
    }

    /**
     * Teleportkapu lerakása.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void putPortalDown() {
        TestLogger.functionCalled(this, "putPortalDown", "boolean");
        ArrayList<Portal> portals = inventory.getPortals();
        if (portals.size() >= 1) {
            this.asteroid.addPortal(portals.get(0));
            this.inventory.removePortal(portals.get(0));
            this.setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            UserIO.addToTemporaryOutput("successful");
            return;
        }
        TestLogger.functionReturned(String.valueOf(false));
        UserIO.addToTemporaryOutput("unsuccessful");
        return;
    }

    /**
     * Nyersanyag visszarakása üres aszteroidába.
     *
     * @param m
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void putMaterialBack(Material m) {
        TestLogger.functionCalled(this, "putMaterialBack", "boolean");
        if (this.asteroid.setMaterial(m)) {
            this.inventory.removeMaterial(m);
            this.setRoundFinished(true);
            UserIO.addToTemporaryOutput("successful");
            TestLogger.functionReturned(String.valueOf(true));
            UserIO.addToTemporaryOutput("successful");
            return;
        }
        TestLogger.functionReturned(String.valueOf(false));
        UserIO.addToTemporaryOutput("unsuccessful");
    }

    /**
     * Nyersanyag kiválasztása az Inventory-ból.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Material chooseMaterial() {
        ArrayList<Material> allMaterials = getInventory().getMaterials();
        HashMap<String, Material> materialNames = new HashMap<>();
        System.out.println("Your inventory has the following:");
        for (Material m : allMaterials) {
            String name = m.getClass().toString().replace("class Materials.", "");
            materialNames.put(name.toLowerCase(), m);
            System.out.println(name);
        }
        if (allMaterials.size() == 0) {
            System.out.println("You don't have any materials!");
            UserIO.addToTemporaryOutput("unsuccessful");
            return null;
        }

        String materialChoice = UserIO.currentLine().size() > 1 ? UserIO.currentLine().get(1) : UserIO.readString();
        materialChoice = materialChoice.toLowerCase();
        if (!allMaterials.contains(materialNames.get(materialChoice))) {
            System.out.println("Not a valid choice, sorry!");
            UserIO.addToTemporaryOutput("unsuccessful");
            return null;
        }
        System.out.println("The chosen material is: " + materialNames.get(materialChoice).getClass().toString().replace("class Materials.", ""));
        Material chosenMaterial = materialNames.get(materialChoice);
        UserIO.addToTemporaryOutput(chosenMaterial.toString());
        return chosenMaterial;
    }

    /**
     * Reagálás robbanásra: meghal.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this, "onExplosion", "void");
        this.die();
        TestLogger.functionReturned();
    }

    private void drawSettlerInfo(VBox vBox) {
        Text text = DrawFunctions.text("Current settler:\n" + this, 15);
        vBox.getChildren().add(text);
    }

    private void drawInventory(VBox vBox, Rectangle2D screenBounds) {
        FlowPane inventory = new FlowPane();
        inventory.setHgap(10);
        inventory.setAlignment(Pos.CENTER);
        inventory.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10.0), Insets.EMPTY)));
        for (Material material : this.inventory.getMaterials()) {
            double imgSize = screenBounds.getWidth() / 30;
            ImageView imageView = DrawFunctions.image(material.getImagePath(), imgSize);
            inventory.getChildren().add(imageView);
        }
        vBox.getChildren().add(inventory);
    }

    private void drawPortals(VBox vBox, Rectangle2D screenBounds) {

    }

    private void drawActions(VBox vBox, Rectangle2D screenBounds) {

    }

    private void drawPortalsAndActions(VBox vBox, Rectangle2D screenBounds) {
        drawPortals(vBox, screenBounds);
        drawActions(vBox, screenBounds);
    }

    private void drawAsteroidDetails(VBox vBox, Rectangle2D screenBounds) {
        HBox detailContainer = new HBox(25);

        double imgSize = screenBounds.getWidth() / 10;
        String imgPath = this.getAsteroid().getImage();
        ImageView imageView = DrawFunctions.image(imgPath, imgSize);

        detailContainer.getChildren().add(imageView);

        VBox details = new VBox(10);
        details.setAlignment(Pos.CENTER_LEFT);
        Text asteroidName = DrawFunctions.text("Asteroid ID:\n" + this.getAsteroid().toString().replace("Playground.Asteroid@", ""), 15);
        Text asteroidLayers = DrawFunctions.text("Layers: " + this.getAsteroid().getLayers(), 15);
        Text asteroidCore = DrawFunctions.text("Material:\n" + this.getAsteroid().getMaterial().toString().replace("Materials.", ""), 15);

        details.getChildren().addAll(asteroidName, asteroidLayers, asteroidCore);

        detailContainer.getChildren().add(details);

        vBox.getChildren().add(detailContainer);
    }

    /**
     * Kirajzolja a telepes inventoriját, lehetséges lépéseit és az aszteroidájának az adatait
     *
     * @param root
     */
    public void drawGUI(Group root, Rectangle2D screenBounds) {
        double width = screenBounds.getWidth() / 5;
        double height = screenBounds.getHeight();
        double posX = 4 * screenBounds.getWidth() / 5;
        double posY = 0;

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setLayoutX(posX);
        mainContainer.setLayoutY(posY);
        mainContainer.setPrefWidth(width);
        mainContainer.setPrefHeight(height);
        mainContainer.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));

        drawSettlerInfo(mainContainer);
        drawInventory(mainContainer, screenBounds);
        drawPortalsAndActions(mainContainer, screenBounds);
        drawAsteroidDetails(mainContainer, screenBounds);

        root.getChildren().add(mainContainer);
    }

    /**
     * Lépés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void step(Group root, Rectangle2D screenBounds) throws IOException {
        TestLogger.functionCalled(this, "step", "void");
        if (UserIO.isConsole()) {
            if (!this.roundFinished) {
                UserIO.clearTemporaryInput();
                ArrayList<String> choice;

                choice = UserIO.readLine();

                System.out.println("What would you like to do?");
                System.out.println("(drill) Drill the asteroid");
                System.out.println("(mine) Mine the asteroid core");
                System.out.println("(move) Move the settler");
                System.out.println("(moveThroughPortal) Move the settler through a portal");
                System.out.println("(build) Build something");
                System.out.println("(putPortalDown) Place a portal on the current asteroid");
                System.out.println("(putMaterialBack) Fill the asteroid's core with a selected material");
                System.out.println("(show) Show details about the current asteroid");
                System.out.println("(solarStorm) Generate a storm near a given asteroid");
                if (!UserIO.readFromFile())
                    System.out.println("(save;filename.txt) Save the user input as a test case in the specified file");
                switch (choice.get(0).toLowerCase()) {
                    case "drill":
                        UserIO.addToTemporaryOutput("drill");
                        drill();
                        UserIO.addToCustomInput();
                        break;
                    case "mine":
                        UserIO.addToTemporaryOutput("mine");
                        mine();
                        UserIO.addToCustomInput();
                        break;
                    case "move":
                        UserIO.addToTemporaryOutput("move");
                        move();
                        UserIO.addToCustomInput();
                        break;
                    case "movethroughportal":
                        UserIO.addToTemporaryOutput("movethroughportal");
                        moveThroughPortal();
                        UserIO.addToCustomInput();
                        break;
                    case "build":
                        UserIO.addToTemporaryOutput("build");
                        build();
                        UserIO.addToCustomInput();
                        break;
                    case "putportaldown":
                        UserIO.addToTemporaryOutput("putPortalDown");
                        putPortalDown();
                        UserIO.addToCustomInput();
                        break;
                    case "putmaterialback":
                        UserIO.addToTemporaryOutput("putMaterialBack");
                        Material m = chooseMaterial();
                        putMaterialBack(m);
                        UserIO.addToCustomInput();
                        break;
                    case "show":
                        this.getAsteroid().printAsteroidDetails();
                        UserIO.clearTemporaryInput();
                        break;
                    case "save":
                        if (UserIO.readFromFile())
                            break;
                        UserIO.clearTemporaryInput();
                        UserIO.saveCustomIO(UserIO.Phase.TEST, UserIO.currentLine().get(1));
                        break;
                    case "solarstorm":
                        UserIO.addToTemporaryOutput("solarstorm");
                        this.asteroid.getMap().solarStorm(this.asteroid);
                        UserIO.addToResultOutput();
                        break;
                    case "quit":
                        this.getAsteroid().getMap().switchGameState(GameState.WON);
                        break;
                    default:
                        System.out.println("Something went wrong! Check the test files!");
                        UserIO.clearTemporaryInput();
                        break;
                }
                TestLogger.functionReturned();
            }
        } else {
            //setRoundFinished(true); //TODO itt kellene kezelni majd valahogy a köröket
        }
    }

    /**
     * Teleportkapun keresztüli mozgás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean moveThroughPortal() {
        int i = 0;
        ArrayList<Asteroid> tmpArray = new ArrayList<>();
        if (asteroid.getPortals().size() != 0) {
            System.out.println("Which portal would you like to go through?");
            for (Portal p : asteroid.getPortals()) {
                System.out.println(i + ". asteroid: " + p.getPair().getAsteroid());
                tmpArray.add(p.getPair().getAsteroid());
                i++;
            }

            int portalChoice = -1;
            while (portalChoice > tmpArray.size() || portalChoice < 0) {
                portalChoice = UserIO.currentLine().size() > 1
                        ? Integer.parseInt(UserIO.currentLine().get(1))
                        : UserIO.readInt();
                if (portalChoice > tmpArray.size() || portalChoice < 0) {
                    System.out.println("That is not a valid index!");
                    System.out.println("Which neighbor would you like to pick?");
                }
            }

            asteroid.removeFigure(this);
            tmpArray.get(portalChoice).addFigure(this);
            setAsteroid(tmpArray.get(portalChoice));
            this.setRoundFinished(true);
            UserIO.addToTemporaryOutput(Integer.toString(portalChoice));
            UserIO.addToTemporaryOutput("successful");
            return true;
        } else {
            UserIO.addToTemporaryOutput("unsuccessful");
            return false;
        }
    }

    /**
     * Építés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    private void build() {
        System.out.println("What would you like to build?");
        System.out.println("(any key) Nothing");
        System.out.println("(portal) Build Portal");
        System.out.println("(robot) Build Robot");
        System.out.println("(base) Build Base");

        if (UserIO.currentLine().size() < 2) {
            UserIO.currentLine().add(UserIO.readLine().get(0));
        }

        switch (UserIO.currentLine().get(1)) {
            case "portal":
                UserIO.addToTemporaryOutput("portal");
                buildPortal();
                break;
            case "robot":
                UserIO.addToTemporaryOutput("robot");
                buildRobot();
                break;
            case "base":
                UserIO.addToTemporaryOutput("base");
                buildBase();
                break;
            default:
                System.out.println("Wrong number.");
        }
    }
}
