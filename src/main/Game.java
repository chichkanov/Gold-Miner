package main;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Game {

    public Game() throws FileNotFoundException {
        JFrame f = new JFrame("Level "+Levels.levelChosen);
        Board b = new Board();
        int size = Map.getRows();
        f.add(b);
        f.setSize(size * 32 + 6, size * 32 + 60);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Menu();
    }
}
