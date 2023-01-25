import javax.swing.*;
import java.awt.*;

public class TitleScreen extends JFrame {
    GlobalVariables globalVariablesClass;
    TitleScreen () {
        globalVariablesClass = new GlobalVariables();
        generateGUI();
        generateTitle();
        generateButtons();
        this.add(new BlankLabel()); //stops the last added item from taking up the whole screen
    }
    private void generateGUI() {
        this.setTitle("Battleship");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1920, 1080);
        this.setVisible(true);

        ImageIcon image = new ImageIcon("icon.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(0x07CBED));
    }
    private void generateTitle() {
        JLabel title = new JLabel();
        title.setFont(new Font("Comic Sans", Font.PLAIN, 35));
        title.setText("Battleship");
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.TOP);
        title.setBackground(Color.BLACK);
        title.setBounds(725,150,200,50);
        this.add(title);
    }
    private void generateButtons() {
        TitleScreenButtons playMulti = new TitleScreenButtons("playMulti");
        TitleScreenButtons playSingle = new TitleScreenButtons("playSingle");
        TitleScreenButtons exitButton = new TitleScreenButtons("exit");
        this.add(playMulti);
        this.add(playSingle);
        this.add(exitButton);
    }
}
