package it.polimi.ingsw.Model;

public class Board {
    private Box[][] board=new Box[5][5];

    public Board()
    {
        int x;
        int y;
        for(y=0;y<5;y++)
            for(x=0;x<5;x++) {
                board[x][y]=new Box();
            }
    }

    public Box getBox(int x,int y) throws IndexOutOfBoundsException
    {
        return board[x][y];
    }

    public void placePawn(Worker worker,int x,int y) throws IndexOutOfBoundsException
    {
        board[x][y].setPawn(worker);
        worker.setPos(x,y);
    }
}
