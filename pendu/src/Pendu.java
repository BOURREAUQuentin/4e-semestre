import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TitledPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData ;
import javafx.scene.control.ButtonType ;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les images des themes
     */
    private ArrayList<Image> lesImagesThemes;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * la fenetre (root) de jeu
    */
    private BorderPane fenetre;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de lancer ou relancer une partie
     */ 
    private Button bJouer;
    /**
     * le bouton qui permet de savoir les règles du jeu
     */ 
    private Button boutonInfos;
    /**
     * la variable désignant l'état du jeu
     */ 
    private String mode;
    /**
     * la variable de couleur du thème de fond choisi
     */ 
    private String couleurThemeFond;
    /**
     * la variable de couleur du thème de bordure choisi
     */ 
    private String couleurThemeBordure;
    /**
     * la valeur du slider par défaut
     */ 
    private int valSliderDefaut;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.lesImagesThemes = new ArrayList<Image>();
        this.chargerImagesThemes("./img");
        this.niveaux = new ArrayList<String>();
        this.niveaux.add("Facile");
        this.niveaux.add("Médium");
        this.niveaux.add("Difficile");
        this.niveaux.add("Expert");
        this.dessin = new ImageView(this.lesImages.get(0));
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        this.pg = new ProgressBar(0);
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(this.modelePendu, this), 8);
        this.leNiveau = new Text("");
        this.leNiveau.setFont(Font.font("Arial", 20));
        this.chrono = new Chronometre();
        this.panelCentral = new BorderPane();
        this.fenetre = new BorderPane();
        this.boutonParametres = new Button();
        this.boutonMaison = new Button();
        this.bJouer = new Button();
        this.boutonInfos = new Button();
        this.mode = "accueil";
        this.couleurThemeFond = "#f4f4f4";
        this.couleurThemeBordure = "#cecefc";
        this.valSliderDefaut = 10;
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        this.fenetre.setTop(this.titre());
        this.fenetre.setCenter(this.panelCentral);
        return new Scene(this.fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){
        BorderPane banniere = new BorderPane();
        Background background = new Background(new BackgroundFill(Color.web(this.couleurThemeBordure), null, null)); // créer un Background avec le BackgroundFill
        banniere.setBackground(background); // applique le Background au BorderPane

        Text text = new Text("Jeu du Pendu");
        text.setFont(Font.font("Arial", 30));
        BorderPane.setMargin(text, new Insets(20, 20, 20, 20));
        banniere.setLeft(text);

        HBox hbox = new HBox(5);
        ImageView imageHome = new ImageView(new Image("file:./img/home.png"));
        imageHome.setFitWidth(30);
        imageHome.setFitHeight(30);
        this.boutonMaison.setGraphic(imageHome);
        this.boutonMaison.setOnAction(new RetourAccueil(this.modelePendu, this));
        ImageView imageParametres = new ImageView(new Image("file:./img/parametres.png"));
        imageParametres.setFitWidth(30);
        imageParametres.setFitHeight(30);
        this.boutonParametres.setGraphic(imageParametres);
        this.boutonParametres.setOnAction(new ControleurParametres(this));
        ImageView imageInfos = new ImageView(new Image("file:./img/info.png"));
        imageInfos.setFitWidth(30);
        imageInfos.setFitHeight(30);
        this.boutonInfos.setGraphic(imageInfos);
        this.boutonInfos.setOnAction(new ControleurInfos(this));

        // maj des couleurs des boutons du titre
        this.boutonParametres.setStyle("-fx-background-color: "+this.couleurThemeFond);
        this.boutonMaison.setStyle("-fx-background-color: "+this.couleurThemeFond);
        this.boutonInfos.setStyle("-fx-background-color: "+this.couleurThemeFond);

        hbox.getChildren().addAll(this.boutonMaison, this.boutonParametres, this.boutonInfos);
        BorderPane.setMargin(hbox, new Insets(20, 20, 20, 20));
        banniere.setRight(hbox);

        return banniere;
    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono(){
        TitledPane res = new TitledPane("Chronomètre", this.chrono);
        res.setCollapsible(false);
        return res;
    }

    /**
     * Savoir si une partie est en cours
     * 
     * @return (boolean) : true si une partie est en cours, sinon false
     */
    public boolean partieEnCours(){
        return this.mode.equals("jeu");
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    /**
     * charge les images à afficher pour les thèmes
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImagesThemes(String repertoire){
        for (int i=0; i<3; i++){
            File file = new File(repertoire+"/theme"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImagesThemes.add(new Image(file.toURI().toString()));
        }
    }

    /**
     * change la fenetre pour la page accueil
     */
    public void modeAccueil(){
        this.mode = "accueil";
        this.modelePendu.setNiveau(0);; // on réinitialise le niveau de base (facile)
        this.boutonMaison.setDisable(true); // on désactive le bouton de maison
        this.boutonParametres.setDisable(false); // on active le bouton de parametres
        this.panelCentral = new BorderPane();
        Background background = new Background(new BackgroundFill(Color.web(this.couleurThemeFond), null, null)); // créer un Background avec le BackgroundFill
        this.panelCentral.setBackground(background); // applique le Background au BorderPane

        this.bJouer = new Button("Lancer une partie");
        this.bJouer.setStyle("-fx-background-color: "+this.couleurThemeBordure); // maj de la couleur de bJouer
        this.bJouer.setOnAction(new ControleurLancerPartie(this.modelePendu, this));
        this.panelCentral.setTop(this.bJouer);
        BorderPane.setMargin(this.bJouer, new Insets(20, 20, 20, 20));

        VBox vbox = new VBox(10);
        VBox vboxRb = new VBox(5);
        TitledPane titledPane = new TitledPane("Niveau de difficulté", vboxRb);
        titledPane.setCollapsible(false); // désactive l'ouverture/fermeture du TitledPane
        RadioButton rbFacile = new RadioButton("Facile");
        rbFacile.setOnAction(new ControleurNiveau(this.modelePendu));
        RadioButton rbMedium = new RadioButton("Médium");
        rbMedium.setOnAction(new ControleurNiveau(this.modelePendu));
        RadioButton rbDifficile = new RadioButton("Difficile");
        rbDifficile.setOnAction(new ControleurNiveau(this.modelePendu));
        RadioButton rbExpert = new RadioButton("Expert");
        rbExpert.setOnAction(new ControleurNiveau(this.modelePendu));
        ToggleGroup toggleGroup = new ToggleGroup();
        rbFacile.setToggleGroup(toggleGroup);
        rbFacile.setSelected(true);
        rbMedium.setToggleGroup(toggleGroup);
        rbDifficile.setToggleGroup(toggleGroup);
        rbExpert.setToggleGroup(toggleGroup);
        vboxRb.getChildren().addAll(rbFacile, rbMedium, rbDifficile, rbExpert);

        vbox.getChildren().add(titledPane);
        this.panelCentral.setCenter(vbox);
        BorderPane.setMargin(vbox, new Insets(20, 20, 20, 20));

        this.fenetre.setTop(this.titre());
        this.fenetre.setCenter(this.panelCentral);
    }
    
    /**
     * change la fenetre pour la page jeu
     */
    public void modeJeu(){
        this.mode = "jeu";
        // réinitialisation de l'affichage
        this.pg.setProgress(0);
        this.dessin.setImage(this.lesImages.get(0));
        this.modelePendu.setMotATrouver();
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(this.modelePendu, this), 8);
        this.clavier.changeCouleurClavier(this.couleurThemeBordure);

        this.boutonMaison.setDisable(false); // on active le bouton de maison
        this.boutonParametres.setDisable(true); // on desactive le bouton de parametres
        this.panelCentral = new BorderPane();
        Background background = new Background(new BackgroundFill(Color.web(this.couleurThemeFond), null, null)); // créer un Background avec le BackgroundFill
        this.panelCentral.setBackground(background); // applique le Background au BorderPane

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        this.motCrypte.setFont(Font.font("Arial", 25));
        vbox.getChildren().addAll(this.motCrypte, this.dessin, this.pg, this.clavier);
        this.panelCentral.setLeft(vbox);

        VBox vbox2 = new VBox(10);
        this.leNiveau.setText("Niveau " + this.niveaux.get(this.modelePendu.getNiveau())); // on change le text du niveau
        this.bJouer = new Button("Nouveau mot");
        this.bJouer.setStyle("-fx-background-color: "+this.couleurThemeBordure);
        this.bJouer.setOnAction(new ControleurLancerPartie(this.modelePendu, this));
        vbox2.getChildren().addAll(this.leNiveau, this.leChrono(), this.bJouer);
        
        // gestion des marges
        BorderPane.setMargin(vbox, new Insets(20, 20, 20, 50));
        BorderPane.setMargin(vbox2, new Insets(20, 20, 20, 20));
        VBox.setMargin(this.leNiveau, new Insets(0, 0, 25, 0));
        VBox.setMargin(this.bJouer, new Insets(20, 0, 0, 0));

        this.panelCentral.setRight(vbox2);
        this.fenetre.setTop(this.titre());
        this.fenetre.setCenter(this.panelCentral);
    }
    
    /**
     * change la fenetre pour la page parametres
     */
    public void modeParametres(){
        this.boutonMaison.setDisable(false); // on active le bouton de maison
        this.boutonParametres.setDisable(true); // on desactive le bouton de parametres
        this.panelCentral = new BorderPane();
        Background background = new Background(new BackgroundFill(Color.web(this.couleurThemeFond), null, null)); // créer un Background avec le BackgroundFill
        this.panelCentral.setBackground(background); // applique le Background au BorderPane
        
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.TOP_CENTER);
        Text text = new Text("Choix des thèmes du Pendu");
        text.setFont(Font.font("Arial", 20));
        Button boutonBase = new Button(" Base");
        ImageView imageThemeBase = new ImageView(this.lesImagesThemes.get(0));
        imageThemeBase.setFitWidth(150);
        imageThemeBase.setFitHeight(150);
        boutonBase.setGraphic(imageThemeBase);
        boutonBase.setOnAction(new ControleurTheme(this));
        Button boutonBleu = new Button(" Bleu");
        ImageView imageThemeBleu = new ImageView(this.lesImagesThemes.get(1));
        imageThemeBleu.setFitWidth(150);
        imageThemeBleu.setFitHeight(150);
        boutonBleu.setGraphic(imageThemeBleu);
        boutonBleu.setOnAction(new ControleurTheme(this));
        Button boutonRouge = new Button(" Rouge");
        ImageView imageThemeRouge = new ImageView(this.lesImagesThemes.get(2));
        imageThemeRouge.setFitWidth(150);
        imageThemeRouge.setFitHeight(150);
        boutonRouge.setGraphic(imageThemeRouge);
        boutonRouge.setOnAction(new ControleurTheme(this));
        vbox.getChildren().addAll(text, boutonBase, boutonBleu, boutonRouge);
        VBox.setMargin(text, new Insets(20, 0, 0, 20));
        VBox.setMargin(boutonBase, new Insets(20, 0, 0, 20));
        VBox.setMargin(boutonBleu, new Insets(20, 0, 0, 20));
        VBox.setMargin(boutonRouge, new Insets(20, 0, 0, 20));
        this.panelCentral.setLeft(vbox);

        GridPane gridPane = new GridPane();
        Text textLettres = new Text("Nombre de lettres maximum par mot :");
        textLettres.setFont(Font.font("Arial", 20));
        Slider slider = new Slider(4, 20, this.valSliderDefaut);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // lorsque la valeur du slider change
            this.modelePendu.setLongueurMaxMot(newValue.intValue()); // change le nombre de lettres max d'un mot (change donc le dictionnaire)
            this.valSliderDefaut = newValue.intValue(); // rafraichi la valeur par défaut du slider
        });
        gridPane.add(textLettres, 0, 0);
        gridPane.add(slider, 0, 1);
        GridPane.setMargin(textLettres, new Insets(20, 20, 0, 0));
        GridPane.setMargin(slider, new Insets(20, 20, 0, 0));
        this.panelCentral.setRight(gridPane);

        this.fenetre.setTop(this.titre());
        this.fenetre.setCenter(this.panelCentral);
    }

    /** lance une partie */
    public void lancePartie(){
        this.modeJeu();
    }

    /** arrete une partie (retour à l'accueil) */
    public void arretePartie(){
        this.modeAccueil();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     * @param nbLettresCorrespondantes le nombre de lettres correspondantes dans le mot crypté
     */
    public void majAffichage(int nbLettresCorrespondantes){
        this.clavier.desactiveTouches(this.modelePendu.getLettresEssayees());
        System.out.println(this.modelePendu.getMotATrouve());
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        if (this.modelePendu.getNbErreursRestants() == 0 || this.modelePendu.gagne()){ // la partie est finie (on désactive toutes les touches) et on affiche le mot crypté
            this.pg.setProgress((this.modelePendu.getNbErreursMax() - this.modelePendu.getNbErreursRestants())*0.1); // cas de la dernière erreur utilisée
            this.motCrypte.setText(this.modelePendu.getMotATrouve()); // découvre le mot crypté
            this.clavier.desactiveClavier();
            this.chrono.stop();
        }
        else if (nbLettresCorrespondantes == 0){ // si la lettre n'est pas présente dans le mot et que la partie n'est pas finie
            this.pg.setProgress((this.modelePendu.getNbErreursMax() - this.modelePendu.getNbErreursRestants())*0.1);
            this.dessin.setImage(this.lesImages.get(this.modelePendu.getNbErreursMax() - this.modelePendu.getNbErreursRestants())); // faire un test quand c'est fini (les images ne sont pas en raccord avec le nb d'essais)
        }
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        return this.chrono;
    }

    /**
     * alerte (pop-up) pour la partie en cours
     * @return Alert la pop-up de partie en cours
     */
    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Partie en cours");
        alert.setTitle("Attention");
        return alert;
    }
        
    /**
     * alerte (pop-up) pour les règles du jeu
     * @return Alert la pop-up des règles du jeu
     */
    public Alert popUpReglesDuJeu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Infos");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setHeaderText("Règles du jeu du Pendu");
        alert.setContentText("IMPORTANT :\n\nVous avez un nombre limité de tentatives pour deviner le mot secret avant que le dessin du pendu ne soit complété.\n- Si le pendu est complété avant que vous ne deviniez le mot, vous perdez la partie.\n- Si vous devinez le mot avant que le pendu ne soit complété, vous remportez la partie.\n\n\n1. Proposez une lettre à la fois en suggérant une lettre qui pourrait être présente dans le mot secret.\n\n2. Si la lettre est correcte, elle sera révélée dans les emplacements appropriés du mot secret. Si la lettre est incorrecte, une partie du pendu sera dessinée pour indiquer votre erreur et la barre d'erreurs augmentera en conséquence.\n\n3. Le jeu se termine lorsque vous devinez correctement le mot secret ou lorsque le dessin du pendu est complet.");
        return alert;
    }
    
    /**
     * alerte (pop-up) pour avoir gagné la partie
     * @return Alert la pop-up pour avoir gagné la partie
     */
    public Alert popUpMessageGagne(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bien joué, vous avez gagné !");
        alert.setTitle("Gagné"); 
        return alert;
    }
    
    /**
     * alerte (pop-up) pour avoir perdu la partie
     * @return Alert la pop-up pour avoir perdu la partie
     */
    public Alert popUpMessagePerdu(){   
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Dommage, vous avez perdu !");
        alert.setTitle("Perdu");
        return alert;
    }

    /**
     * Change la couleur du theme de fond
     * @param newCouleurThemeFond la nouvelle couleur du theme de fond de la fenetre
     */
    public void setCouleurThemeFond(String newCouleurThemeFond){
        this.couleurThemeFond = newCouleurThemeFond;
    }

    /**
     * Change la couleur du theme des boutons et clavier (bordures)
     * @param newCouleurThemeBordure la nouvelle couleur du theme des boutons et clavier (bordures) de la fenetre
     */
    public void setCouleurThemeBordure(String newCouleurThemeBordure){
        this.couleurThemeBordure = newCouleurThemeBordure;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
