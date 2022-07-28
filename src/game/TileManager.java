package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[26];
        mapTileNum = new int[gp.worldMaxCol][gp.worldMaxRow];

        getTileImage();
        loadMap("/game/maps/world02.txt");
    }

    public void getTileImage() {
        try {
            for (int i = 10; i < tile.length; i++) {
                System.out.println(i);
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(getClass().getResourceAsStream("/game/images/tiles/tile" + i + ".png"));
            }
            tile[10].collision = true;
            // From 9 to 15
            for (int i = 19; i < 25; i++) tile[i].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapFilePath) {
        try {
            InputStream is = getClass().getResourceAsStream(mapFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.worldMaxCol && row < gp.worldMaxRow) {
                String line = br.readLine();
                while (col < gp.worldMaxCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.worldMaxCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.worldMaxCol && worldRow < gp.worldMaxRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
            int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.getWorldX() - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.getWorldX() + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.getWorldY() - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.getWorldY() + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.worldMaxCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
