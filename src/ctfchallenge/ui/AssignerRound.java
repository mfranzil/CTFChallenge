package ctfchallenge.ui;

import ctfchallenge.Team;
import ctfchallenge.assets.Common;
import ctfchallenge.assets.Logging;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public final class AssignerRound extends GridPane {

    private final int SIZE = 3;
    private Text score;
    private CheckBox completed, fixedBonus;
    private ComboBox<String> posBonus;
    private int[] roundScore;

    /**
     * AssignerRound is a class tasked with assigning points for a given team and a given round.
     * Inside it there are two CheckBoxes for round completion and fixed bonuses, and a ComboBox for
     * the variable bonuses (aka bonuses given from the leaderboard position)
     *
     * @param team  The single Team instance.
     * @param round An integer representing the round.
     */
    public AssignerRound(Team team, int round) {
        this.roundScore = new int[SIZE];

        score = new Text("Score: " + sum(roundScore));
        completed = new CheckBox("Comp.");
        fixedBonus = new CheckBox("Fixed");
        posBonus = new ComboBox<>();

        posBonus.setTooltip(new Tooltip("Position..."));

        int min = Math.min(Common.MAX_TEAMS_BONUS, Common.teamNumber);
        for (int i = 0; i < min; i++) {
            posBonus.getItems().add(String.valueOf(i + 1));
        }

        posBonus.getItems().add((min + 1) + "+");

        completed.setOnAction(e -> {
            int pts = Common.getScore(round);
            if (completed.isSelected()) {
                roundScore[0] = pts;
                team.setScore(team.getScore() + pts);
            } else {
                roundScore[0] = 0;
                team.setScore(team.getScore() - pts);
            }
            Logging.info("Team " + team.getName() + " completed round " + round + "\nScore: " + sum(roundScore));
            score.setText("Score: " + sum(roundScore));
            ((AssignerTeam) getParent()).update();
            Scoreboard.refreshScoreboard();
        });

        fixedBonus.setOnAction(e -> {
            if (fixedBonus.isSelected()) {
                roundScore[1] = Common.FIXED_BONUS;
                team.setScore(team.getScore() + Common.FIXED_BONUS);
            } else {
                roundScore[1] = 0;
                team.setScore(team.getScore() - Common.FIXED_BONUS);
            }
            Logging.info("Team " + team.getName() + " obtained fixed bonus for" +
                    " round " + round + "\nScore: " + sum(roundScore));
            score.setText("Score: " + sum(roundScore));
            ((AssignerTeam) getParent()).update();
            Scoreboard.refreshScoreboard();
        });

        posBonus.setOnAction(e -> {
            int min2 = Math.min(Common.MAX_TEAMS_BONUS, Common.teamNumber);
            int bonusScore;
            try {
                bonusScore = min2 - Integer.parseInt(posBonus.getValue()) + 1;
                team.setScore(team.getScore() + bonusScore);
                Logging.info("Team " + team.getName() + " obtained variable bonus for" +
                        " round " + round + "\nBonus: " + bonusScore);
                roundScore[2] = bonusScore;
            } catch (NumberFormatException ex) {
                team.setScore(team.getScore() - roundScore[2]);
                roundScore[2] = 0;
                Logging.info("No bonus points for team " + team.getName() + " round " + round);
            }
            score.setText("Score: " + sum(roundScore));
            ((AssignerTeam) getParent()).update();
            Scoreboard.refreshScoreboard();
        });

        add(score, 0, 0);
        add(completed, 0, 1);
        add(fixedBonus, 0, 2);
        add(posBonus, 0, 3);

        setPadding(new Insets(5));
        setVgap(5);
        setHgap(5);
    }

    /**
     *  Completely blocks access to the AssignerRound instances disabling all buttons.
     */
    public void dismantle() {
        completed.setDisable(true);
        fixedBonus.setDisable(true);
        posBonus.setDisable(true);
    }

    public int sum(int[] A) {
        int res = 0;
        for (int i = 0; i < SIZE; i++) {
            res += A[i];
        }
        return res;
    }
}
