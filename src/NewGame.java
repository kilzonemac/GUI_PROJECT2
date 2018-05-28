import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class NewGame extends JFrame {
    private static  int size;
    private JPanel cards_panel;
    private static int points=0;
    private  ArrayList <Card> cards;
    private String compare1="";
    private String compare2="";
    private  ArrayList<ImageIcon> icons=new ArrayList<>();
    private JLabel time;
    private Timer timer_wynik;


    public NewGame(String size){
        int wymiar_w=Character.getNumericValue(size.charAt(0));
        int wymiar_col=Character.getNumericValue(size.charAt(2));
        this.size=wymiar_w*wymiar_col;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Gra");
        setPreferredSize(new Dimension(wymiar_col*100,wymiar_w*120));
        setLocation(300,300);
        setVisible(true);
        setResizable(false);

        pack();


        //DODATNIE AWERSOW
        try {
            for(int i=1;i<(this.size/2)+1;i++) {
                Image icon = ImageIO.read(getClass().getResource("memory/"+i+".jpg"));
                icons.add(new ImageIcon(icon));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //TIMER
        time = new JLabel("0:00", SwingConstants.CENTER);
        time.setFont(new Font("Serif", Font.BOLD, 24));
        int delay = 1000; //milliseconds

        timer_wynik = new Timer(delay, new ActionListener() {
            int sekundy=0;
            int ten=0;
            int minuty=0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if(sekundy==10){
                    ten++;
                    sekundy=0;
                }
                if(ten==6 && sekundy==0){
                    sekundy=0;
                    ten=0;
                    minuty++;
                }

                time.setText(minuty+":"+ten+sekundy);
                sekundy++;
            }
        });
        timer_wynik.start();



        cards_panel=new JPanel(new GridLayout(wymiar_w,wymiar_col));
        //REAGOWANIE NA PRZYCISK
        InputMap imap = cards_panel.getInputMap(JComponent.WHEN_FOCUSED);
        imap.put(KeyStroke.getKeyStroke("ctrl shift Q"), "back");
        ActionMap amap = cards_panel.getActionMap();
        amap.put("back", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                timer_wynik.stop();
                points=0;
            }
        });

        shuffle(cards_panel);
        add(cards_panel, BorderLayout.CENTER);
        add(time,BorderLayout.PAGE_END);



    }


    public void shuffle(JPanel panel){
        cards=new ArrayList<>();
        Collections.shuffle(icons);
        int index_image=0;
        for(int i=0;i<this.size;i++) {
            if(index_image==(this.size/2))
                index_image=0;
                cards.add(new Card(index_image));
                Card temp= cards.get(i);

                temp.setAverse(icons.get(index_image));
                temp.addActionListener(e -> {
                    temp.setDisabledIcon(temp.getAverse());
                    if(getCompare1().equals(""))
                        setCompare1(temp.getActionCommand());
                    else
                        setCompare2(temp.getActionCommand());
                    temp.setEnabled(false);

                //WARUNKI GRY
                    try {
                        game();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                });

            if(i==(this.size/2)-1) {
                Collections.shuffle(cards);
            }
            index_image++;
        }
        for(int i=0;i<this.size;i++)
            panel.add(cards.get(i));
    }

    public void game() throws IOException, ClassNotFoundException {

        int delay = 2000; //milliseconds
        Timer timer = new Timer(delay, e ->  {
                for(Card temp:cards){
                    temp.setEnabled(true);
                    temp.setDisabledIcon(Card.getReverse());
                }
            compare1="";
            compare2="";
        });


            if(compare1.equals(compare2)&&!compare1.equals("")){
                points++;
                for(int i=0;i<cards.size();i++){
                    if(cards.get(i).getActionCommand().equals(compare1)) {
                        cards.remove(i);
                    }
                }
                compare1="";
                compare2="";
            }
            else if(!compare1.equals("") && !compare2.equals("")){
                for(Card temp:cards){
                    temp.setEnabled(false);
                }
                timer.start();
                timer.setRepeats(false);
            }
            //SPRAWDZANIE PUNKTACJI
        if(points == size/2) {
                timer_wynik.stop();
                String czas=time.getText();
                String nazwa="Anonim";
                nazwa=JOptionPane.showInputDialog("Podaj swoje imię:","Gracz1");
                double sekundy=(((Character.getNumericValue(czas.charAt(0)))*60)+(Integer.parseInt(String.valueOf(czas.charAt(2))+String.valueOf(czas.charAt(3)))));
                double wynik =  (double)(size) / sekundy;
                wynik*=10;

                wynik=Math.round(wynik);
                this.dispose();
                points=0;


            ArrayList<Result> results = new ArrayList<>();
            if(nazwa==null)
                nazwa="Anonim";
            Result temp=new Result(nazwa,wynik);

            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("results.bin"))) {
                        try {
                            results.addAll((Collection<? extends Result>) inputStream.readObject());
                        }
                        catch(EOFException e){

                        }
            }

            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("results.bin"))) {
                boolean repeat=true;
                for(Result a:results){
                    if(a.getNazwa().equals(temp.getNazwa())){
                        if(temp.getWynik()>a.getWynik()) {
                            results.remove(a);
                            results.add(temp);
                            repeat=false;
                        }
                    }
                }
                if(repeat)
                    results.add(temp);

                Collections.sort(results);
                outputStream.writeObject(results);
            }

        }
    }

    public String getCompare1() {
        return compare1;
    }

    public void setCompare1(String compare) {
        compare1 = compare;
    }

    public void setCompare2(String compare) {
        compare2 = compare;
    }

    public ArrayList<ImageIcon> getIcons() {
        return icons;
    }
}
