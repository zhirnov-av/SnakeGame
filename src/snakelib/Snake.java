package snakelib;

import java.util.ArrayList;
import java.util.LinkedList;

public class Snake {

    private CellPosition headPosition;
    private LinkedList<CellPosition> snakeBody;
    private int snakeLength;

    private ArrayList<MovingDirection> movingDirectionList;
    private int incLength;
    private boolean incrementingLength;
    private Scene scene;

    public Snake(MovingDirection initialDirection, int posX, int posY, Scene scene){
        this.snakeLength = 1;
        movingDirectionList = new ArrayList<>();
        movingDirectionList.add(initialDirection);

        this.snakeBody = new LinkedList<>();
        this.snakeBody.addFirst(new CellPosition(posX, posY));

        this.headPosition = new CellPosition(posX, posY);

        this.incLength = 0;
        this.incrementingLength = false;

        this.scene = scene;


    }

    public void incLength(int length){
        this.incLength = length;
        this.incrementingLength = true;
    }

    public boolean doNextStep() throws EndOfGameException {

        if (movingDirectionList.size() > 1){
            movingDirectionList.remove(0);
        }
        MovingDirection direction = movingDirectionList.get(0);

        switch(direction){
            case UP:
                doNextStepUp();
                break;
            case DOWN:
                doNextStepDown();
                break;
            case LEFT:
                doNextStepLeft();
                break;
            case RIGHT:
                doNextStepRight();
                break;
        }
        checkHeadOnSnakePosition();

        if ( scene.getMapData(headPosition.getX(), headPosition.getY()) == 1 ) {
            incLength(1);
            scene.removeItem(headPosition);

            scene.fireActionPerformed(new SnakeGameEvent(1));

        }
        if (snakeLength != 0){
            snakeBody.addFirst(new CellPosition(headPosition.getX(), headPosition.getY()));
            //snakeBody.getFirst().setX(headPosition.getX());
            //snakeBody.getFirst().setY(headPosition.getY());
            if (!this.incrementingLength) {
                snakeBody.removeLast();
            }else{
                this.incrementingLength = !(--this.incLength == 0);
            }
        }

        return true;
    }

    private void checkHeadOnSnakePosition() throws EndOfGameException{
        for (int i = 0; i < snakeBody.size(); i++){
            if (snakeBody.get(i).equals(headPosition)){
                throw new EndOfGameException("GAME OVER");
            }
        }
    }

    private void doNextStepUp() throws EndOfGameException{
        if (headPosition.getY() - 1 < 0){
            throw new EndOfGameException("GAME OVER");
        }
        headPosition.setY(headPosition.getY() - 1);
    }

    private void doNextStepDown() throws EndOfGameException{
        if (headPosition.getY() + 1 >= scene.getMapHeight()){
            throw new EndOfGameException("GAME OVER");
        }
        headPosition.setY(headPosition.getY() + 1);
    }

    private void doNextStepLeft() throws EndOfGameException{
        if (headPosition.getX() - 1 < 0){
            throw new EndOfGameException("GAME OVER");
        }
        headPosition.setX(headPosition.getX() - 1);
    }

    private void doNextStepRight() throws EndOfGameException{
        if (headPosition.getX() + 1 >= scene.getMapWidth()){
            throw new EndOfGameException("GAME OVER");
        }
        headPosition.setX(headPosition.getX() + 1);
    }

    public void setMovingDirection(MovingDirection movingDirection) {
        movingDirectionList.add( movingDirection );
    }

    public LinkedList<CellPosition> getSnakeBody() {
        return snakeBody;
    }

    public CellPosition getHeadPosition() {
        return headPosition;
    }

    public MovingDirection getMovingDirection() {
        return movingDirectionList.get(0);
    }
}
