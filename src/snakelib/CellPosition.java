package snakelib;

public class CellPosition {
    private int x;
    private int y;

    public CellPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object o){
        if (o instanceof CellPosition){
            CellPosition pos = (CellPosition)o;
            if (x == pos.getX() && y == pos.getY())
                return true;
            else
                return false;
        }else
            return false;
    }

    public String toString(){
        return String.format("[%d; %d]", x, y);
    }
}
