package game;

import javax.swing.JPanel;
import java.awt.*;

import game.entity.Entity;
import game.entity.EntityManager;
import game.entity.Player;
import game.object.ObjectManager;
import game.object.SuperObject;

public class GamePanel extends JPanel implements Runnable {

    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;

    public static final int TILE_SIZE = 16;

    public static final int WORLD_MAX_COL = 70;
    public static final int WORLD_MAX_ROW = 70;
    private static final int SCREEN_MAX_COL = 60;
    private static final int SCREEN_MAX_ROW = 32;
    private static final int SCREEN_MAX_OBJECTS = 10;
    private static final int SCREEN_MAX_ENTITIES = 10;
    public static final int SCREEN_MIN_WIDTH = SCREEN_MAX_COL * TILE_SIZE;
    public static final int SCREEN_MIN_HEIGHT = SCREEN_MAX_ROW * TILE_SIZE;

    private Thread gameThread;

    private final KeyInputHandler keyHandler = new KeyInputHandler(this);
    private final TileManager tileManager = new TileManager(this);
    private final SuperObject[] gameObjects = new SuperObject[SCREEN_MAX_OBJECTS];
    private final ObjectManager objectManager = new ObjectManager(this);
    private final Entity[] gameEntities = new Entity[SCREEN_MAX_ENTITIES];
    private final EntityManager entityManager = new EntityManager(this);
    private final CollisionHandler collisionHandler = new CollisionHandler(this);

    private final GameUI gameUI = new GameUI(this);

    public Player player = new Player(this, keyHandler);

    private int gameState;

    public boolean isInPlayState() {
        return gameState == PLAY_STATE;
    }

    public boolean isInPauseState() {
        return gameState == PAUSE_STATE;
    }

    public void setGameState(int state) {
        gameState = state;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public SuperObject[] getGameObjects() {
        return gameObjects;
    }

    public Entity[] getGameEntities() {
        return gameEntities;
    }

    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_MIN_WIDTH, SCREEN_MIN_HEIGHT));
        this.setBackground(Color.black);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void prepareGame() {
        objectManager.placeObjects();
        entityManager.placeEntities();
        gameState = PLAY_STATE;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        // Game Loop Variables
        int maxFPS = 60;
        double drawInterval = (double) 1000000000 / maxFPS;
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

    public void update() {
        if (gameState == PLAY_STATE) {
            player.update();
            for (Entity entity : gameEntities) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Tiles
        tileManager.draw(g2);

        // Objects
        for (SuperObject superObject : gameObjects) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        // NPCs
        for (Entity entity : gameEntities) {
            if (entity != null) {
                entity.draw(g2);
            }
        }

        // Players
        player.draw(g2);

        // UI
        gameUI.draw(g2);

        g2.dispose();
    }
}