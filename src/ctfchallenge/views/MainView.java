package ctfchallenge.views;

import ctfchallenge.assets.Logging;
import ctfchallenge.assets.TeamList;
import ctfchallenge.ui.PointAssigner;
import ctfchallenge.ui.Toolbar;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Matteo Franzil
 * @version 1.1
 */
public class MainView extends Stage {
    public MainView(Logging logWindow, TeamList teamList) {
        BorderPane mainPane = new BorderPane();
        Scene mainScene = new Scene(mainPane);

        PointAssigner pointAssigner = new PointAssigner();

        Toolbar toolbar = new Toolbar(pointAssigner, teamList);

        mainPane.setTop(toolbar);
        mainPane.setLeft(logWindow);
        mainPane.setCenter(pointAssigner);

        setScene(mainScene);
        initGUI();
    }

    private void initGUI() {
        setMaximized(false);
        setWidth(1100);
        setHeight(600);
        setTitle("Main");
        setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Sei sicuro di voler uscire?",
                    ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> Platform.exit());
        });
        getIcons().add(new Image("file:logo.png"));
    }
}
