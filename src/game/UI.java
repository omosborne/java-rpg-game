package game;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font fontArial20;
    public boolean notificationActive = false;
    public String notifictionMessage = "";
    int notificationActiveCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        fontArial20 = new Font("Arial", Font.PLAIN, 20);
    }

    public void displayNotification(String text) {
        notifictionMessage = text;
        notificationActive = true;
    }

    public void draw(Graphics2D g2) {
        // Don't instantiate anything here, called in game loop.

        this.g2 = g2;

        g2.setFont(fontArial20);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState) drawPlayScreen();
        else if (gp.gameState == gp.pauseState) drawPauseScreen();
    }

    public void drawPlayScreen() {
        g2.drawString("X: " + gp.player.worldX, 25, 40);
        g2.drawString("Y: " + gp.player.worldY, 25, 60);

        // Notifications
        if (notificationActive) {
            g2.setFont(g2.getFont().deriveFont(10F));
            int x = getXForCenteredText(notifictionMessage);
            int y = gp.screenHeight - 20;
            g2.drawString(notifictionMessage, x, y);

            notificationActiveCounter++;

            if (notificationActiveCounter > 120) {
                notificationActiveCounter = 0;
                notificationActive = false;
            }
        }
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    private int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
