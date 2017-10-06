package snakelib;

import java.util.ArrayList;

public abstract class Scene {

    private int mapWidth;
    private int mapHeight;
    private byte[][] map;
    private Snake snake;
    private int countItems;
    private int score;

    protected SnakeEventListener listener = null;

    public Scene(int width, int height, SnakeEventListener listener){
        this(width, height);
        this.listener = listener;
    }

    public Scene(int width, int height) {
        map = new byte[width][height];
        mapHeight = height;
        mapWidth = width;
        initializeScene();
        snake = new Snake(MovingDirection.UP, mapWidth/2, mapHeight/2, this);
    }

    public void initializeScene(){
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){
                map[i][j] = 0;
            }
        }
        snake = new Snake(MovingDirection.UP, mapWidth/2, mapHeight/2, this);
        score = 0;
        countItems = 0;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public byte getMapData(int x, int y){
        return map[x][y];
    }

    public void setMapData(int x, int y, byte value){
        map[x][y] = value;
    }

    public void addSomeItems(int numItems){
        ArrayList<CellPosition> freePositions = new ArrayList<>();
        byte _map[][] = new byte[mapWidth][mapHeight];
        for (int i = 0; i < map.length; i++){
            System.arraycopy(map[i], 0, _map[i], 0, map[i].length);
        }


        for (int i = 0; i < snake.getSnakeBody().size(); i++){
            _map[snake.getSnakeBody().get(i).getX()][snake.getSnakeBody().get(i).getY()] = 2;
        }

        for (int i = 0; i < mapWidth; i++){
            for (int j = 0; j < mapHeight; j++){
                if (_map[i][j] == 0){
                    freePositions.add(new CellPosition(i, j));
                }
            }
        }

        int maxFreePositions = freePositions.size();
        int itemsCount = maxFreePositions;
        if (numItems < itemsCount)
            itemsCount = numItems;

        for (int i = 0; i < itemsCount; i++){
            int pos = (int)(Math.random() * maxFreePositions);
            map[freePositions.get(pos).getX()][freePositions.get(pos).getY()] = 1;
        }
        countItems += numItems;
    }

    public void doNextSnakeStep(){
        try {
            this.snake.doNextStep(); //If exception end of game, we must generate this event
        }catch (Exception e){
            fireActionPerformed(SnakeGameEvent.END_OF_GAME); // calling event listener
        }
    }

    public void changeSnakeMovingDirection(MovingDirection direction){
        this.snake.setMovingDirection(direction);
    }

    public Snake getSnake() {
        return snake;
    }

    protected void fireActionPerformed(SnakeGameEvent e) {
        listener.actionPerformed(e);
    }

    public void addEventListener(SnakeEventListener listener){
        this.listener = listener;
    }

    public void removeItem(CellPosition pos){
        if (map[pos.getX()][pos.getY()] == 1){
            map[pos.getX()][pos.getY()] = 0;
            countItems--;
            score += (double)20/countItems;
        }

    }

    public int getScore() {
        return score;
    }

    public int getCountItems() {
        return countItems;
    }

    public abstract void drawScene(); // method to draw game scene and snake
}
