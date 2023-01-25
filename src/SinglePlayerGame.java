import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import java.util.Objects;

public class SinglePlayerGame extends JFrame {
    private int currentShip;
    private Ships[] aircraftCarrier = new Ships[5];
    private Ships[] battleship = new Ships[4];
    private Ships[] submarine = new Ships[3];
    private Ships[] cruiser = new Ships[3];
    private Ships[] destroyer = new Ships[2];
    private PlayerGridSquare[][] gridP1;
    private EnemyGridSquare[][] gridP2;
    private JLayeredPane jLayeredPane = new JLayeredPane();
    private int[][] aircraftCarrierFinalCoords;
    private int[][] battleshipFinalCoords;
    private int[][] submarineFinalCoords;
    private int[][] cruiserFinalCoords;
    private int[][] destroyerFinalCoords;
    private int[][] enemyAircraftCarrierCoords = new int[5][2];
    private int[][] enemyBattleshipCoords = new int[4][2];
    private int[][] enemyCruiserCoords = new int[3][2];
    private int[][] enemySubmarineCoords = new int[3][2];
    private int[][] enemyDestroyerCoords = new int[2][2];
    private JLabel instructions;
    private int currentPlayer;
    private boolean attackPhase;
    private boolean player1Won;
    private boolean player2Won;
    private int turnNum;

    //generate build GUI
    SinglePlayerGame() {
        this.getContentPane().setBackground(new Color(0x03044D));
        this.setTitle("Battleship - Build Phase");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1920, 1080);
        this.setVisible(true);

        makeGrid("buildPhase");
        outputShips();
        outputInstructions();
        makeShipsVisible();
        jLayeredPane.add(new BlankLabel(),-1); //stops the last added item from taking up the whole screen
        this.add(jLayeredPane);

        addKeybinds();
    }

    private void makeGrid(String phase) {
        switch (phase) {
            case "buildPhase" -> {
                gridP1 = new PlayerGridSquare[11][11];
                Border border = BorderFactory.createLineBorder(Color.WHITE);
                for (int x = 0; x < 550; x += 50) {
                    for (int y = 0; y < 550; y += 50) {
                        String text = " ";
                        if (x == 0 && y / 50 != 0) {
                            text = String.valueOf(y / 50);
                        }
                        if (y == 0) {
                            switch (x / 50) {
                                case 1 -> text = "A";
                                case 2 -> text = "B";
                                case 3 -> text = "C";
                                case 4 -> text = "D";
                                case 5 -> text = "E";
                                case 6 -> text = "F";
                                case 7 -> text = "G";
                                case 8 -> text = "H";
                                case 9 -> text = "I";
                                case 10 -> text = "J";
                            }
                        }
                        if (!(x == 0 && y == 0)) {
                            gridP1[x / 50][y / 50] = new PlayerGridSquare(x + 200, y + 100, text, 0);
                            gridP1[x / 50][y / 50].setBorder(border);
                            jLayeredPane.add(gridP1[x / 50][y / 50], 0);
                        }
                    }
                }
            }
            case "attackPhase" -> {
                gridP2 = new EnemyGridSquare[11][11];
                Border border = BorderFactory.createLineBorder(Color.WHITE);
                for (int x = 0; x < 550; x += 50) {
                    for (int y = 0; y < 550; y += 50) {
                        String text = " ";
                        if (x == 0 && y / 50 != 0) {
                            text = String.valueOf(y / 50);
                        }
                        if (y == 0) {
                            switch (x / 50) {
                                case 1 -> text = "A";
                                case 2 -> text = "B";
                                case 3 -> text = "C";
                                case 4 -> text = "D";
                                case 5 -> text = "E";
                                case 6 -> text = "F";
                                case 7 -> text = "G";
                                case 8 -> text = "H";
                                case 9 -> text = "I";
                                case 10 -> text = "J";
                            }
                        }
                        if (!(x == 0 && y == 0)) {
                            gridP2[x / 50][y / 50] = new EnemyGridSquare(x + 800, y + 100, text, 1);
                            gridP2[x / 50][y / 50].setBorder(border);
                            jLayeredPane.add(gridP2[x / 50][y / 50], 4);
                        }
                    }
                }
            }
        }
    }

    public PlayerGridSquare[][] getGridP1() {
        return gridP1;
    }

    private void outputShips() {
        for (int i = 0; i < 5; i++) {
            aircraftCarrier[i] = new Ships("aircraftCarrier", 250 + (50 * i), 150);
            jLayeredPane.add(aircraftCarrier[i],1);
        }
        for (int i = 0; i < 4; i++) {
            battleship[i] = new Ships("battleship", 250 + (50 * i), 250);
            jLayeredPane.add(battleship[i],2);
        }
        for (int i = 0; i < 3; i++) {
            submarine[i] = new Ships("submarine", 250 + (50 * i), 350);
            jLayeredPane.add(submarine[i],3);
        }
        for (int i = 0; i < 3; i++) {
            cruiser[i] = new Ships("cruiser", 250 + (50 * i), 450);
            jLayeredPane.add(cruiser[i],4);
        }
        for (int i = 0; i < 2; i++) {
            destroyer[i] = new Ships("destroyer", 250 + (50 * i), 550);
            jLayeredPane.add(destroyer[i],5);
        }
    }

    private void outputInstructions () {
        instructions = new JLabel();
        Border border = BorderFactory.createLineBorder(Color.WHITE);
        instructions.setFont(new Font ("Comic Sans", Font.PLAIN,15));
        instructions.setForeground(Color.WHITE);
        instructions.setBorder(border);
        instructions.setBounds(1000,100,200,550);
        instructions.setText("<html>Keybinds:<br>ZXCVB to switch between ships<br>WASD to move them around<br>R to rotate<br>F to finish deploy phase (please ensure no ships overlap)</html>");
        instructions.setVisible(true);
        jLayeredPane.add(instructions,0);
    }

    private void makeShipsVisible() {
        for (int i = 0; i < 5; i++) {
            aircraftCarrier[i].setVisible(true);
        }
        for (int i = 0; i < 4; i++) {
            battleship[i].setVisible(true);
        }
        for (int i = 0; i < 3; i++) {
            submarine[i].setVisible(true);
        }
        for (int i = 0; i < 3; i++) {
            cruiser[i].setVisible(true);
        }
        for (int i = 0; i < 2; i++) {
            destroyer[i].setVisible(true);
        }
    }

    private void move(String ship, String UDLR/*up down left right*/) {
        boolean valid = false;
        String rotation;
        switch (ship) {
            case "aircraftCarrier" -> {
                rotation = aircraftCarrier[0].getRotation();

                if (rotation.equals("horizontal")) {
                    switch (UDLR) {
                        case "up", "down", "left" -> valid = aircraftCarrier[0].checkValidForMove(UDLR);
                        case "right" -> valid = aircraftCarrier[4].checkValidForMove(UDLR);
                    }
                }

                if (rotation.equals("vertical")) {
                    switch (UDLR) {
                        case "up" -> valid = aircraftCarrier[0].checkValidForMove(UDLR);
                        case "down", "left", "right" -> valid = aircraftCarrier[4].checkValidForMove(UDLR);
                    }
                }

                if (valid) {
                    for (int i = 0; i < 5; i++) {
                        aircraftCarrier[i].move(UDLR);
                    }
                }
            }
            case "battleship" -> {
                rotation = battleship[0].getRotation();
                switch (rotation) {
                    case "horizontal" -> {
                        switch (UDLR) {
                            case "up", "down", "left" -> valid = battleship[0].checkValidForMove(UDLR);
                            case "right" -> valid = battleship[3].checkValidForMove(UDLR);
                        }
                    }
                    case "vertical" -> {
                        switch (UDLR) {
                            case "up" -> valid = battleship[0].checkValidForMove(UDLR);
                            case "down", "left", "right" -> valid = battleship[3].checkValidForMove(UDLR);
                        }
                    }
                }
                if (valid) {
                    for (int i = 0; i < 4; i++) {
                        battleship[i].move(UDLR);
                    }
                }
            }
            case "submarine" -> {
                rotation = submarine[0].getRotation();
                switch (rotation) {
                    case "horizontal" -> {
                        switch (UDLR) {
                            case "up", "down", "left" -> valid = submarine[0].checkValidForMove(UDLR);
                            case "right" -> valid = submarine[2].checkValidForMove(UDLR);
                        }
                    }
                    case "vertical" -> {
                        switch (UDLR) {
                            case "up" -> valid = submarine[0].checkValidForMove(UDLR);
                            case "down", "left", "right" -> valid = submarine[2].checkValidForMove(UDLR);
                        }
                    }
                }
                if (valid) {
                    for (int i = 0; i < 3; i++) {
                        submarine[i].move(UDLR);
                    }
                }
            }
            case "cruiser" -> {
                rotation = cruiser[0].getRotation();
                switch (rotation) {
                    case "horizontal" -> {
                        switch (UDLR) {
                            case "up", "down", "left" -> valid = cruiser[0].checkValidForMove(UDLR);
                            case "right" -> valid = cruiser[2].checkValidForMove(UDLR);
                        }
                    }
                    case "vertical" -> {
                        switch (UDLR) {
                            case "up" -> valid = cruiser[0].checkValidForMove(UDLR);
                            case "down", "left", "right" -> valid = cruiser[2].checkValidForMove(UDLR);
                        }
                    }
                }
                if (valid) {
                    for (int i = 0; i < 3; i++) {
                        cruiser[i].move(UDLR);
                    }
                }
            }
            case "destroyer" -> {
                rotation = destroyer[0].getRotation();
                switch (rotation) {
                    case "horizontal" -> {
                        switch (UDLR) {
                            case "up", "down", "left" -> valid = destroyer[0].checkValidForMove(UDLR);
                            case "right" -> valid = destroyer[1].checkValidForMove(UDLR);
                        }
                    }
                    case "vertical" -> {
                        switch (UDLR) {
                            case "up" -> valid = destroyer[0].checkValidForMove(UDLR);
                            case "down", "left", "right" -> valid = destroyer[1].checkValidForMove(UDLR);
                        }
                    }
                }
                if (valid) {
                    for (int i = 0; i < 2; i++) {
                        destroyer[i].move(UDLR);
                    }
                }
            }
        }
    }

    private void rotate(String ship) {
        String rotation;
        switch (ship) {
            case "aircraftCarrier" -> {
                if(aircraftCarrier[4].checkValidForRotation()) {
                    for (int i = 0; i < 5; i++) {
                        aircraftCarrier[i].rotate(i);
                    }
                }
                else {
                    System.out.println("invalid point for rotation");
                }
            }
            case "battleship" -> {
                if (battleship[3].checkValidForRotation()) {
                    for (int i = 0; i < 4; i++) {
                        battleship[i].rotate(i);
                    }
                }
                else {
                    System.out.println("invalid point for rotation");
                }
            }
            case "submarine" -> {
                if (submarine[2].checkValidForRotation()) {
                    for (int i = 0; i < 3; i++) {
                        submarine[i].rotate(i);
                    }
                }
                else {
                    System.out.println("invalid point for rotation");
                }
            }
            case "cruiser" -> {
                if (cruiser[2].checkValidForRotation()) {
                    for (int i = 0; i < 3; i++) {
                        cruiser[i].rotate(i);
                    }
                }
                else {
                    System.out.println("invalid point for rotation");
                }
            }
            case "destroyer" -> {
                if (destroyer[1].checkValidForRotation()) {
                    for (int i = 0; i < 2; i++) {
                        destroyer[i].rotate(i);
                    }
                }
                else {
                    System.out.println("invalid point for rotation");
                }
            }
        }
    }

    private void addKeybinds() {
        String z = "z";
        String x = "x";
        String c = "c";
        String v = "v";
        String b = "b";
        String w = "w";
        String a = "a";
        String s = "s";
        String d = "d";
        String r = "r";
        String f = "f";

        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = this.jLayeredPane.getInputMap(condition);
        ActionMap actionMap = this.jLayeredPane.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), z);
        actionMap.put(z, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShip = 1;
                System.out.println("z");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), x);
        actionMap.put(x, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShip = 2;
                System.out.println("x");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), c);
        actionMap.put(c, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShip = 3;
                System.out.println("c");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0), v);
        actionMap.put(v, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShip = 4;
                System.out.println("v");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), b);
        actionMap.put(b, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentShip = 5;
                System.out.println("b");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), w);
        actionMap.put(w, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentShip) {
                    case 1 -> move("aircraftCarrier", "up");
                    case 2 -> move("battleship", "up");
                    case 3 -> move("submarine", "up");
                    case 4 -> move("cruiser", "up");
                    case 5 -> move("destroyer", "up");
                }
                System.out.println("w");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), a);
        actionMap.put(a, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentShip) {
                    case 1 -> move("aircraftCarrier", "left");
                    case 2 -> move("battleship", "left");
                    case 3 -> move("submarine", "left");
                    case 4 -> move("cruiser", "left");
                    case 5 -> move("destroyer", "left");
                }
                System.out.println("a");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), s);
        actionMap.put(s, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentShip) {
                    case 1 -> move("aircraftCarrier", "down");
                    case 2 -> move("battleship", "down");
                    case 3 -> move("submarine", "down");
                    case 4 -> move("cruiser", "down");
                    case 5 -> move("destroyer", "down");
                }
                System.out.println("s");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), d);
        actionMap.put(d, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentShip) {
                    case 1 -> move("aircraftCarrier", "right");
                    case 2 -> move("battleship", "right");
                    case 3 -> move("submarine", "right");
                    case 4 -> move("cruiser", "right");
                    case 5 -> move("destroyer", "right");
                }
                System.out.println("d");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), r);
        actionMap.put(r, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentShip) {
                    case 1 -> rotate("aircraftCarrier");
                    case 2 -> rotate("battleship");
                    case 3 -> rotate("submarine");
                    case 4 -> rotate("cruiser");
                    case 5 -> rotate("destroyer");
                }
                System.out.println("r");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), f);
        actionMap.put(f, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("f");
                finishBuildPhase();
            }
        });
    }

    private void removeKeybinds () {
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = this.jLayeredPane.getInputMap(condition);
        ActionMap actionMap = this.jLayeredPane.getActionMap();

        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_W,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_A,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_S,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_D,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_Z,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_X,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_C,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_V,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_B,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_R,0));
        inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_W,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_A,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_S,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_D,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_Z,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_X,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_C,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_V,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_B,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_R,0));
        actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F,0));
    }

    private void finishBuildPhase() {
        if(checkNoShipsOverlap()) {
            System.out.println("true");
            removeKeybinds();
            instructions.setVisible(false);
            mergeShipsWithGrid();
            this.setTitle("Battleship - Attack Phase");
            makeGrid("attackPhase");
            generateEnemyShips();
            attackPhase();
        }
        else {
            System.out.println("false");
        }
    }

    private boolean checkNoShipsOverlap() {
        aircraftCarrierFinalCoords = getFinalCoords("aircraftCarrier");
        battleshipFinalCoords = getFinalCoords("battleship");
        submarineFinalCoords = getFinalCoords("submarine");
        cruiserFinalCoords = getFinalCoords("cruiser");
        destroyerFinalCoords = getFinalCoords("destroyer");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (aircraftCarrierFinalCoords[i][0] == battleshipFinalCoords[j][0] && aircraftCarrierFinalCoords[i][1] == battleshipFinalCoords[j][1]) {
                    return false;
                }
            }
            for (int j = 0; j < 3; j++) {
                if (aircraftCarrierFinalCoords[i][0] == submarineFinalCoords[j][0] && aircraftCarrierFinalCoords[i][1] == submarineFinalCoords[j][1]) {
                    return false;
                }
                if (aircraftCarrierFinalCoords[i][0] == cruiserFinalCoords[j][0] && aircraftCarrierFinalCoords[i][1] == cruiserFinalCoords[j][1]) {
                    return false;
                }
            }
            for (int j = 0; j < 2; j++) {
                if(aircraftCarrierFinalCoords[i][0] == destroyerFinalCoords[j][0] && aircraftCarrierFinalCoords[i][1] == destroyerFinalCoords[j][1]) {
                    return false;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (battleshipFinalCoords[i][0] == submarineFinalCoords[j][0] && battleshipFinalCoords[i][1] == submarineFinalCoords[j][1]) {
                    return false;
                }
                if (battleshipFinalCoords[i][0] == cruiserFinalCoords[j][0] && battleshipFinalCoords[i][1] == cruiserFinalCoords[j][1]) {
                    return false;
                }
            }
            for (int j = 0; j < 2; j++) {
                if (battleshipFinalCoords[i][0] == destroyerFinalCoords[j][0] && battleshipFinalCoords[i][1] == destroyerFinalCoords[j][1]) {
                    return false;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (submarineFinalCoords[i][0] == cruiserFinalCoords[j][0] && submarineFinalCoords[i][1] == cruiserFinalCoords[j][1]) {
                    return false;
                }
            }
            for (int j = 0; j < 2; j++) {
                if (submarineFinalCoords[i][0] == destroyerFinalCoords[j][0] && submarineFinalCoords[i][1] == destroyerFinalCoords[j][1]) {
                    return false;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (cruiserFinalCoords[i][0] == destroyerFinalCoords[j][0] && cruiserFinalCoords[i][1] == destroyerFinalCoords[j][1]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] getFinalCoords(String ship) {
        switch (ship){
            case "aircraftCarrier" -> {
                int[][] finalCoords = new int[5][2];
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        finalCoords[i][j] = aircraftCarrier[i].getCoords(j);
                    }
                }
                return finalCoords;
            }
            case "battleship" -> {
                int[][] finalCoords = new int[4][2];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 2; j++) {
                        finalCoords[i][j] = battleship[i].getCoords(j);
                    }
                }
                return finalCoords;
            }
            case "submarine" -> {
                int[][] finalCoords = new int[3][2];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 2; j++) {
                        finalCoords[i][j] = submarine[i].getCoords(j);
                    }
                }
                return finalCoords;
            }
            case "cruiser" -> {
                int[][] finalCoords = new int[3][2];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 2; j++) {
                        finalCoords[i][j] = cruiser[i].getCoords(j);
                    }
                }
                return finalCoords;
            }
            case "destroyer" -> {
                int[][] finalCoords = new int[2][2];
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        finalCoords[i][j] = destroyer[i].getCoords(j);
                    }
                }
                return finalCoords;
            }
        }
        return new int[0][];
    }

    private void mergeShipsWithGrid() {
        for (int i = 0; i < 5; i++) {
            gridP1[aircraftCarrier[i].getCoords(0) / 50 - 4][aircraftCarrier[i].getCoords(1) / 50 - 2].setInhabited(true);
            aircraftCarrier[i].setVisible(false);
        }
        for (int i = 0; i < 4; i++) {
            gridP1[battleship[i].getCoords(0) / 50 - 4][battleship[i].getCoords(1) / 50 - 2].setInhabited(true);
            battleship[i].setVisible(false);
        }
        for (int i = 0; i < 3; i++) {
            gridP1[submarine[i].getCoords(0) / 50 - 4][submarine[i].getCoords(1) / 50 - 2].setInhabited(true);
            submarine[i].setVisible(false);
            gridP1[cruiser[i].getCoords(0) / 50 - 4][cruiser[i].getCoords(1) / 50 - 2].setInhabited(true);
            cruiser[i].setVisible(false);
        }
        for (int i = 0; i < 2; i++) {
            gridP1[destroyer[i].getCoords(0) / 50 - 4][destroyer[i].getCoords(1) / 50 - 2].setInhabited(true);
            destroyer[i].setVisible(false);
        }
    }

    private void attackPhase() {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                int finalJ = j;
                int finalI = i;
                turnNum = 0;
                gridP2[i][j].addActionListener(e -> {
                    if (!gridP2[finalI][finalJ].getClicked()) { // if tile has been clicked do nothing
                        gridP2[finalI][finalJ].onClick(currentPlayer);
                        checkForWin();
                        this.currentPlayer = 1;
                        if (!player1Won && !player2Won) {
                            this.computerTurn();
                            checkForWin();
                        }
                    }
                });
            }
        }
    }

    private void generateEnemyShips() {
        SecureRandom random = new SecureRandom();
        int x;
        int y;
        int horizontal;
        boolean[] valid;
        boolean battleshipNotPlaced;
        boolean cruiserNotPlaced;
        boolean submarineNotPlaced;
        boolean destroyerNotPlaced;

        // generate aircraft carrier
        horizontal = random.nextInt(2);
        if (horizontal == 1) {
            x = random.nextInt(5) + 1;
            y = random.nextInt(9) + 1;
            for (int i = 0; i < 5; i++) {
                gridP2[x + 1 + i][y + 1].setInhabited(true, "1");
                enemyAircraftCarrierCoords[i][0] = x * 50 + 800 + i*50;
                enemyAircraftCarrierCoords[i][1] = y * 50 + 100;
            }
        } else {
            x = random.nextInt(9) + 1;
            y = random.nextInt(5) + 1;
            for (int i = 0; i < 5; i++) {
                gridP2[x + 1][y + 1 + i].setInhabited(true, "1");
                enemyAircraftCarrierCoords[i][0] = x * 50 + 800;
                enemyAircraftCarrierCoords[i][1] = y * 50 + 100 + i*50;
            }
        }

        // generate battleship
        do {
            System.out.println("Battleship");
            valid = new boolean[4];
            horizontal = random.nextInt(2);
            if (horizontal == 1) {
                x = random.nextInt(6) + 1;
                y = random.nextInt(9) + 1;
                for (int i = 0; i < 4; i++) {
                    enemyBattleshipCoords[i][0] = x * 50 + 800 + i*50;
                    enemyBattleshipCoords[i][1] = y * 50 + 100;
                }
            } else {
                x = random.nextInt(9) + 1;
                y = random.nextInt(6) + 1;
                for (int i = 0; i < 4; i++) {
                    enemyBattleshipCoords[i][0] = x * 50 + 800;
                    enemyBattleshipCoords[i][1] = y * 50 + 100 + i*50;
                }
            }

            // validate overlap
            for (int i = 0; i < 4; i++) { // for each battleship piece
                valid[i] = !gridP2[(enemyBattleshipCoords[i][0]-800)/50][(enemyBattleshipCoords[i][1]-100)/50].getInhabited();
                if (!valid[i]) {
                    System.out.println("2 - overlap");
                }
            }

            // if overlap doesn't occur place the ship
            if (valid[0] && valid[1] && valid[2] && valid[3]) {
                for (int i = 0; i < 4; i++) {
                    if (horizontal == 1) {
                        gridP2[x + i][y].setInhabited(true, "2");
                    } else {
                        gridP2[x][y + i].setInhabited(true, "2");
                    }
                }
                battleshipNotPlaced = false;
                System.out.println("false");
            }
            else {
                battleshipNotPlaced = true;
                System.out.println("true");
            }
        } while (battleshipNotPlaced);

        // generate cruiser
        do {
            System.out.println("Cruiser");
            valid = new boolean[3];
            horizontal = random.nextInt(2);
            if (horizontal == 1) {
                x = random.nextInt(7) + 1;
                y = random.nextInt(9) + 1;
                for (int i = 0; i < 3; i++) {
                    enemyCruiserCoords[i][0] = x * 50 + 800 + i*50;
                    enemyCruiserCoords[i][1] = y * 50 + 100;
                }
            } else {
                x = random.nextInt(9) + 1;
                y = random.nextInt(7) + 1;
                for (int i = 0; i < 3; i++) {
                    enemyCruiserCoords[i][0] = x * 50 + 800;
                    enemyCruiserCoords[i][1] = y * 50 + 100 + i*50;
                }
            }

            // validate overlap
            for (int i = 0; i < 3; i++) { // for each cruiser piece
                valid[i] = !gridP2[(enemyCruiserCoords[i][0]-800)/50][(enemyCruiserCoords[i][1]-100)/50].getInhabited();
                if (!valid[i]) {
                    System.out.println("3 - overlap");
                }
            }

            // if overlap doesn't occur place the ship
            if (valid[0] && valid[1] && valid[2]) {
                for (int i = 0; i < 3; i++) {
                    if (horizontal == 1) {
                        gridP2[x + i][y].setInhabited(true, "3");
                    }
                    else {
                        gridP2[x][y + i].setInhabited(true, "3");
                    }
                }
                cruiserNotPlaced = false;
                System.out.println("false");
            }
            else {
                cruiserNotPlaced = true;
                System.out.println("true");
            }
        } while (cruiserNotPlaced);

        // generate submarine
        do {
            System.out.println("Submarine");
            valid = new boolean[3];
            horizontal = random.nextInt(2);
            if (horizontal == 1) {
                x = random.nextInt(7) + 1;
                y = random.nextInt(9) + 1;
                for (int i = 0; i < 3; i++) {
                    enemySubmarineCoords[i][0] = x * 50 + 800 + i*50;
                    enemySubmarineCoords[i][1] = y * 50 + 100;
                }
            } else {
                x = random.nextInt(9) + 1;
                y = random.nextInt(7) + 1;
                for (int i = 0; i < 3; i++) {
                    enemySubmarineCoords[i][0] = x * 50 + 800;
                    enemySubmarineCoords[i][1] = y * 50 + 100 + i*50;
                }
            }

            // validate overlap
            for (int i = 0; i < 3; i++) { // for each submarine piece
                valid[i] = !gridP2[(enemySubmarineCoords[i][0]-800)/50][(enemySubmarineCoords[i][1]-100)/50].getInhabited();
                if (!valid[i]) {
                    System.out.println("4 - overlap");
                }
            }
            // if overlap doesn't occur place the ship
            if (valid[0] && valid[1] && valid[2]) {
                for (int i = 0; i < 3; i++) {
                    if (horizontal == 1) {
                        gridP2[x + i][y].setInhabited(true, "4");
                    }
                    else {
                        gridP2[x][y + i].setInhabited(true, "4");
                    }
                }
                submarineNotPlaced = false;
                System.out.println("false");
            }
            else {
                submarineNotPlaced = true;
                System.out.println("true");
            }
        } while (submarineNotPlaced);

        // generate destroyer
        do {
            System.out.println("Destroyer");
            valid = new boolean[2];
            horizontal = random.nextInt(2);
            if (horizontal == 1) {
                x = random.nextInt(7) + 1;
                y = random.nextInt(9) + 1;
                for (int i = 0; i < 2; i++) {
                    enemyDestroyerCoords[i][0] = x * 50 + 800 + i*50;
                    enemyDestroyerCoords[i][1] = y * 50 + 100;
                }
            }
            else {
                x = random.nextInt(9) + 1;
                y = random.nextInt(7) + 1;
                for (int i = 0; i < 2; i++) {
                    enemyDestroyerCoords[i][0] = x * 50 + 800;
                    enemyDestroyerCoords[i][1] = y * 50 + 100 + i*50;
                }
            }

            // validate overlap
            for (int i = 0; i < 2; i++) { // for each destroyer piece
                valid[i] = !gridP2[(enemyDestroyerCoords[i][0]-800)/50][(enemyDestroyerCoords[i][1]-100)/50].getInhabited();
                if (!valid[i]) {
                    System.out.println("5 - overlap");
                }
            }
            // if overlap doesn't occur place the ship
            if (valid[0] && valid[1]) {
                for (int i = 0; i < 2; i++) {
                    if (horizontal == 1) {
                        gridP2[x + i][y].setInhabited(true, "5");
                    }
                    else {
                        gridP2[x][y + i].setInhabited(true, "5");
                    }
                }
                destroyerNotPlaced = false;
                System.out.println("false");
            }
            else {
                destroyerNotPlaced = true;
                System.out.println("true");
            }
        } while (destroyerNotPlaced);
    }

    private void computerTurn() {
        SecureRandom random = new SecureRandom();
        int x;
        int y;
        boolean turnDone;
        System.out.println("turnNum: " + turnNum);
        do {
            x = random.nextInt(10) + 1;
            y = random.nextInt(10) + 1;
            System.out.println("Coords: " + y + "," + x);
            System.out.println("getShot: " + gridP1[x][y].getShot());
            if (gridP1[x][y].getShot()) { // if tile has been shot already
                turnDone = false;
            }
            else {
                gridP1[x][y].shootTile();
                System.out.println("tile shot");
                turnDone = true;
            }
        } while (!turnDone);
        currentPlayer = 0;
        turnNum++;
    }

    private void checkForWin() {
        int P1Hits=0;
        int P2Hits=0;
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (Objects.equals(gridP1[i][j].getText(), "X")) {
                    P1Hits++;
                }
                if (Objects.equals(gridP2[i][j].getText(), "X")) {
                    P2Hits++;
                }
            }
        }
        if (P1Hits == 17) {
            JLabel winningLabel = new JLabel("Player 2 Wins");
            winningLabel.setFont(new Font("Comic Sans", Font.PLAIN, 35));
            winningLabel.setBounds(675,25,400,50);
            winningLabel.setVisible(true);
            winningLabel.setForeground(Color.WHITE);
            jLayeredPane.add(winningLabel,10);
            System.out.println("Player 2 Wins");
            player2Won = true;
        }
        else if (P2Hits == 17) {
            JLabel winningLabel = new JLabel("Player 1 Wins");
            winningLabel.setFont(new Font("Comic Sans", Font.PLAIN, 35));
            winningLabel.setBounds(675,25,400,50);
            winningLabel.setVisible(true);
            winningLabel.setForeground(Color.WHITE);
            jLayeredPane.add(winningLabel,10);
            System.out.println("Player 1 Wins");
            player1Won = true;
        }
    }
}
