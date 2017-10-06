import snakelib.EndOfGameException;
import snakelib.MovingDirection;
import snakelib.SnakeEventListener;
import snakelib.SnakeGameEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeGame extends JFrame {

    private GameScene scene = new GameScene(30, 30);
    private DrawPanel panel = new DrawPanel(scene);
    private Timer timer;
    private Timer timerToAddPoints;
    private int delay;

    public SnakeGame() {
        super("Snake...");

        setBounds(200, 200, 300, 300);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createMenu();

        Container container = getContentPane();
        panel.setDoubleBuffered(true);
        container.add(panel);

        delay = 250;
        timer = new Timer(delay, new TimerListener());
        timerToAddPoints = new Timer(6000, new TimerAddPointsListener());
        addKeyListener(new MyKeyListener());

        scene.addEventListener(new MySnakeEventListener());

        startGame();
    }

    public void startGame(){
        timer.stop();
        timerToAddPoints.stop();
        scene.initializeScene();
        scene.addSomeItems(3);
        timer.start();
        timerToAddPoints.start();
        delay = 250;
    }

    private void createMenu() {
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        for (String fileItem : new String [] { "New", "Exit" }) {
            JMenuItem item = new JMenuItem(fileItem);
            item.setActionCommand(fileItem.toLowerCase());
            item.addActionListener(new NewMenuListener());
            fileMenu.add(item);
        }
        fileMenu.insertSeparator(1);

        menu.add(fileMenu);
        setJMenuBar(menu);
    }

    private class NewMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("exit".equals(command)) {
                System.exit(0);
            }
            if ("new".equals(command)) {
                startGame();
            }

        }
    }

    private class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateGame();
            ((Timer)(e.getSource())).setDelay(delay);
        }
    }

    private class TimerAddPointsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            scene.addSomeItems(3);


        }
    }

    private class MySnakeEventListener implements SnakeEventListener{
        @Override
        public void actionPerformed(SnakeGameEvent event) {
            if (event == SnakeGameEvent.GOT_ITEM){
                delay -= 1; // Snake will be move a little bit faster
            }
            if (event == SnakeGameEvent.END_OF_GAME){
                timer.stop();
                timerToAddPoints.stop();
            }
        }
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 39){
                if (scene.getSnake().getMovingDirection() != MovingDirection.LEFT) {
                    scene.changeSnakeMovingDirection(MovingDirection.RIGHT);
                }
            }
            if (e.getKeyCode() == 38){
                if (scene.getSnake().getMovingDirection() != MovingDirection.DOWN) {
                    scene.changeSnakeMovingDirection(MovingDirection.UP);
                }
            }
            if (e.getKeyCode() == 37){
                if (scene.getSnake().getMovingDirection() != MovingDirection.RIGHT) {
                    scene.changeSnakeMovingDirection(MovingDirection.LEFT);
                }
            }
            if (e.getKeyCode() == 40){
                if (scene.getSnake().getMovingDirection() != MovingDirection.UP) {
                    scene.changeSnakeMovingDirection(MovingDirection.DOWN);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public void updateGame(){
        scene.doNextSnakeStep();
        panel.repaint(panel.getBounds());
    }

    public static void main(String[] args) {
        JFrame app = new SnakeGame();
        app.setVisible(true);

    }
}
