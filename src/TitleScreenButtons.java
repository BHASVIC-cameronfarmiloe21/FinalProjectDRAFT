import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TitleScreenButtons extends JButton{
    String buttonType;
    TitleScreenButtons(String buttonType) {
        this.buttonType = buttonType;
        this.setFont(new Font ("Comic Sans", Font.PLAIN,20));
        this.setFocusable(false);
        switch (buttonType) {
            case "playSingle" -> {
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                this.setBounds(700,350,200,50);
                this.setText("Play Singleplayer");
                this.setOpaque(true);
                this.setBorder(border);
            }
            case "playMulti" -> {
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                this.setBounds(700,425,200,50);
                this.setText("Play Multiplayer (DNF)");
                this.setOpaque(true);
                this.setBorder(border);
            }
            case "exit" -> {
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                this.setBounds(700,500,200,50);
                this.setText("Exit");
                this.setOpaque(true);
                this.setBorder(border);
            }
        }
        this.addActionListener(e -> onClick());
    }
    private void onClick() {
        switch (buttonType) {
            case "playSingle" -> {
                SinglePlayerGame singlePlayerGameBuildMode = new SinglePlayerGame();
                Main.setTitleVisible(false); //hides title screen
            }
            case "playMulti" -> {
                //do something else
            }
            case "exit" -> System.exit(0);
        }
    }
}
