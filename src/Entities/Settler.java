package Entities;

import Bills.*;
import Interfaces.IDrill;
import Interfaces.IMine;
import Materials.*;
import Playground.*;
import Test.TestLogger;
import Test.UserIO;

import java.io.IOException;
import java.util.ArrayList;

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
        } else {
            this.asteroid.removeFigure(this);
            neighbors.get(neighborChoice).addFigure(this);
            this.setAsteroid(neighbors.get(neighborChoice));
            this.setRoundFinished(true);
            TestLogger.functionReturned();
            System.out.println("Move done");
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
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        System.out.println("Mine NOT done");
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
    public boolean buildPortal() {
        TestLogger.functionCalled(this, "buildPortal", "boolean");
        BillOfPortal billOfPortal = new BillOfPortal();
        if (billOfPortal.hasEnoughMaterials(this.inventory.getMaterials())) {
            billOfPortal.pay(billOfPortal.getBill());
            Portal p1 = new Portal();
            Portal p2 = new Portal();
            p1.setPair(p2);
            p2.setPair(p1);
            this.inventory.addPortal(p1);
            this.inventory.addPortal(p2);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        } else {
            TestLogger.functionReturned(String.valueOf(false));
            return false;
        }
    }

    /**
     * Robot építés.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean buildRobot() {
        TestLogger.functionCalled(this, "buildRobot", "void");
        BillOfRobot billOfRobot = new BillOfRobot();

        if (!billOfRobot.hasEnoughMaterials(this.getInventory().getMaterials())) {
            TestLogger.functionReturned(String.valueOf(false));
            return false;
        }

        billOfRobot.pay(billOfRobot.getBill());
        new Robot(this.asteroid, true);
        this.setRoundFinished(true);
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    /**
     * Bázis építés.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean buildBase() {
        TestLogger.functionCalled(this, "buildBase", "void");
        BillOfBase billOfBase = new BillOfBase();
        if (billOfBase.hasEnoughMaterials(this.asteroid.summarizeMaterials())) {
            //TODO: WIN!
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    /**
     * Teleportkapu lerakása.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean putPortalDown() {
        TestLogger.functionCalled(this, "putPortalDown", "boolean");
        ArrayList<Portal> portals = inventory.getPortals();
        if (portals.size() >= 1) {
            this.asteroid.addPortal(portals.get(0));
            this.inventory.removePortal(portals.get(0));
            this.setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    /**
     * Nyersanyag visszarakása üres aszteroidába.
     *
     * @param m
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean putMaterialBack(Material m) {
        TestLogger.functionCalled(this, "putMaterialBack", "boolean");
        if (this.asteroid.setMaterial(m)) {
            this.inventory.removeMaterial(m);
            this.setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    /**
     * Nyersanyag kiválasztása az Inventory-ból.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Material chooseMaterial() {
        ArrayList<Material> allMaterials = getInventory().getMaterials();
        System.out.println("Your inventory has the following:");
        int i = 0;
        for (Material m : allMaterials) {
            System.out.println(++i + "\t" + m.getClass().toString().replace("class Materials.", ""));
        }
        if (allMaterials.size() == 0) {
            System.out.println("You don't have any materials!");
            return null;
        }
        int materialChoice = Integer.parseInt(UserIO.currentLine().get(1));
        if (materialChoice < 0 || materialChoice > allMaterials.size() + 1) {
            System.out.println("Not a valid choice, sorry!");
            return null;
        }
        System.out.println("The chosen material is: " +
                allMaterials.get(materialChoice - 1).getClass().toString().replace("class Materials.", ""));
        return getInventory().getMaterials().get(materialChoice - 1);
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

    /**
     * Lépés.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void step() throws IOException {
        TestLogger.functionCalled(this, "step", "void");
        if (!this.roundFinished) {
            System.out.println("What would you like to do?");
            System.out.println("(drill) Drill the asteroid");
            System.out.println("(mine) Mine the asteroid core");
            System.out.println("(move) Move the settler");
            System.out.println("(build) Build something");
            System.out.println("(putPortalDown) Place a portal on the current asteroid");
            System.out.println("(putMaterialBack) Fill the asteroid's core with a selected material");
            System.out.println("(show) Show details about the current asteroid");
            if (!UserIO.readFromFile())
                System.out.println("(save;filename.txt) Save the user input as a test case in the specified file");

            UserIO.clearTemporaryInput();
            ArrayList<String> choice = UserIO.readLine();
            switch (choice.get(0).toLowerCase()) {
                case "drill":
                    drill();
                    UserIO.addToCustomInput();
                    break;
                case "mine":
                    mine();
                    UserIO.addToCustomInput();
                    break;
                case "move":
                    move();
                    UserIO.addToCustomInput();
                    break;
                case "movethroughportal":
                    moveThroughPortal();
                    UserIO.addToCustomInput();
                    break;
                case "build":
                    build();
                    UserIO.addToCustomInput();
                    break;
                case "putportaldown":
                    putPortalDown();
                    UserIO.addToCustomInput();
                    break;
                case "putmaterialback":
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
                    UserIO.saveCustomInput();
                    break;
                default:
                    System.out.println("Something went wrong! Check the test files!");
                    UserIO.clearTemporaryInput();
                    break;
            }
            TestLogger.functionReturned();
        }
    }

    /**
     * Teleportkapun keresztüli mozgás.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean moveThroughPortal() {
        int i = 1;
        ArrayList<Asteroid> tmpArray = new ArrayList<>();
        if (asteroid.getPortals().size() != 0) {
            System.out.println("Which portal would you like to go through? Choose the number seen before its memory code! ");
            for (Portal p : asteroid.getPortals()) {
                if (p.getPair().getAsteroid() != null) {
                    System.out.println("Az " + i + ". asteroida ahova el tudsz mozogni: " + p.getPair().getAsteroid());
                    tmpArray.add(p.getPair().getAsteroid());
                    i++;
                }
            }

            int choice = UserIO.readInt();

            asteroid.removeFigure(this);
            tmpArray.get(choice - 1).addFigure(this);
            setAsteroid(tmpArray.get(choice - 1));
            this.setRoundFinished(true);
        } else {
            return false;
        }
        return false;
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
                buildPortal();
                break;
            case "robot":
                buildRobot();
                break;
            case "base":
                buildBase();
                break;
            default:
                System.out.println("Wrong number.");
        }
    }
}
