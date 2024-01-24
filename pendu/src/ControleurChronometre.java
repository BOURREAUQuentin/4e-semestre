import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur du chronomètre
 */
public class ControleurChronometre implements EventHandler<ActionEvent> {
    /**
     * temps enregistré lors du dernier événement
     */
    private long tempsPrec;
    /**
     * temps courant depuis le début de la mesure
     */
    private long tempsCourant;
    /**
     * Vue du chronomètre
     */
    private Chronometre chrono;

    /**
     * Constructeur du contrôleur du chronomètre
     * noter que le modèle du chronomètre est tellement simple
     * qu'il est inclus dans le contrôleur
     * @param chrono Vue du chronomètre
     */
    public ControleurChronometre (Chronometre chrono){
        this.chrono = chrono;
        this.tempsPrec = 0;
        this.tempsCourant = -1;
    }

    /**
     * Actions à effectuer tous les pas de temps
     * essentiellement mesurer le temps écoulé depuis la dernière mesure
     * et mise à jour de la durée totale
     * @param actionEvent événement Action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        long heureDuSysteme = System.currentTimeMillis();
        if (this.tempsCourant != -1){
            // calcul du temps écoulé depuis la dernière frame
            long tempsEcoule = heureDuSysteme - this.tempsCourant;
            this.tempsPrec += tempsEcoule;
            this.chrono.setTime((this.tempsPrec/1000));
        }
        this.tempsCourant = heureDuSysteme;
    }

    /**
     * Remet la durée à 0
     */
    public void reset(){
        this.tempsPrec = 0;
        this.tempsCourant = -1;
    }
}
