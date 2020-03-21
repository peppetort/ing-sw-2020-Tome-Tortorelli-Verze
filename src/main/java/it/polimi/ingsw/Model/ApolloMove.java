package it.polimi.ingsw.Model;

/*
    worker può spostarsi nella casella di un lavoratore avversario
    e costringendolo a occupare la casella appena liberata scambiando le posizioni
 */

public class ApolloMove implements Move {

    private Board board;
    private Player player;


    public ApolloMove(Player player){
        this.player = player;
        this.board = player.getSession().getBoard();
    }

    @Override
    public void move(Worker worker, int x, int y) {
        try {
            int wX = worker.getXPos();
            int wY = worker.getYPos();

            Box workerBox = board.getBox(wX, wY);
            Box nextBox = board.getBox(x, y);

            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 || (x == wX && y == wY)){
                throw new RuntimeException("Invalid move!");
            }else if(!workerBox.compare(nextBox)){
                throw new RuntimeException("Level not compatible!");
            }else{
                if(!nextBox.isFree()){
                    Worker other = nextBox.getPawn();
                    // Controllo se l'altra pedina è una pedina avversaria
                    if(!other.getId().equals(player.getWorker1().getId()) && !other.getId().equals(player.getWorker2().getId())){
                        board.placePawn(other, wX, wY);         //posiziono la pedina avversaria nella mia posizione
                        other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                        other.setPos(wX, wY);                   // aggiorno coordinate pedina avversaria
                        board.placePawn(worker, x, y);          // posiziono la mia pedina nella nuova posizione
                        worker.updateLastBox(workerBox);        // aggiorno l'ultima box della mia pedina
                        worker.setPos(x, y);                    //aggiorno coordinate pedona
                    }else {
                        throw new RuntimeException("Can't place pawn here!");
                    }
                }else {
                    board.placePawn(worker, x, y);
                    workerBox.removePawn();                     // rimuovo pedina dalla vecchia pos
                    worker.updateLastBox(workerBox);            // aggiorno l'ultima box nel worker
                    worker.setPos(x, y);                        // aggiorno cordinate pedina
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }catch (NullPointerException e){
            System.out.println("Pawns not in board");
        }
    }
}
