import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton Accueil
 */
public class RetourAccueil implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    public RetourAccueil(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }


    /**
     * L'action consiste à retourner sur la page d'accueil. Il faut vérifier qu'il n'y avait pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        if (this.vuePendu.partieEnCours()){ // il y a une partie en cours
            Optional<ButtonType> reponse = this.vuePendu.popUpPartieEnCours().showAndWait(); // on lance la fenêtre popup et on attends la réponse
            // si la réponse est oui
            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
                this.vuePendu.getChrono().stop(); // arrêt du chronomètre
                this.vuePendu.getChrono().resetTime(); // reset du chronomètre
                this.vuePendu.arretePartie();
            }
        }
        else{ // il n'y a pas de partie en cours
            this.vuePendu.arretePartie();
        }
    }
}
