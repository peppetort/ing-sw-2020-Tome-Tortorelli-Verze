package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Exceptions.AlreadyExistingSessionException;
import it.polimi.ingsw.Exceptions.InvalidUsernameException;
import it.polimi.ingsw.Messages.SuccessfulCreate;
import it.polimi.ingsw.Messages.SessionListMessage;
import it.polimi.ingsw.Messages.SuccessfulJoin;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

public class MainController extends Observable<Object> implements Observer<Object> {

    StartMenuController startController;
    JoinMenuController joinController;
    CreateMenuController createController;

    public MainController(){
        startController = new StartMenuController();
        joinController = new JoinMenuController();
        createController = new CreateMenuController();
    }

    @Override
    public void update(Object msg) {
            if (msg instanceof SessionListMessage) {
                JoinMenuController.display((SessionListMessage) msg);
            } else if (msg instanceof AlreadyExistingSessionException) {
                CreateMenuController.handleException((Exception) msg);
            } else if (msg instanceof SuccessfulCreate) {
                createController.handleStart();
            } else if (msg instanceof SuccessfulJoin) {
                joinController.handleStart();
            } else if (msg instanceof InvalidUsernameException) {
                joinController.handleException((Exception)msg);
            }
    }



}
