package Views;

import Materials.Material;

/**
 * Nyersanyagok view-ja
 *
 */
public class MaterialView extends View {

    /**
     * Konstruktor.
     *
     * @param material
     */
    public MaterialView(Material material) {
        imagePath = "/asteroids/" + material.getClass().toString().replace("class Materials.", "").toLowerCase() + ".png";
    }

    public String getImagePath() {
        return imagePath;
    }
}
