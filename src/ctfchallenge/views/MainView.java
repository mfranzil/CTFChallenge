package ctfchallenge.views;

import ctfchallenge.assets.Logging;
import ctfchallenge.assets.TeamList;
import ctfchallenge.ui.AssignerTable;
import ctfchallenge.ui.Toolbar;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * @author Matteo Franzil
 * @version 20181108v1
 */
public class MainView extends Stage {
    public MainView(Logging logWindow, TeamList teamList) {
        BorderPane mainPane = new BorderPane();
        Scene mainScene = new Scene(mainPane);

        AssignerTable assignerTable = new AssignerTable();
        ScrollPane scrollPane = new ScrollPane(assignerTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Toolbar toolbar = new Toolbar(assignerTable, teamList);

        mainPane.setTop(toolbar);
        mainPane.setLeft(logWindow);
        mainPane.setCenter(scrollPane);

        setScene(mainScene);
        initGUI(toolbar);
    }

    private void initGUI(Toolbar toolbar) {
        setMaximized(false);
        setWidth(1100);
        setHeight(600);
        setTitle("CTFChallenge");

        setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you really want to quit?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                toolbar.stopBackupThread();
                Platform.exit();
            }
        });

        // Icona dell'applicazione
        getIcons().add(new Image("file:logo.png"));
    }
}
