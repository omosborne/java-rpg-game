package game;

import javax.swing.JPanel;
import java.awt.*;
import game.entity.Player;

public class GamePanel extends JPanel implements Runnable {
    // Main screen settings
    final byte originalTileSize = 16;

    public final int tileSize = originalTileSize * 3;

    final int screenMaxCol = 16;
    final int screenMaxRow = 12;

    final int screenWidth = screenMaxCol * tileSize;
    final int screenHeight = screenMaxRow * tileSize;

    KeyInputHandler keyH = new KeyInputHandler();
    Thread gameThread;

    final byte MaxFPS = 60;

    TileManager tileM = new TileManager(this);

    Player player = new Player(this, keyH);

    int playerX = 100;
    int playerY = 100;
    byte playerSpeed = 4;

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread () {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run () {
        // Game Loop Variables
        double drawInterval = 1000000000/MaxFPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // FPS Debug Variables
        long timer = 0;
        int FPS = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                FPS++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS - " + FPS);
                timer = 0;
                FPS = 0;
            }
        }
    }

    public void update () {
        player.update();
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}