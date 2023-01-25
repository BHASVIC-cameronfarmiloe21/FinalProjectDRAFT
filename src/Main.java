import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.*;

public class Main {
    private static TitleScreen titleScreen;
    private static Boolean buildMode;
    private static Boolean AttackPhase;
    private static int currentTurn;

    public static void main(String[] args) {
        titleScreen = new TitleScreen();
    }

    public static void setTitleVisible(Boolean bool) {
        titleScreen.setVisible(bool);
    }

    public static Boolean getBuildMode() {
        return buildMode;
    }

    public static void setBuildMode(Boolean buildMode) {
        Main.buildMode = buildMode;
    }

    public static Boolean getAttackPhase() {
        return AttackPhase;
    }

    public static void setAttackPhase(Boolean attackPhase) {
        AttackPhase = attackPhase;
    }

    public static int getCurrentTurn() {
        return currentTurn;
    }

    public static void setCurrentTurn(int currentTurn) {
        Main.currentTurn = currentTurn;
    }
}