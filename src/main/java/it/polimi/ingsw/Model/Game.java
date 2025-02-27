package it.polimi.ingsw.Model;


import it.polimi.ingsw.Exceptions.SimpleGameException;
import it.polimi.ingsw.Messages.ChatUpdateMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.RemovedMessage;
import it.polimi.ingsw.Observer.Observable;

import java.util.ArrayList;

/**
 * Class which represents the model itself
 */
public class Game extends Observable<Message> {
    private final ArrayList<Player> players = new ArrayList<>();    //lista dei player
    private ArrayList<God> cards;          //list delle carte selezionate dai player
    private final Board board;
    private final boolean simpleGame;

    /**
     * Indicates whether players can level up or not
     */
    private boolean canGoUp;


    /**
     * Constructor of the class {@link Game} if it's a 2-Player's match
     *
     * @param player1    Player 1, the one which choose the deck
     * @param player2    Player 2
     * @param board      Board where the game is taken
     * @param simpleGame true if the game is without any {@link God}, false if each {@link Player} has a {@link God}
     */

    public Game(String player1, String player2, Board board, boolean simpleGame) {
        this.canGoUp = true;
        this.board = board;
        this.simpleGame = simpleGame;
        players.add(new Player(player1, this, Color.BLUE));
        players.add(new Player(player2, this, Color.RED));
    }

    /**
     * Constructor of the class {@link Game} if it's a 3-Player's match
     *
     * @param player1    Player 1, the one which choose the deck
     * @param player2    Player 2
     * @param player3    Player 3
     * @param board      Board where the game is taken
     * @param simpleGame true if the game is without any {@link God}, false if each {@link Player} has a {@link God}
     */
    public Game(String player1, String player2, String player3, Board board, boolean simpleGame) {
        this.canGoUp = true;
        this.board = board;
        this.simpleGame = simpleGame;
        players.add(new Player(player1, this, Color.BLUE));
        players.add(new Player(player2, this, Color.RED));
        players.add(new Player(player3, this, Color.GREEN));
    }

    /**
     * Create the deck by checking that it is not a game without cards
     *
     * @param cards list of {@link God} chosen by player
     */
    public void addCards(ArrayList<God> cards) {
        if (simpleGame) {
            throw new SimpleGameException("Game mode: no cards!");
        }
        this.cards = cards;
    }


    /**
     * @return deck
     */
    public ArrayList<God> getCards() {
        ArrayList<God> clonedCards = new ArrayList<>(cards.size());
        clonedCards.addAll(cards);
        return clonedCards;
    }

    /**
     * @return players list
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return true if the game is without card otherwise false
     */
    public boolean isSimple() {
        return this.simpleGame;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * This method will remove the parameter {@link Player} from the {@link Game} and his {@link Worker} from the {@link Board}
     *
     * @param player player to remove
     * @throws NullPointerException if player is not present in the list of players
     */
    public void removePlayer(Player player) throws NullPointerException{
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();

        try {
            board.getBox(worker1.getXPos(), worker1.getYPos()).removePawn();
            board.getBox(worker2.getXPos(), worker2.getYPos()).removePawn();
        }catch (NullPointerException | IndexOutOfBoundsException ignored){}
        players.remove(player);
        notify(new RemovedMessage(player.getColor()));
    }

    /**
     * Notify according to MVC pattern the chat message
     *
     * @param player that sends message
     * @param message content
     */
    public void updateChat(Player player,String message){
        notify(new ChatUpdateMessage(player,message));
    }

    /**
     * Update turn calling {@link Board#removeAction()}
     */
    public void turnUpdate(){
        board.removeAction();
    }

    public void updateCanGoUp(boolean value){
        this.canGoUp = value;
    }

    public boolean getCanGoUp(){
        return this.canGoUp;
    }

}
