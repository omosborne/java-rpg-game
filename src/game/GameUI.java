package game;

import java.awt.*;

public class GameUI {
    private final GamePanel gp;

    private Graphics2D g2;

    private final Font fontArial20;
    private final Font fontNotifications;

    private boolean notificationActive = false;
    private String notificationMessage = "";
    private int notificationActiveCounter = 0;

    public GameUI(GamePanel gp) {
        this.gp = gp;
        fontArial20 = new Font("Arial", Font.PLAIN, 20);
        fontNotifications = new Font("Arial", Font.BOLD, 14);
    }

    public void displayNotification(String text) {
        notificationMessage = text;
        notificationActive = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(fontArial20);
        g2.setColor(Color.white);

        if (gp.isInPlayState()) {
            drawPlayScreenUI();
        }
        else if (gp.isInPauseState()) {
            drawPauseScreenUI();
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
}
