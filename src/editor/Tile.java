package editor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends Component {

    protected BufferedImage image;
    protected String code;

    public Tile() {
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
}
