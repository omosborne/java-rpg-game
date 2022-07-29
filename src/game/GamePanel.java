package game;

import javax.swing.JPanel;
import java.awt.*;

import game.entity.Entity;
import game.entity.EntityManager;
import game.entity.Player;
import game.object.ObjectManager;
import game.object.SuperObject;

public class GamePanel extends JPanel implements Runnable {
    // Main screen settings

    public final int tileSize = 16;

    public final int screenMaxCol = 60;
    public final int screenMaxRow = 32;

    public final int screenWidth = screenMaxCol * tileSize;
    public final int screenHeight = screenMaxRow * tileSize;

    // World Settings.
    public final int worldMaxCol = 70;
    public final int worldMaxRow = 70;
    public final int worldWidth = tileSize * worldMaxCol;
    public final int worldHeight = tileSize * worldMaxRow;


    KeyInputHandler keyH = new KeyInputHandler(this);
    Thread gameThread;
    public CollisionCheck cChecker = new CollisionCheck(this);

    final byte maxFPS = 60;

    TileManager tileM = new TileManager(this);

    public Player player = new Player(this, keyH);
    // Number of objects that can be displayed at once.
    public SuperObject[] obj = new SuperObject[10];
    public ObjectManager objM = new ObjectManager(this);
    public Entity[] npc = new Entity[10];
    public EntityManager npcM = new EntityManager(this);

    public UI ui = new UI(this);

    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void prepareGame() {
        objM.placeObject();
        npcM.placeEntities();
        gameState = playState;
    }

    public void startGameThread () {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run () {
        // Game Loop Variables
        double drawInterval = 1000000000/ maxFPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update () {
        if (gameState == playState) {
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        else if (gameState == pauseState) {

        }
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Tiles
        tileM.draw(g2);

        // Objects
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // NPCs
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        // Players
        player.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose();
    }
}