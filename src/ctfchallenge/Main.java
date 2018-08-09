package ctfchallenge;

import ctfchallenge.assets.Logging;
import ctfchallenge.views.MainView;
import ctfchallenge.views.ScoreboardView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Logging logWindow = Logging.getInstance();
        Scoreboard scoreboard = new Scoreboard();
        TeamList teamList = new TeamList();
        scoreboard.setItems(teamList);

        ScoreboardView scoreboardWindow = new ScoreboardView(scoreboard);
        scoreboardWindow.show();

        MainView main = new MainView(scoreboard, logWindow, teamList);
        main.show();
    }


}
