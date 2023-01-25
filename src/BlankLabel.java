import javax.swing.*;
import java.awt.*;

public class BlankLabel extends JLabel {
    //placeholder class that stops objects from taking up whole screens
    BlankLabel() {
        this.setBounds(1920,1080,1,1);
        this.setPreferredSize(new Dimension(1,1));
    }
}
