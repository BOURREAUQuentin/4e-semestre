import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Contrôleur à activer lorsque l'on clique sur un bouton d'un thème
 */
public class ControleurTheme implements EventHandler<ActionEvent> {

    private Pendu appliPendu;

    /**
     * @param appliPendu vue du jeu
     */
    public ControleurTheme(Pendu appliPendu) {
        this.appliPendu = appliPendu;
    }

    /**
     * L'action consiste à changer le thème du pendu suivant le bouton cliqué
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button bouton = (Button) actionEvent.getTarget();
        String nomBouton = bouton.getText();
        if (nomBouton.contains("Base")){
            this.appliPendu.setCouleurThemeFond("#f4f4f4");
            this.appliPendu.setCouleurThemeBordure("#e6e6fa");
        }
        else if (nomBouton.contains("Bleu")){
            this.appliPendu.setCouleurThemeFond("#4b9bff");
            this.appliPendu.setCouleurThemeBordure("#beeefc");
        }
        else if (nomBouton.contains("Rouge")){
            this.appliPendu.setCouleurThemeFond("#ff2323");
            this.appliPendu.setCouleurThemeBordure("#ffbfbf");
        }
        this.appliPendu.modeParametres(); // on remet à jour la page
    }
}
