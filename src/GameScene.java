import snakelib.Scene;

import javax.swing.*;
import java.awt.*;

public class GameScene extends Scene {
    Graphics graphics;

    public GameScene(int width, int height){
        super(width, height);
        this.graphics = graphics;

    }

    public void drawScene(Graphics g){
        graphics = g;
        drawScene();
    }

    public void drawScene(){


        double cellWidth = (double)graphics.getClipBounds().width / (double)getMapWidth();
        double cellHeight = (double)graphics.getClipBounds().height / (double)getMapHeight();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, graphics.getClipBounds().width, graphics.getClipBounds().height);

        for (int i = 0; i < getMapWidth(); i++){
            for (int j = 0; j < getMapHeight(); j++){
                if (getMapData(i, j) == 1) {
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect((int) Math.round(i * cellWidth), (int) Math.round(j * cellHeight), (int) Math.round(cellWidth), (int) Math.round(cellHeight));
                }
            }
        }

        graphics.setColor(Color.GREEN);
        for (int i = 0; i < getSnake().getSnakeBody().size(); i++){
            graphics.fillRect((int)Math.round(getSnake().getSnakeBody().get(i).getX() * cellWidth), (int)Math.round(getSnake().getSnakeBody().get(i).getY() * cellHeight), (int)Math.round(cellWidth), (int)Math.round(cellHeight));
        }

        graphics.setColor(Color.BLUE);
        //graphics.drawString(String.format("width: %d, hight: %d",graphics.getClipBounds().width, graphics.getClipBounds().height), 10, 10);
        graphics.drawString(String.format("Score: %d", getScore()), 10, 10);

    }
}
