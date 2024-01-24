import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu) {
        this.modelePendu = modelePendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText().toUpperCase();
        if (nomDuRadiobouton.equals("MÉDIUM")) {
            this.modelePendu.setNiveau(1);
        }
        else if (nomDuRadiobouton.equals("DIFFICILE")) {
            this.modelePendu.setNiveau(2);
        }
        else if (nomDuRadiobouton.equals("EXPERT")) {
            this.modelePendu.setNiveau(3);
        }
        else{
            this.modelePendu.setNiveau(0);
        }
    }
}
