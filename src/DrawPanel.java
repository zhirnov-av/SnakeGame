import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {
    private GameScene scene;

    public DrawPanel(GameScene scene){
        super();
        this.scene = scene;
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        scene.drawScene(g);

    }
}
