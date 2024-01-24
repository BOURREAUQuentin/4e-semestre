import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton paramètres
 */
public class ControleurParametres implements EventHandler<ActionEvent> {

    private Pendu appliPendu;

    /**
     * @param appliPendu vue du jeu
     */
    public ControleurParametres(Pendu appliPendu) {
        this.appliPendu = appliPendu;
    }

    /**
     * L'action consiste à ouvrir une nouvelle fenetre où l'on peut choisir les paramètres du jeu
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        this.appliPendu.modeParametres();
    }
}
