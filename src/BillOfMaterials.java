import java.util.ArrayList;
import java.util.HashMap;

public class BillOfMaterials {

    HashMap<Material, Integer> bill;
    ArrayList<Material> listOfMaterials;

    public BillOfMaterials() {
        bill = new HashMap<>();
        listOfMaterials = new ArrayList<>();
    }

    public void addMaterial(Material m) {
        listOfMaterials.add(m);
    }

    public ArrayList<Material> getMaterials() {
        return listOfMaterials;
    }

    public boolean hasEnoughMaterial(/*HashMap<Material, Integer> inventoryMaterials*/ Material iron, Material uranium, Material ice, Material coal, Settler s) {
        /*
        for (Material m : inventoryMaterials.keySet()) {
            if (bill.get(m) > inventoryMaterials.get(m)) {
                TestLogger.errorMessage("Insufficient materials!");
                return false;
            }
        }
        return true;
        */
        for(int i=0; i<2; i++) {
            for(int j=0; j<s.getBillOfMaterials().listOfMaterials.size(); j++) {
                if(s.getBillOfMaterials().listOfMaterials.get(j) == iron) {
                    s.getBillOfMaterials().listOfMaterials.remove(s.getBillOfMaterials().listOfMaterials.get(j));
                    System.out.println("Kivettünk egy vasat");
                } else
                {
                    System.out.println("Nem vettünk ki egy vasat sem");
                }
            }
        }
        for(int i=0; i<1; i++) {
            for(int j=0; j<s.getBillOfMaterials().listOfMaterials.size(); j++) {
                if(s.getBillOfMaterials().listOfMaterials.get(j) == ice) {
                    s.getBillOfMaterials().listOfMaterials.remove(s.getBillOfMaterials().listOfMaterials.get(j));
                    System.out.println("Kivettünk egy jeget");
                }
            }
        }
        for(int i=0;i<1; i++) {
            for(int j=0; j<s.getBillOfMaterials().listOfMaterials.size(); j++) {
                if(s.getBillOfMaterials().listOfMaterials.get(j) == uranium) {
                    s.getBillOfMaterials().listOfMaterials.remove(s.getBillOfMaterials().listOfMaterials.get(j));
                }
            }
        }

        if(s.getBillOfMaterials().listOfMaterials.size() == s.getInventory().getMats().size()-4) {
            s.getBillOfMaterials().listOfMaterials.clear();
            return true;
        }
        else {
            s.getBillOfMaterials().listOfMaterials.clear();
            return false;
        }
    }

    public boolean isNeeded(Material m) {
        return bill.get(m) > 0;
    }

    public void checkMaterial(Material m) {
        if (bill.get(m) > 0)
            bill.put(m, bill.get(m) - 1);
    }

    /*
    public void pay(HashMap<Material, Integer> inventoryMaterials) {
        assert hasEnoughMaterial(inventoryMaterials);
        inventoryMaterials.replaceAll((material, value) -> value - bill.get(material));
    }
     */

}
