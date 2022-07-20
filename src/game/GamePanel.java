package game;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Main screen settings
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;

    final int screenMaxCol = 16;
    final int screenMaxRow = 12;

    final int screenWidth = screenMaxCol * tileSize;
    final int screenHeight = screenMaxRow * tileSize;

    KeyInputHandler keyH = new KeyInputHandler();
    Thread gameThread;

    final byte MaxFPS = 60;

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

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

    @Override
    public void run () {
        double drawInterval = 1000000000/MaxFPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
        }
    }

    public void update () {
        if (keyH.upPressed) playerY -= playerSpeed;
        else if (keyH.leftPressed) playerX -= playerSpeed;
        else if (keyH.downPressed) playerY += playerSpeed;
        else if (keyH.rightPressed) playerX += playerSpeed;
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }
}