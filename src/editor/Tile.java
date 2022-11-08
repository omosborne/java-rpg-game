package editor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Tile extends Component implements MouseListener {

    private BufferedImage image;
    private String code;
    private TilesetViewer tilesetViewer;

    public Tile(TilesetViewer tilesetViewer) {
        this.tilesetViewer = tilesetViewer;
        addMouseListener(this);
        setPreferredSize(new Dimension(EditorPanel.DEFAULT_TILE_SIZE, EditorPanel.DEFAULT_TILE_SIZE));
    }

    @Override
    public String getName() {
        return code;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage tileImage) {
        image = tileImage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String tileCode) {
        code = tileCode;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        tilesetViewer.setSelectedTile((Tile) e.getComponent());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
