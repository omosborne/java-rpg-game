package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[16];
        mapTileNum = new int[gp.screenMaxCol][gp.screenMaxRow];

        getTileImage();
        loadMap("/game/maps/map01.txt");
    }

    public void getTileImage() {
        try {
            for (int i = 0; i < tile.length; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(getClass().getResourceAsStream("/game/images/tiles/tile_" + i + ".png"));
            }

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

            while (col < gp.screenMaxCol && row < gp.screenMaxRow) {
                String line = br.readLine();
                while (col < gp.screenMaxCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.screenMaxCol) {
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

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.screenMaxCol && row < gp.screenMaxRow) {
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.screenMaxCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
