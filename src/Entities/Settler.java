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

public class Settler extends Figure implements IMine, IDrill {
    private final Inventory inventory;

    public Settler(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
        this.inventory = new Inventory();
    }

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

    @Override
    public Inventory getInventory() {
        TestLogger.functionCalled(this, "getInventory", "inventory");
        TestLogger.functionReturned("inventory");
        return inventory;

    }

    public boolean buildPortal() {
        TestLogger.functionCalled(this, "buildPortal", "boolean");
        BillOfPortal billOfPortal = new BillOfPortal();
        if (billOfPortal.hasEnoughMaterial(this.inventory.getMaterials())) {
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

    public boolean buildRobot() {
        TestLogger.functionCalled(this, "buildRobot", "void");
        BillOfRobot billOfRobot = new BillOfRobot();
        HashMap<Class<?>, Integer> materials = new HashMap<>();
        for (Class<?> m : this.inventory.getMaterials().keySet()) {
            if (materials.containsKey(m))
                materials.put(m, materials.get(m) + this.inventory.getMaterials().get(m));
            else materials.put(m, this.inventory.getMaterials().get(m));
        }
        if (!billOfRobot.hasEnoughMaterial(materials)) {
            TestLogger.functionReturned(String.valueOf(false));
            return false;
        }

        billOfRobot.pay(billOfRobot.getBill());
        Robot r = new Robot(this.asteroid, false);
        r.setRoundFinished(true);
        TestLogger.functionReturned(String.valueOf(true));
        return true;
    }

    public boolean buildBase() {
        TestLogger.functionCalled(this, "buildBase", "void");
        BillOfBase billOfBase = new BillOfBase();
        if (billOfBase.hasEnoughMaterial(this.asteroid.summarizeMaterials())) {
            //TODO: WIN!
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

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

    public Material chooseMaterial() {
        return new Iron(); // TODO
    }

    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this, "onExplosion", "void");
        this.die();
        TestLogger.functionReturned();
    }

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
                default:
                    break;
            }
            TestLogger.functionReturned();
        }
    }

    public boolean moveThroughPortal() {
        int i = 1;
        ArrayList<Asteroid> tmpArray = new ArrayList<>();
        if(asteroid.getPortals().size()!=0) {
            System.out.println("Which portal you would like to go through, choose the number seen before its memory code? ");
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
        }
        else {
            return false;
        }
        return false;
    }

    private void build() {

    }
}
