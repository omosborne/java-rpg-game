package editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LevelTile extends Tile implements MouseListener {

    private int levelX;
    private int levelY;
    private int layer;
    private LevelViewer levelViewer;

    public int getLevelX() {
        return levelX;
    }

    public int getLevelY() {
        return levelY;
    }

    public int getLayer() {
        return layer;
    }

    public LevelTile(LevelViewer levelViewer, int x, int y, int layer) {
        super();
        this.levelViewer = levelViewer;
        this.levelX = x;
        this.levelY = y;
        this.layer = layer;
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        levelViewer.updateTile((LevelTile) event.getComponent());
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }
}
