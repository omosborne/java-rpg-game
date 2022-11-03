package game;

import javax.swing.JPanel;
import java.awt.*;

import game.entity.Entity;
import game.entity.EntityManager;
import game.entity.Player;
import game.object.ObjectManager;
import game.object.SuperObject;

public class GamePanel extends JPanel implements Runnable {

    public enum State {
        TITLE {
            @Override
            public boolean isTitle() {
                return true;
            }

            @Override
            public boolean isWorldVisible() {
                return false;
            }
        },
        PLAY {
            @Override
            public boolean isPlay() {
                return true;
            }
        },
        PAUSE {
            @Override
            public boolean isPause() {
                return true;
            }
        },
        DIALOGUE {
            @Override
            public boolean isDialogue() {
                return true;
            }
        };

        public boolean isTitle() {
            return false;
        }

        public boolean isPlay() {
            return false;
        }

        public boolean isPause() {
            return false;
        }

        public boolean isDialogue() {
            return false;
        }

        public boolean isWorldVisible() {
            return true;
        }
    }

    public static final boolean IS_IN_DEBUG_MODE = false;

    public static final String DEFAULT_MAP = "world04";

    public static final int TILE_SIZE = 96;
    public static final int ZOOM_FACTOR = 6;

    public static final int WORLD_MAX_COL = 20;
    public static final int WORLD_MAX_ROW = 20;
    private static final int SCREEN_MAX_COL = 16;
    private static final int SCREEN_MAX_ROW = 9;
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

    private State gameState = State.TITLE;

    private int maxFPS = 60;
    private int fps;

    public String getFPS() {
        return String.valueOf(fps);
    }

    public State getGameState() {
        return gameState;
    }

    public void setGameState(State state) {
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
        setGameState(State.TITLE);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        while (gameThread != null) {
            long timeBeforeUpdateInMS = System.currentTimeMillis();
            update();
            repaint();
            long timeAfterUpdateInMS = System.currentTimeMillis();

            long timeIntervalInMS = (timeAfterUpdateInMS - timeBeforeUpdateInMS);
            long threadSleepTimeInMS = 1000 / maxFPS - timeIntervalInMS;

            try {
                if (threadSleepTimeInMS > 0) {
                    Thread.sleep(threadSleepTimeInMS);
                    fps = maxFPS;
                } else {
                    fps = (int) (1000 / timeIntervalInMS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                gameThread.interrupt();
            }
        }
    }

    public void update() {
        if (!gameState.isPlay()) return;

        player.update();

        for (Entity entity : gameEntities) {
            if (entity != null) {
                entity.update();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState.isWorldVisible()) {
            drawWorldContent(g2);
        }
        else {
            gameUI.draw(g2);
        }

        g2.dispose();
    }

    private void drawWorldContent(Graphics2D g2) {
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
}