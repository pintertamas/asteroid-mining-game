import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Settler extends Figure {
    private final Inventory inventory;

    public Settler(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
        this.inventory = new Inventory();
    }

    @Override
    public void move() {
        TestLogger.functionCalled(this, "move", "void");
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        if(neighbors.size() == 0){
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
        }
        if (neighborChoice == -1) {
            System.out.println("Move NOT done, back");
            return;
        }
        else {
            this.asteroid.removeFigure(this);
            neighbors.get(neighborChoice).addFigure(this);
            this.setAsteroid(neighbors.get(neighborChoice));
            this.setRoundFinished(true);
            TestLogger.functionReturned();
            System.out.println("Move done");
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
            billOfPortal.pay(billOfPortal.bill);
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

        billOfRobot.pay(billOfRobot.bill);
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
        ArrayList<Portal> p = inventory.getPortals();
        if (p.size() == 2 || p.size() == 1) {
            this.asteroid.addPortal(p.get(0));
            this.inventory.removePortal(p.get(0));
            this.setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public boolean putMaterialBack(Material m) {
        TestLogger.functionCalled(this, "putMaterialBack", "boolean");
        if (this.asteroid.coreChanged(m)) {
            this.inventory.removeMaterial(m);
            this.setRoundFinished(true);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public Material chooseMaterial() {
        return new Iron();
    }

    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this, "onExplosion", "void");
        this.die();
        TestLogger.functionReturned();
    }

    @Override
    public void step() {
        TestLogger.functionCalled(this, "step", "void");
        while(!this.roundFinished){
            System.out.println("What would you like to do?");
            System.out.println("0 - drill");
            System.out.println("1 - mine");
            System.out.println("2 - move");
            System.out.println("3 - build");
            System.out.println("4 - put down portal");
            System.out.println("5 - put back material");
            Scanner kb = new Scanner(System.in);
            int choice = 0;
            if(kb.hasNextInt()) {
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
                    break;
                default:
                    break;
            }
            TestLogger.functionReturned();
        }
    }
}
