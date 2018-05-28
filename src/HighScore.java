import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class HighScore extends JFrame {
    private JPanel panel = new JPanel();
    private JList<Result> lista;

    public HighScore(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("HighScore");
        setPreferredSize(new Dimension(300,400));
        setLocation(300,300);
        setVisible(true);
        setResizable(false);

        panel.setLayout(new BorderLayout());
        Vector<Result> labels=new Vector<>();
        ArrayList<Result> results=new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("results.bin"))) {
            try {
                results = (ArrayList<Result>) inputStream.readObject();
            }
            catch(EOFException e){

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        labels.addAll(results);
        lista = new JList<Result>(labels);

        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(lista);

        panel.add(scrollPane);

        getContentPane().add(panel);
        pack();
    }
}
