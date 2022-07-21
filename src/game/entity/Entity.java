package game.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage idle_left, idle_right, idle_up, idle_down;
    public BufferedImage walk_left1, walk_right1, walk_up1, walk_down1;
    public BufferedImage walk_left2, walk_right2, walk_up2, walk_down2;

    public byte dir;
    public byte spriteCounter = 0;
    public byte spriteNumber = 1;

    public Rectangle hitbox;
    public boolean collisionOn = false;
}
