package game;

import java.awt.*;

public class GameUI {
    private final GamePanel gp;

    private Graphics2D g2;

    private final Font fontArial20;
    private final Font fontNotifications;
    private final Font fontDialogue;

    private boolean notificationActive = false;
    private String notificationMessage = "";
    private int notificationActiveCounter = 0;

    public String currentDialogue = "";

    public GameUI(GamePanel gp) {
        this.gp = gp;
        fontArial20 = new Font("Arial", Font.PLAIN, 20);
        fontNotifications = new Font("Arial", Font.BOLD, 14);
        fontDialogue = new Font("Arial", Font.BOLD, 16);
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
        else if (gp.isInDialogueState()) {
            drawDialogueScreenUI();
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
}
