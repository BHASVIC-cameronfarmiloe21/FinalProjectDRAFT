import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PlayerGridSquare extends JLabel {
    private Boolean attackPhase = false;
    private int[] coords = {0,0};
    private Boolean inhabited = false;
    private int playerOwnedBy;
    private boolean shot;
    PlayerGridSquare(int x, int y, String text, int player) {
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

    public int[] getCoords() {
        return coords;
    }

    public void setInhabited(Boolean inhabited){
        this.inhabited = inhabited;
        this.setBackground(Color.GRAY);
        this.setOpaque(true);
    }

    public void shootTile() {
        if (inhabited) {
            this.setText("X");
        }
        else {
            this.setText("O");
        }
    }

    public boolean getShot() {
        return shot;
    }
}
