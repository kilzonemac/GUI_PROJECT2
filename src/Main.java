import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main {
    public static void main(String [] args){
        EventQueue.invokeLater(()->{
            JFrame frame = new Menu();
        });
/*
       try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("results.bin"))) {


        } catch (IOException e) {
            e.printStackTrace();
        }
*/

/*
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("results.bin"))) {

            System.out.println(inputStream.readObject());
            System.out.println(inputStream.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
*/
    }
}
