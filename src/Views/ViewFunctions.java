package Views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/**
 * Ez az osztály valósítja meg a képek és a text-ek létrehozását függvényeken kereszül
 */
public class ViewFunctions {

    /**
     * Egy kép létrehozásáért felel egy méret és egy kép elérési út alapján
     * @param imgPath
     * @param imgSize
     */
    public static ImageView image(String imgPath, double... imgSize) {
        assert imgSize.length > 0;
        assert imgSize.length <= 2;
        Image image;
        try {
            if (imgSize.length == 1) image = new Image(imgPath, imgSize[0], imgSize[0], true, true);
            else image = new Image(imgPath, imgSize[0], imgSize[1], true, true);
            return new ImageView(image);
        } catch(Exception e) {
            System.out.println(imgPath);
            e.printStackTrace();
            return new ImageView(new Image("asteroids/hollow.png"));
        }
    }

    /**
     * Egy text létrehozásáért felel egy megadott szöveg és méret alapján
     * @param text
     * @param size
     */
    public static Text text(String text, int size) {
        Text newText = new Text(text);
        newText.setFont(font(size));
        return newText;
    }

    public static Font font(int size) {
        return Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, size);
    }
}
