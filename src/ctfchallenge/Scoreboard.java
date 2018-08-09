package ctfchallenge;


import ctfchallenge.assets.Logging;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public final class Scoreboard extends TableView<Team> {

    private final TableColumn<Team, String> nomesquadra;
    private final TableColumn<Team, TableColumn<Team, String>> giocatori;
    private final TableColumn<Team, String> membro1;
    private final TableColumn<Team, String> membro2;
    private final TableColumn<Team, String> punteggio;

    private final int fontsizes[] = {6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 21, 24, 36, 48, 60, 72, 84, 96};
    private int pointer = 11; // Pt = 24

    /**
     * Costruttore standard della Scoreboard. Si comporta come una TableView con le colonne come variabili d'istanza.
     */
    @SuppressWarnings("unchecked")
    public Scoreboard() {
        nomesquadra = new TableColumn<>("Nome squadra");
        giocatori = new TableColumn<>("Giocatori");
        membro1 = new TableColumn<>("Membro 1");
        membro2 = new TableColumn<>("Membro 2");
        punteggio = new TableColumn<>("Punteggio");

        nomesquadra.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        membro1.setCellValueFactory(new PropertyValueFactory<>("member1"));
        membro2.setCellValueFactory(new PropertyValueFactory<>("member2"));
        punteggio.setCellValueFactory(new PropertyValueFactory<>("score"));

        nomesquadra.prefWidthProperty().bind(widthProperty().divide(6.0));
        membro1.prefWidthProperty().bind(widthProperty().divide(3.0));
        membro2.prefWidthProperty().bind(widthProperty().divide(3.0));
        punteggio.prefWidthProperty().bind(widthProperty().divide(6.0));

        resizeFont(24);

        giocatori.getColumns().addAll(membro1, membro2);
        getColumns().addAll(nomesquadra, giocatori, punteggio);
    }

    /**
     * Metodo che decrementa il font di una grandezza fissa stabilita dall'array nella classe.
     */
    public void decrementFont() {
        --pointer;
        try {
            resizeFont(fontsizes[pointer]);
            refresh();
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logging.warning("Impossibile settare la grandezza desiderata\n");
            ++pointer;
        }
    }

    /**
     * Metodo che aumenti il font di una grandezza fissa stabilita dall'array nella classe.
     */
    public void incrementFont() {
        ++pointer;
        try {
            resizeFont(fontsizes[pointer]);
            refresh();
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logging.warning("Impossibile settare la grandezza desiderata\n");
            --pointer;
        }
    }

    /**
     * Metodo che modifica il font di una grandezza arbitraria.
     *
     * @param size La grandezza (px) del font
     */
    public void resizeFont(int size) {
        if (size > 0 && size < 100) {
            String style = ("-fx-font: " + size + "px Arial; -fx-alignment: CENTER");
            nomesquadra.setStyle(style);
            membro1.setStyle(style);
            membro2.setStyle(style);
            punteggio.setStyle(style);
            giocatori.setStyle(style);
            Logging.info("Grandezza del font settata a " + size + "\n");
        } else {
            Logging.warning("Grandezza del font non valida!" + "\n");
        }

    }
}
