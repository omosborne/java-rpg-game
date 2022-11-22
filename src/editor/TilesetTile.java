package editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TilesetTile extends Tile implements MouseListener {

    private TilesetViewer tilesetViewer;

    public TilesetTile(TilesetViewer tilesetViewer) {
        super();
        this.tilesetViewer = tilesetViewer;
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        tilesetViewer.setSelectedTile((Tile) event.getComponent());
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
