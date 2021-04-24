package Controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class DrawFunctions {
    public static ImageView image(String imgPath, double... imgSize) {
        assert imgSize.length > 0;
        assert imgSize.length <= 2;
        Image image;
        if (imgSize.length == 1) image = new Image(imgPath, imgSize[0], imgSize[0], true, true);
        else image = new Image(imgPath, imgSize[0], imgSize[1], true, true);
        return new ImageView(image);
    }

    public static Text text(String text, int size) {
        Text newText = new Text(text);
        newText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, size));
        return newText;
    }
}
