import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Card extends JButton {

private ImageIcon averse;
private static ImageIcon reverse;

    public Card(int index){
        try {
            Image img = ImageIO.read(getClass().getResource("memory/reverse.jpg"));
            reverse=new ImageIcon(img);
            setIcon(reverse);
            setDisabledIcon(reverse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setActionCommand(String.valueOf(index));

    }


    public static ImageIcon getReverse() {
        return reverse;
    }

    public ImageIcon getAverse() {
        return averse;
    }

    public void setAverse(ImageIcon averse) {
        this.averse = averse;
    }
}
