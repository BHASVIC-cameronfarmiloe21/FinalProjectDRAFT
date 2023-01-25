import javax.swing.*;
import java.awt.*;


public class Ships extends JLabel{
    private String shipType;
    private int length;
    private String rotation = "horizontal";
    private int[] coords = {0,0};
    Ships(String shipType, int x, int y) {
        this.shipType = shipType;
        this.setOpaque(true);
        this.setBackground(Color.GRAY);
        this.setBounds(x,y,50,50);
        this.setPreferredSize(new Dimension(50,50));
        this.coords[0] = x;
        this.coords[1] = y;
        switch (shipType) {
            case "aircraftCarrier" -> this.length = 5;
            case "battleship" -> this.length = 4;
            case "submarine", "cruiser" -> this.length = 3;
            case "destroyer" -> this.length = 2;
        }
    }
    public int getLength() {
        return length;
    }
    public void rotate(int i) {
        if (rotation.equals("horizontal")) {
            rotation = "vertical";
            coords[0] -= 50 * i;
            coords[1] += 50 * i;
        }
        else {
            rotation = "horizontal";
            coords[0] += 50 * i;
            coords[1] -= 50 * i;
        }
        this.setBounds(coords[0],coords[1],50,50);
    }
    public String getRotation() {
        return rotation;
    }

    public void setCoords(int x,int y) {
        this.coords[0] = x;
        this.coords[1] = y;
    }

    public int getCoords(int i) {
        return coords[i];
    }

    public void move(String UDLR) {
        switch (UDLR) {
            case "up" -> this.coords[1]-=50;
            case "down" -> this.coords[1]+=50;
            case "left" -> this.coords[0]-=50;
            case "right" -> this.coords[0]+=50;
        }
        this.setBounds(coords[0],coords[1],50,50);
    }

    public boolean checkValidForMove(String UDLR) {
        // Checks if the new coords fit inside the 500x500 grid (Top left coords: (250,150). Bottom left coords: (250,650). Top right coords: (750,150). Bottom right coords: (750,650).
        switch (UDLR) {
            case "up" -> {
                return coords[1]-50 >= 150;
            }
            case "down" -> {
                return coords[1]+50 <= 600;
            }
            case "left" -> {
                return coords[0]-50 >= 250;
            }
            case "right" -> {
                return coords[0]+50 <= 700;
            }
        }
        return false;
    }

    public boolean checkValidForRotation() {
        if (rotation.equals("horizontal")) {
            switch (shipType) {
                case "aircraftCarrier" -> {
                    return (coords[1]+250 <= 650);
                }
                case "battleship" -> {
                    return (coords[1]+200 <= 650);
                }
                case "submarine", "cruiser" -> {
                    return (coords[1]+150 <= 650);
                }
                case "destroyer" -> {
                    return (coords[1]+100 <= 650);
                }
            }
        }
        else if (rotation.equals("vertical")) {
            switch (shipType) {
                case "aircraftCarrier" -> {
                    return (coords[0]+250 <= 750);
                }
                case "battleship" -> {
                    return (coords[0]+200 <= 750);
                }
                case "submarine", "cruiser" -> {
                    return (coords[0]+150 <= 750);
                }
                case "destroyer" -> {
                    return (coords[0]+100 <= 750);
                }
            }
        }
        return false;
    }
}
