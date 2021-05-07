package Views;

import Materials.Material;

public class MaterialView extends View {

    public MaterialView(Material material) {
        imagePath = "/asteroids/" + material.getClass().toString().replace("class Materials.", "").toLowerCase() + ".png";
    }

    public String getImagePath() {
        return imagePath;
    }
}
