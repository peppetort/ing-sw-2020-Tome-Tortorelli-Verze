package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.PlayerLostException;
import it.polimi.ingsw.Exceptions.TurnNotStartedException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultTurnTest {



    @Test(expected = RuntimeException.class)
    public void doubleStart(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        Turn turn = new DefaultTurn(player);
        turn.start(worker);
        turn.start(worker);
    }

    @Test(expected = NullPointerException.class)
    public void startCanMoveWorkerNotPlaced(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.getBox(0,1).build(Block.LTWO);
        board.getBox(1,0).build(Block.LTWO);
        board.getBox(1,1).build(Block.LTWO);
        Turn turn = new DefaultTurn(player);
        turn.start(worker);
   }

    @Test(expected = PlayerLostException.class)
    public void startCanMoveFalse(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.placePawn(worker1, 0, 0);
        board.placePawn(worker2, 0,1);
        board.getBox(0,2).build(Block.LTWO);
        board.getBox(1,0).build(Block.LTWO);
        board.getBox(1,1).build(Block.LTWO);
        board.getBox(1,2).build(Block.LTWO);
        Turn turn = new DefaultTurn(player);
        turn.start(worker1);
    }

    @Test(expected = TurnNotStartedException.class)
    public void moveTurnNotStarted(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 1);
        Turn turn = new DefaultTurn(player);
        turn.move(1,1);
    }

    @Test(expected = RuntimeException.class)
    public void moveDouble(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 1);
        Turn turn = new DefaultTurn(player);
        turn.move(1,1);
        turn.move(1,2);
    }

    @Test(expected = TurnNotStartedException.class)
    public void buildTurnNotStarted(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 1);
        Turn turn = new DefaultTurn(player);
        turn.build(1,1);
    }

    @Test(expected = RuntimeException.class)
    public void buildMoveNotDone(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 1);
        Turn turn = new DefaultTurn(player);
        turn.start(worker);
        turn.build( 1,1);
    }

    @Test(expected = RuntimeException.class)
    public void buildDouble(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 1);
        Turn turn = new DefaultTurn(player);
        turn.build(1,1);
        turn.build(1,1);
    }

    @Test(expected = TurnNotStartedException.class)
    public void endNotStart(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Turn turn = new DefaultTurn(player);
        turn.end();
    }

    @Test(expected = RuntimeException.class)
    public void endNoMoveDone(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.placePawn(worker1, 0, 0);
        board.placePawn(worker2, 1, 0);
        Turn turn = new DefaultTurn(player);
        turn.start(worker1);
        turn.end();
    }

    @Test(expected = RuntimeException.class)
    public void endNoBuildDone(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.placePawn(worker1, 0, 0);
        board.placePawn(worker2, 1, 0);
        Turn turn = new DefaultTurn(player);
        turn.start(worker1);
        turn.move(1,1);
        turn.end();
    }

    @Test
    public void wonTrue(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.getBox(0,0).build(Block.LTWO);
        board.getBox(0,1).build(Block.LTHREE);
        board.placePawn(worker1, 0, 0);
        board.placePawn(worker2,4,4);

        Turn turn = new DefaultTurn(player);
        TurnUtils util = new TurnUtils(player);
        boolean tmp = util.getCanGoUp();
        util.setCanGoUp(true);

        turn.start(worker1);
        turn.move( 0,1);
        util.setCanGoUp(tmp);
        assertTrue(turn.won());
    }

    @Test
    public void completeDefaultTurn(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.placePawn(worker1, 0, 0);
        board.placePawn(worker2,4,4);
        Turn turn = new DefaultTurn(player);
        turn.start(worker1);
        turn.move(0, 1);
        turn.build(0,0);
        turn.end();
    }

    @Test
    public void doubleTurnDifferentWorker(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.placePawn(worker1, 0, 0);
        board.placePawn(worker2,4,4);
        Turn turn = new DefaultTurn(player);
        turn.start(worker1);
        turn.move(0, 1);
        turn.build(0,0);
        turn.end();
        turn.start(worker2);
        turn.move(4,3);
        turn.build(4,4);
        turn.end();
        assertEquals(board.getBox(0,1).getPawn(), worker1);
        assertEquals(board.getBox(4,3).getPawn(), worker2);
        assertEquals(board.getBox(0,0).getBlock(), Block.LONE);
        assertEquals(board.getBox(4,4).getBlock(), Block.LONE);
    }

}