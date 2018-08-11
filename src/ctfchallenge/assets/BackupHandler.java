package ctfchallenge.assets;

import ctfchallenge.Main;
import ctfchallenge.Team;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackupHandler {
    /**
     * Metodo che fa un backup dei dati su backup.txt ogni volta che viene
     * chiamato sovrascrivendo il precedente.
     *
     * @param teamList Il gestore interno delle squadre.
     */
    @Deprecated
    public static void backupData(TeamList teamList) {
        try {
            BufferedWriter fileOut = new BufferedWriter(new FileWriter("log.txt"));
            fileOut.write(Logging.getLog());
            fileOut.close();
            fileOut = new BufferedWriter(new FileWriter("backup.txt"));
            Logging.info("Backup in progress...");
            for (Team temp : teamList) {
                String data = temp.getName() + " TAB " + temp.getPlayer1() + " TAB "
                        + temp.getPlayer2() + " TAB " + temp.getScore() + " TAB";
                fileOut.write(data);
            }
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Logging.info("Backup finished.");
        }

    }

    /**
     * Metodo che evoca un FileChooser per riprendere il gioco da un backup precedente.
     *
     * @param teamList Il gestore interno delle squadre.
     */
    @Deprecated
    public static void restoreData(@NotNull TeamList teamList) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload a file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fileChooser.showOpenDialog(new Stage());
            if (file == null) {
                Logging.warning("No file chosen!");
            } else {
                teamList.clear();
                Scanner stream = new Scanner(file).useDelimiter("\\s*TAB\\s*");
                while (stream.hasNext()) {
                    String nomesquadra = stream.next(), membro1 = stream.next(),
                            membro2 = stream.next(), punteggio = stream.next();
                    Team temp = new Team(nomesquadra, membro1, membro2, Integer.parseInt(punteggio));
                    teamList.add(temp);
                }
                Logging.info("Backup successfully recovered.");
            }
        } catch (FileNotFoundException ex) {
            Logging.fatal("Backup file not found. Cannot recover from backup.");
        }
    }
}
