package game;

import java.awt.*;

public class UI {
    GamePanel gp;
    Font fontArial40;

    public UI(GamePanel gp) {
        this.gp = gp;
        fontArial40 = new Font("Arial", Font.PLAIN, 20);
    }

    public void draw(Graphics2D g2) {

        // Don't instantiate anything here, called in game loop.
        g2.setFont(fontArial40);
        g2.setColor(Color.white);
        g2.drawString("X: " + gp.player.worldX, 25, 40);
        g2.drawString("Y: " + gp.player.worldY, 25, 60);

    }
}
