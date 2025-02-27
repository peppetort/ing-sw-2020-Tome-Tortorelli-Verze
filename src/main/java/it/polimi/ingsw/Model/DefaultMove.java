package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.CantGoUpException;
import it.polimi.ingsw.Exceptions.InvalidMoveException;

/**
 * Represents the move if the
 * {@link Player} has no card / has a card that doesn't modifies the move rules.
 */

public class DefaultMove implements Move {

    final Board board;

    /**
     * Represents constructor method.
     *
     * @param player represents the {@link Player} that will move his pawns with default rules.
     */
    public DefaultMove(Player player) {
        this.board = player.getSession().getBoard();
    }

    /**
     * Moves the selected {@link Worker} with default move rules.
     *
     * @param worker selected worker
     * @param x      x coordinate where the {@link Player} wants to move
     * @param y      y coordinate where the {@link Player} wants to move
     * @throws InvalidMoveException if the distance is too high
     * @throws InvalidMoveException if it's the same of the actual position
     * @throws InvalidMoveException if I'm trying to move on a level which is too high
     * @throws InvalidMoveException if the box is already occupied
     */
    @Override
    public void move(Worker worker, int x, int y) throws IndexOutOfBoundsException, NullPointerException {

        int wX = worker.getXPos();
        int wY = worker.getYPos();

        Box workerBox = board.getBox(wX, wY);
        Box nextBox = board.getBox(x, y);

        if (x > wX + 1 || x < wX - 1 || y > wY + 1 || y < wY - 1) {
            throw new InvalidMoveException("Move too far from the worker position!");
        }else if(x == wX && y == wY){
            throw new InvalidMoveException("Invalid move");
        } else if (!nextBox.isFree()) {
            throw new InvalidMoveException("Can't place paws here! There is a worker");
        } else if (!workerBox.compare(nextBox)) {
            throw new InvalidMoveException("Level in box " + x + " " + y + "is too high!");
        } else {
            //salvataggio stato delle celle
            //posizione iniziale della pedina
            board.addAction(worker,wX,wY,workerBox.getBlock());
            //stato della cella dove la pedina viene spostata
            board.addAction(nextBox.getPawn(),x,y,nextBox.getBlock());

            board.placePawn(worker, x, y);
            workerBox.removePawn();  //rimuovo pedina dalla vecchia pos
            worker.updateLastBox(workerBox); // aggiorno l'ultima box nel worker
        }
    }

    /**
     * Moves the selected {@link Worker} calling {@link DefaultMove#move(Worker, int x, int y) } but checking
     * that the chosen {@link Box} does not contain a higher level than the {@link Worker} is on
     *
     * @param worker {@link Worker} that you want to move
     * @param x board coordinate
     * @param y board coordinate
     * @throws IndexOutOfBoundsException if chosen coordinates are already occupied by another player
     * @throws NullPointerException if you try to move a worker that has null reference
     */
    @Override
    public void moveNoGoUp(Worker worker, int x, int y) throws IndexOutOfBoundsException, NullPointerException{
        int wX = worker.getXPos();
        int wY = worker.getYPos();

        Box workerBox = board.getBox(wX, wY);
        Box nextBox = board.getBox(x, y);

        if (workerBox.getDifference(nextBox) < 0) {
            throw new CantGoUpException("Can't go up!");
        }

        move(worker, x, y);
    }

}
