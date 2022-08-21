package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameUI {

    public enum TitleScreenOption {
        NEW_GAME {
            @Override
            public String toString() {
                return "New Game";
            }
        },
        LOAD_GAME {
            @Override
            public String toString() {
                return "Load Game";
            }
        },
        OPTIONS {
            @Override
            public String toString() {
                return "Options";
            }
        },
        QUIT {
            @Override
            public String toString() {
                return "Quit";
            }
        };

        private static final TitleScreenOption[] options = values();

        public TitleScreenOption next() {
            return options[(this.ordinal() + 1) % options.length];
        }

        public TitleScreenOption previous() {
            return options[(this.ordinal() + (options.length - 1)) % options.length];
        }
    }

    private final GamePanel gp;

    private Graphics2D g2;

    private final Font fontArial20;
    private final Font fontNotifications;
    private final Font fontDialogue;

    private BufferedImage titleScreenBackground;
    private TitleScreenOption selectedTitleScreenOption = TitleScreenOption.NEW_GAME;

    private boolean notificationActive = false;
    private String notificationMessage = "";
    private int notificationActiveCounter = 0;

    public String currentDialogue = "";

    public void selectPreviousTitleScreenOption() {
        selectedTitleScreenOption = selectedTitleScreenOption.previous();
    }

    public void selectNextTitleScreenOption() {
        selectedTitleScreenOption = selectedTitleScreenOption.next();
    }

    public TitleScreenOption getSelectedTitleScreenOption() {
        return selectedTitleScreenOption;
    }

    public GameUI(GamePanel gp) {
        this.gp = gp;
        fontArial20 = new Font("Arial", Font.PLAIN, 20);
        fontNotifications = new Font("Arial", Font.BOLD, 14);
        fontDialogue = new Font("Arial", Font.BOLD, 16);

        try {
            titleScreenBackground = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/game/images/title.png")));
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void displayNotification(String text) {
        notificationMessage = text;
        notificationActive = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(fontArial20);
        g2.setColor(Color.white);

        switch (gp.getGameState()) {
            case PLAY -> drawPlayScreenUI();
            case PAUSE -> drawPauseScreenUI();
            case DIALOGUE -> drawDialogueScreenUI();
            case TITLE -> drawTitleScreen();
        }
    }

    private void drawPlayScreenUI() {
        g2.drawString("X: " + gp.player.getWorldX() / GamePanel.TILE_SIZE, 25, 40);
        g2.drawString("Y: " + gp.player.getWorldY() / GamePanel.TILE_SIZE, 25, 60);

        if (notificationActive) {
            drawNotification();
        }
    }

    private void drawPauseScreenUI() {
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = GamePanel.SCREEN_MIN_HEIGHT / 2;
        g2.drawString(text, x, y);
    }

    private int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (GamePanel.SCREEN_MIN_WIDTH / 2) - (length / 2);
    }

    private void drawDialogueScreenUI() {
        int x = GamePanel.SCREEN_MIN_WIDTH / 16;
        int y = GamePanel.SCREEN_MIN_HEIGHT / 16;
        int width = GamePanel.SCREEN_MIN_WIDTH - (x * 2);
        int height = GamePanel.SCREEN_MIN_HEIGHT / 3;

        drawWindow(x, y, width, height);

        g2.setFont(fontDialogue);
        x += x;
        y += y;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 30;
        }
    }

    private void drawWindow(int x, int y, int width, int height) {
        Color black = new Color(0, 0, 0, 192);
        g2.setColor(black);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        Color white = new Color(255, 255, 255);
        g2.setColor(white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    private void drawNotification() {
        g2.setFont(fontNotifications);

        int x = getXForCenteredText(notificationMessage);
        int y = GamePanel.SCREEN_MIN_HEIGHT - 20;

        g2.drawString(notificationMessage, x, y);

        notificationActiveCounter++;

        if (notificationActiveCounter > 120) {
            notificationActiveCounter = 0;
            notificationActive = false;
        }
    }

    private void drawTitleScreen() {
        g2.drawImage(titleScreenBackground, 0, 0, GamePanel.SCREEN_MIN_WIDTH, GamePanel.SCREEN_MIN_HEIGHT, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

        int x = 300;
        int y = GamePanel.SCREEN_MIN_HEIGHT / 2;
        int gapBetweenOptions = 50;

        for (TitleScreenOption option : TitleScreenOption.options) {
            if (option == selectedTitleScreenOption) {
                g2.setColor(Color.white);
            }
            else {
                g2.setColor(Color.red);
            }

            g2.drawString(option.toString(), x, y);

            y += gapBetweenOptions;
        }
    }
}
