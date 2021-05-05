package Views;

import Materials.Material;

public class MaterialView {
    private Material material;
    protected String imagePath;

    public MaterialView(Material material) {
        this.material = material;
        imagePath = "/asteroids/" + this.material.getClass().toString().replace("class Materials.", "").toLowerCase() + ".png";
    }

    public String getImagePath() {
        return imagePath;
    }
}
