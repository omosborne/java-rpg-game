package game;

import javax.swing.*;

public class Main {
    public static void main (String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setResizable(false);
        window.setTitle("Last Mind");
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.prepareGame();
        gamePanel.startGameThread();
    }
}