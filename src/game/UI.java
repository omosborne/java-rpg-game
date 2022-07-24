package game;

import java.awt.*;

public class UI {
    GamePanel gp;
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
        g2.setFont(fontArial20);
        g2.setColor(Color.white);
        g2.drawString("X: " + gp.player.worldX, 25, 40);
        g2.drawString("Y: " + gp.player.worldY, 25, 60);

        // Notifications
        if (notificationActive) {
            g2.setFont(g2.getFont().deriveFont(10F));
            g2.drawString(notifictionMessage, 25, 80);

            notificationActiveCounter++;

            if (notificationActiveCounter > 120) {
                notificationActiveCounter = 0;
                notificationActive = false;
            }
        }

    }
}
