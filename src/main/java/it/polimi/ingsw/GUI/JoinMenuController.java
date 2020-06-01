package it.polimi.ingsw.GUI;

import it.polimi.ingsw.ClientGUIApp;
import it.polimi.ingsw.Exceptions.InvalidUsernameException;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.PlayerRetrieveSessions;
import it.polimi.ingsw.Messages.PlayerSelectSession;
import it.polimi.ingsw.Messages.SessionListMessage;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class JoinMenuController implements Initializable {

	private static MainController mainController;

	private static String session;

	public TableView<SessionObject> sessionsTable;
	public TableColumn<SessionObject, String> name;
	public TableColumn<SessionObject, Integer> players;
	public TableColumn<SessionObject, Boolean> cards;
	public ImageView hourGlass1;
	public ImageView hourGlass2;
	public Button backButton;
	public Button joinButton;

	public static ObservableList<SessionObject> list = FXCollections.observableArrayList();

	public static void setMainController(MainController mc) {
		mainController = mc;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Message msg = new PlayerRetrieveSessions();
		mainController.notify(msg);

		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		players.setCellValueFactory(new PropertyValueFactory<>("players"));
		cards.setCellValueFactory(new PropertyValueFactory<>("cards"));

/*        name.setStyle( "-fx-alignment: CENTER;");
        players.setStyle( "-fx-alignment: CENTER;");
        cards.setStyle( "-fx-alignment: CENTER;");*/

		list.addListener((ListChangeListener<SessionObject>) change -> {
			while (change.next()) {
				sessionsTable.getItems().add(change.getAddedSubList().get(0));
			}
		});
	}

	public void handleBack() throws IOException {
		AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartMenu.fxml")));
		Scene scene = new Scene(pane, 715, 776);
		ClientGUIApp.window.setMinWidth(715);
		ClientGUIApp.window.setMinHeight(776);
		ClientGUIApp.window.setMaxWidth(715);
		ClientGUIApp.window.setMaxHeight(776);
		ClientGUIApp.window.setScene(scene);
	}

	public void handleJoin() throws IOException {
        String username = null;
        session = sessionsTable.getSelectionModel().getSelectedItem().name;
        UsernameDialog dialog = new UsernameDialog();
        username = dialog.display();

        String finalUsername = username;
        new Thread(() -> {
            if (finalUsername != null && finalUsername.length() >= 1) {
				mainController.notify(new PlayerSelectSession(session, finalUsername));
			}
		}).start();

		hourGlass1.setVisible(true);
		hourGlass2.setVisible(true);

		backButton.setDisable(true);
		joinButton.setDisable(true);

		RotateTransition rt1 = new RotateTransition(Duration.millis(2000), hourGlass1);
		RotateTransition rt2 = new RotateTransition(Duration.millis(2000), hourGlass2);

		rt1.setByAngle(360);
		rt1.setCycleCount(Animation.INDEFINITE);
		rt1.setInterpolator(Interpolator.LINEAR);


		rt2.setByAngle(360);
		rt2.setCycleCount(Animation.INDEFINITE);
		rt2.setInterpolator(Interpolator.LINEAR);

		rt1.play();
		rt2.play();
	}

	public void handleStart() {
		Platform.runLater(() -> {
			try {
				if (!mainController.isPlaying()) {
					AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Wait.fxml")));
					Scene scene = new Scene(pane, 953, 511);
					ClientGUIApp.window.setMinWidth(953);
					ClientGUIApp.window.setMinHeight(511);
					ClientGUIApp.window.setMaxWidth(953);
					ClientGUIApp.window.setMaxHeight(511);
					ClientGUIApp.window.setScene(scene);
				}
			} catch (IOException ignored) {
			}
		});
	}

	public static void handleException(Exception e) {
		if (e instanceof InvalidUsernameException) {
			System.out.println("Invalid username");
//            SessionObject obj;
//            obj = sessionsTable.getSelectionModel().getSelectedItem();
			Platform.runLater(() -> {
				try {
					String username = new UsernameDialog().displayError();
					if (username.length() > 0) {
						mainController.notify(new PlayerSelectSession(session, username));
					}
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			});
		}
	}


	public static void display(SessionListMessage msg) {
		HashMap<String, Integer> players = msg.getParticipants();
		HashMap<String, Boolean> cards = msg.getCards();

		for (String s : players.keySet()) {
			System.out.println("Session added to list");
			list.add(new SessionObject(s, players.get(s), cards.get(s)));
		}
	}

}
