package Entities;

import Bills.*;
import Interfaces.IDrill;
import Interfaces.IMine;
import Materials.*;
import Playground.*;
import Test.TestLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Telepes osztály, képes mozogni, fúrni és bányászni.
 * A Figure leszármazottja.
 */
public class Settler extends Figure implements IMine, IDrill {
    private final Inventory inventory;

    /**
     * Konstruktor.
     *
     * @param asteroid
     * @param roundFinished
     */
    public Settler(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
        this.inventory = new Inventory();
    }

    /**
     * Mozgás.
     */
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
        int neighborChoice = -2;
        while (neighborChoice <= -1 || neighborChoice > neighbors.size() - 1) {
            System.out.println("Pick an asteroid by its number:");
            Scanner kb = new Scanner(System.in);

            if (kb.hasNextInt()) {
                neighborChoice = kb.nextInt();
            }
            if (neighborChoice == -1) {
                System.out.println("Move NOT done, back");
                return;
            } else {
                this.asteroid.removeFigure(this);
                neighbors.get(neighborChoice).addFigure(this);
                this.setAsteroid(neighbors.get(neighborChoice));
                this.setRoundFinished(true);
                TestLogger.functionReturned();
                System.out.println("Move done");
            }
        }
    }

    /**
     * Bányászás.
     *
     * @return
     */
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
        Scanner in = new Scanner(System.in);
        int materialChoice = in.nextInt();
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
    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this, "onExplosion", "void");
        this.die();
        TestLogger.functionReturned();
    }

    /**
     * Lépés.
     */
    public void step() {
        TestLogger.functionCalled(this, "step", "void");
        while (!this.roundFinished) {
            System.out.println("What would you like to do?");
            System.out.println("0 - drill");
            System.out.println("1 - mine");
            System.out.println("2 - move");
            System.out.println("3 - build");
            System.out.println("4 - put down portal");
            System.out.println("5 - put back material");
            System.out.println("6 - Show details about the current asteroid");
            Scanner kb = new Scanner(System.in);
            int choice = 0;
            if (kb.hasNextInt()) {
                choice = kb.nextInt();
            }
            switch (choice) {
                case 0:
                    drill();
                    break;
                case 1:
                    mine();
                    break;
                case 2:
                    move();
                    break;
                case 3:
                    build();
                    break;
                case 4:
                    putPortalDown();
                    break;
                case 5:
                    Material m = chooseMaterial();
                    putMaterialBack(m);
                    break;
                case 6:
                    this.getAsteroid().printAsteroidDetails();
                    break;
                default:
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

            int choice = 5;
            while (choice <= 0 || choice >= i) {
                Scanner kb = new Scanner(System.in);
                if (kb.hasNextInt()) {
                    choice = kb.nextInt();
                }
            }
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
    private void build() {
        System.out.println("What would you like to build?");
        System.out.println("0 - Nothing");
        System.out.println("1 - Build Portal");
        System.out.println("2 - Build Robot");
        System.out.println("3 - Build Base");
        Scanner kb = new Scanner(System.in);
        int choice = 0;
        if (kb.hasNextInt()) {
            choice = kb.nextInt();
        }
        switch (choice) {
            case 0:
                return;
            case 1:
                buildPortal();
                break;
            case 2:
                buildRobot();
                break;
            case 3:
                buildBase();
                break;
            default:
                System.out.println("Wrong number.");
        }
    }
}
