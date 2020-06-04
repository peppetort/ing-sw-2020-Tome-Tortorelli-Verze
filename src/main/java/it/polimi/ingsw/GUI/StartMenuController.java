package it.polimi.ingsw.GUI;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleCreate() {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("CreateMenu.fxml")));
            Scene scene = new Scene(pane, 715, 776);
            AppMain.window.setMinWidth(715);
            AppMain.window.setMinHeight(776);
            AppMain.window.setMaxWidth(715);
            AppMain.window.setMaxHeight(776);
            AppMain.window.setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void handleJoin() {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("JoinMenu.fxml")));
            Scene scene = new Scene(pane, 715, 776);
            AppMain.window.setMinWidth(715);
            AppMain.window.setMinHeight(776);
            AppMain.window.setMaxWidth(715);
            AppMain.window.setMaxHeight(776);
            AppMain.window.setScene(scene);
        }catch (IOException ignored){}
    }

}