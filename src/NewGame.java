import javax.swing.*;
import java.awt.*;

public class NewGame extends JFrame {
    private int size;

    public NewGame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400,500));
        setLocation(300,300);
        setVisible(true);
        setResizable(false);
        pack();
    }
}
