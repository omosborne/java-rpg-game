package game;

import javax.swing.JPanel;
import java.awt.*;

import game.entity.Entity;
import game.entity.EntityManager;
import game.entity.Player;
import game.object.ObjectManager;
import game.object.SuperObject;

public class GamePanel extends JPanel implements Runnable {

    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int DIALOGUE_STATE = 3;

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

    private final KeyInputHandler keyHandler;
    public final Player player;
    private final Camera camera;

    private final TileManager tileManager;
    private final SuperObject[] gameObjects;
    private final ObjectManager objectManager;
    private final Entity[] gameEntities;
    private final EntityManager entityManager;
    private final CollisionHandler collisionHandler;

    private final GameUI gameUI;

    private int gameState;

    public boolean isInTitleState() {
        return gameState == TITLE_STATE;
    }

    public boolean isInPlayState() {
        return gameState == PLAY_STATE;
    }

    public boolean isInPauseState() {
        return gameState == PAUSE_STATE;
    }

    public boolean isInDialogueState() {
        return gameState == DIALOGUE_STATE;
    }

    public void setGameState(int state) {
        gameState = state;
    }

    public KeyInputHandler getKeyHandler() {
        return keyHandler;
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

    public Player getPlayer() {
        return player;
    }

    public Camera getCamera() {
        return camera;
    }

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_MIN_WIDTH, SCREEN_MIN_HEIGHT));

        keyHandler = new KeyInputHandler(this);
        player = new Player(this, keyHandler);
        camera = new Camera(this);

        tileManager = new TileManager(this);
        gameObjects = new SuperObject[SCREEN_MAX_OBJECTS];
        objectManager = new ObjectManager(this);
        gameEntities = new Entity[SCREEN_MAX_ENTITIES];
        entityManager = new EntityManager(this);
        collisionHandler = new CollisionHandler(this);

        gameUI = new GameUI(this);

        setBackground(Color.black);
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void prepareGame() {
        objectManager.placeObjects();
        entityManager.placeEntities();
        gameState = TITLE_STATE;
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
        if (isInPlayState()) {
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

        if (isInTitleState()) {
            gameUI.draw(g2);
        }
        else {
            // Tiles
            tileManager.draw(g2);

            // Objects
            for (SuperObject superObject : gameObjects) {
                if (superObject != null) {
                    superObject.draw(g2);
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
        }

        g2.dispose();
    }
}