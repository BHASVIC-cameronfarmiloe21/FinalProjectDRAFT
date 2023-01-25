import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EnemyGridSquare extends JButton {
    private Boolean attackPhase = false;
    private int[] coords = new int[2];
    private Boolean inhabited = false;
    private int playerOwnedBy;
    private boolean clicked = false;
    EnemyGridSquare(int x, int y, String text, int player) {
        Border border = BorderFactory.createLineBorder(Color.WHITE);
        this.coords[0] = (x-200)/50;
        this.coords[1] = (y-100)/50;
        this.setOpaque(false);
        this.setBorder(border);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.WHITE);
        this.setBounds(x,y,50,50);
        this.setPreferredSize(new Dimension(50,50));
        this.setText(text);
        this.setHorizontalTextPosition(CENTER);
        this.playerOwnedBy = player;
        this.setFocusable(false);
    }

    public void setInhabited(Boolean inhabited, String ship){
        this.inhabited = inhabited;
    }

    public void onClick(int currentPlayer) {
        if (currentPlayer == 0 /*player*/ && !clicked) {
            if (inhabited) {
                this.setText("X");
            }
            else {
                this.setText("O");
            }
            clicked = true;
        }
    }

    public Boolean getInhabited() {
        return inhabited;
    }

    public boolean getClicked() {
        return clicked;
    }
}
