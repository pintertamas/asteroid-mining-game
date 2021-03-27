package Playground;

import Bills.*;
import Entities.Figure;
import Entities.Settler;
import Entities.Ufo;
import Materials.*;
import Test.TestLogger;

import java.util.*;

public class Map {

    final ArrayList<Asteroid> asteroids;
    GameState gameState;
    boolean manual = false;

    public Map() {
        this.asteroids = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
    }

    public void setGameState(GameState gs) {
        TestLogger.functionCalled(this, "setGameState", "Playground.GameState gs", "void");
        this.gameState = gs;
        TestLogger.functionReturned();
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void addAsteroid(Asteroid a) {
        TestLogger.functionCalled(this, "AddAsteroid", "Playground.Asteroid a", "void");
        this.asteroids.add(a);
        TestLogger.functionReturned();
    }

    public void removeAsteroid(Asteroid a) {
        TestLogger.functionCalled(this, "removeAsteroid", "Playground.Asteroid a", "void");
        this.asteroids.remove(a);
        TestLogger.functionReturned();
    }

    public ArrayList<Asteroid> getAsteroids() {
        return this.asteroids;
    }

    public void setManual(boolean bool) {
        this.manual = bool;
    }

    public void initGame(int numberOfPlayers) {
        TestLogger.functionCalled(this, "initGame", "int numberOfPlayers", "void");

        //Változók amelyeket a player inputnál használunk fel
        int numberOfAsteroid = -1;
        int mat = -1;
        int sun = -1;
        int hollow = -1;
        int layer = -1;
        int numberOfNeighbours = -1;
        boolean nearSun = false;
        boolean isHollow = false;
        int neighbour = -1;
        int numberOfUfos = -1;
        int ufoAsteroid = -1;
        int settlerAsteroid = -1;

        //Itt lehet az asteroidák számát megadni
        System.out.println("Mennyi asteroidát szeretnél a pályára, add meg:");
        Scanner kb = new Scanner(System.in);
        if (kb.hasNextInt()) {
            numberOfAsteroid = kb.nextInt();
        }


        //Az asteroidák belső tulajdonságainak az eldöntése:
        for (int i = 0; i < numberOfAsteroid; i++) {
            while (mat < 0 || mat > 3) {
                System.out.println("Az " + i + ". asteroidának milyen legyen a belseje?: ");
                System.out.println("0 - Uranium");
                System.out.println("1 - Ice");
                System.out.println("2 - Coal");
                System.out.println("3 - Iron");
                kb = new Scanner(System.in);
                if (kb.hasNextInt()) {
                    mat = kb.nextInt();
                }
            }
            Material m = null;
            switch (mat) {
                case 0:
                    m = new Uranium();
                case 1:
                    m = new Ice();
                    break;
                case 2:
                    m = new Coal();
                    break;
                case 3:
                    m = new Iron();
                    break;
            }

            //Napközelben legyen-e vagy sem az asteroida:
            while (sun < 0 || sun > 1) {
                System.out.println("Napközelben legyen az asteroida?");
                System.out.println("1 - Igen");
                System.out.println("0 - Nem");
                kb = new Scanner(System.in);
                if (kb.hasNextInt()) {
                    sun = kb.nextInt();
                }
            }
            switch (sun) {
                case 0:
                    nearSun = false;
                case 1:
                    nearSun = true;
                    break;
                default:
                    System.out.println("Not a valid number dummy.");
            }


            while (hollow < 0 || hollow > 1) {
                System.out.println("Üreges legyen az asteroida? ");
                System.out.println("0 - Nem");
                System.out.println("1 - Igen");
                kb = new Scanner(System.in);
                if (kb.hasNextInt()) {
                    hollow = kb.nextInt();
                }
            }
            switch (hollow) {
                case 0:
                    isHollow = false;
                case 1:
                    isHollow = true;
                    break;
                default:
                    System.out.println("Not a valid number dummy.");
            }

            //Mennyi legyen a layer az asteroidán:
            System.out.println("Milyen vastag legyen az asteroida köpenye?");
            kb = new Scanner(System.in);
            if (kb.hasNextInt()) {
                layer = kb.nextInt();
            }

            //Asteroida létrehozása:
            Asteroid ast = new Asteroid(this, m, layer, nearSun, isHollow);
            asteroids.add(ast);

            //Szomszédok beállítása számának beállítása:
            for (int j = 0; j < numberOfAsteroid; j++) {
                System.out.println("A(z) " + j + ". asteroidának hány szomszédja legyen?");
                while (numberOfNeighbours < 0 || numberOfNeighbours > numberOfAsteroid - 1) {
                    kb = new Scanner(System.in);
                    if (kb.hasNextInt()) {
                        numberOfNeighbours = kb.nextInt();
                    }
                }

                //Egyesével beállítani ki legyen a szomszéd:
                for (int k = 0; k < numberOfNeighbours; k++) {
                    System.out.println("Mi legyen a" + k + ". szomszád, adj meg egy asteroidát a sorszámával.");
                    while (neighbour < 0 || neighbour > numberOfAsteroid - 1 || k == neighbour) {
                        kb = new Scanner(System.in);
                        if (kb.hasNextInt()) {
                            neighbour = kb.nextInt();
                        }
                    }
                    asteroids.get(k).addNeighbor(asteroids.get(neighbour));
                }
            }

            //Ufok létrehozása:
            System.out.println("Mennyi ufot szeretnél szeretnél a pályára, add meg:");
            kb = new Scanner(System.in);
            if (kb.hasNextInt()) {
                numberOfUfos = kb.nextInt();
            }
            for (int j = 0; j < numberOfUfos; j++) {
                System.out.println("A(z) " + j + ". ufo melyik asteroidán legyen, add meg a sorszámát:");
                while (ufoAsteroid < 0 || ufoAsteroid > numberOfAsteroid) {
                    kb = new Scanner(System.in);
                    if (kb.hasNextInt()) {
                        ufoAsteroid = kb.nextInt();
                    }
                }
                Ufo f = new Ufo(asteroids.get(ufoAsteroid), false);
                asteroids.get(ufoAsteroid).addFigure(f);
            }

            //Settlerek létrehozása:
            for (int j = 0; j < numberOfPlayers; j++) {
                System.out.println("A(z) " + j + ". settler melyik asteroidán legyen, add meg a sorszámát:");
                while (settlerAsteroid < 0 || settlerAsteroid > numberOfAsteroid) {
                    kb = new Scanner(System.in);
                    if (kb.hasNextInt()) {
                        settlerAsteroid = kb.nextInt();
                    }
                }
                Settler s = new Settler(asteroids.get(settlerAsteroid), false);
                asteroids.get(settlerAsteroid).addFigure(s);
            }
        }

        TestLogger.functionReturned();
    }

    public void solarStorm() {
        TestLogger.functionCalled(this, "solarStorm", "void");
        if (manual == false) {
            for (Asteroid a : drawSolarArea()) {
                if (a.isNearSun == true) {
                    a.handleFigures();
                }
            }
        } else {
            System.out.println("Do you want to have a solar storm this turn?");
            System.out.println("Yes - Press 1");
            System.out.println("No - Press 0");
            Scanner kb = new Scanner(System.in);
            int num = 0;
            if (kb.hasNextInt()) {
                num = kb.nextInt();
            }
            if (num == 1) {
                for (Asteroid a : drawSolarArea()) {
                    if (a.isNearSun == true) {
                        a.handleFigures();
                    }
                }
            }
        }
        TestLogger.functionReturned();
    }

    public ArrayList<Asteroid> drawSolarArea() {
        ArrayList<Asteroid> tmp = new ArrayList<>();
        Random rand = new Random();
        int asteroidNumber = rand.nextInt(asteroids.size());
        tmp.add(asteroids.get(asteroidNumber));
        for(Asteroid a : asteroids.get(asteroidNumber).getNeighbors()) {
            tmp.add(a);
        }
        return tmp;
    }

    public boolean checkGameEnd() {
        TestLogger.functionCalled(this, "checkGameEnd", "boolean");
        if (!checkIfWinnable()) {
            gameState = GameState.LOST;
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public boolean hasAllMaterials() {
        TestLogger.functionCalled(this, "hasAllMaterials", "boolean");
        ArrayList<Material> allMaterials = new ArrayList<>();
        for (Asteroid asteroid : asteroids) {
            for (Figure f : asteroid.getFigures())
                allMaterials.addAll(f.getInventory().getMaterials());
            if (!asteroid.isHollow)
                allMaterials.add(asteroid.getMaterial());
        }
        boolean hasAll = new BillOfBase().hasEnoughMaterials(allMaterials);
        TestLogger.functionReturned(String.valueOf(hasAll));
        return hasAll;
    }

    private boolean hasAnyFigure() {
        for (Asteroid a : asteroids) {
            if (a.getFigures().size() > 0)
                return true;
        }
        return false;
    }

    /**
     * huhhuuuu
     *
     * @return
     */
    public boolean checkIfWinnable() {
        TestLogger.functionCalled(this, "checkIfWinnable", "boolean");

        boolean hasAll = hasAllMaterials() && hasAnyFigure();
        TestLogger.functionReturned(String.valueOf(hasAll));
        return hasAll;
    }

    public void setupRound() {
        TestLogger.functionCalled(this, "setupRound", "void");
        if (stormComing()) {
            for (Asteroid a : asteroids) {
                a.handleFigures();
            }
        } else {
            for (Asteroid a : asteroids) {
                a.invokeFigures();
            }
        }
        TestLogger.functionReturned();
    }

    public void gameEnd(boolean b) {
        TestLogger.functionCalled(this, "gameEnd", "boolean b", "void");

        TestLogger.functionReturned();
    }

    public boolean stormComing() {
        TestLogger.functionCalled(this, "stormComing", "boolean");
        Random rand = new Random();
        int stormNumber = rand.nextInt(200);
        if (stormNumber == 100) {
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    public void reset() {
        TestLogger.functionCalled(this, "reset", "void");
        for (Asteroid a : asteroids) {
            a.resetStep();
        }
        TestLogger.functionReturned();
    }
}

